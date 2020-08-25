package com.peter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.peter.yarn.Queue;
import com.peter.yarn.YarnProperty;
import com.peter.yarn.YarnQuota;
import com.peter.service.YarnUtil;
import com.peter.utils.HttpClientRequest;
import com.peter.utils.ResourceCalcUtil;
import com.peter.utils.SizeConvertUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.authentication.client.AuthenticatedURL;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lcc
 */
@Service
public class YarnUtilImpl implements YarnUtil {
    private static final Log LOG = LogFactory.getLog(YarnUtilImpl.class);

    private HttpClientRequest clientRequest = null;
    private String yarnSchema = null;
    private boolean kerberos=false;
    private String platform=null;
    private String kerberosUser=null;
    private String kerberosKeytab=null;

    @Override
    public boolean init(YarnProperty yarnProperty){
        yarnSchema=yarnProperty.getYarnSchema();
        kerberos=yarnProperty.isKerberos();
        kerberosUser=yarnProperty.getKerberosUser();
        kerberosKeytab=yarnProperty.getKerberosKeytab();
        platform=yarnProperty.getPlatform();
        clientRequest = new HttpClientRequest();
        LOG.info("YarnUtil init with "+yarnSchema);
        return true;
    }

    /**
     * 递归获取当前队列的所有子队列
     *
     * @param queueJsonObejct 当前队列所在的json结构
     * @param queue           当前队列
     * @param detail          是否需要quota信息
     */
    private void getChildrenQueue(JSONObject queueJsonObejct, Queue queue, boolean detail) {
        JSONObject queuesJsonObject = (JSONObject) queueJsonObejct.get("queues");
        if (queuesJsonObject == null)
            return;
        JSONArray queueArray = (JSONArray) queuesJsonObject.get("queue");

        List<Queue> children = new ArrayList<>();
        for (int i = 0; i < queueArray.size(); i++) {
            JSONObject childQueueJsonObejct = (JSONObject) queueArray.get(i);
            String queueName = childQueueJsonObejct.getString("queueName");
            YarnQuota yarnQuota = null;
            if (detail) {
                long vcoresCapacity = 0;
                long memoryGCapacity = 0;
                long vcoresMaxCapacity = 0;
                long memoryMaxCapacity = 0;
                double globalRatio = 1.0;
                double globalMaxRatio = 1.0;
                double parentRatio = 0;
                double parentMaxRatio = 0;

                parentRatio = childQueueJsonObejct.getDoubleValue("capacity") / 100;
                parentMaxRatio = childQueueJsonObejct.getDoubleValue("maxCapacity") / 100;

                JSONObject amResourceLimit = (JSONObject) childQueueJsonObejct.get("AMResourceLimit");
                vcoresMaxCapacity= ResourceCalcUtil.calcAbsoluteCapacity(amResourceLimit,queue.getYarnQuota().getVcoresMaxCapacity(),parentMaxRatio,"vCores");
                memoryMaxCapacity= ResourceCalcUtil.calcAbsoluteCapacity(amResourceLimit,queue.getYarnQuota().getMemoryMaxCapacity(),parentMaxRatio,"memory",true);

                JSONObject usedAMResource = (JSONObject) childQueueJsonObejct.get("usedAMResource");
                vcoresCapacity= ResourceCalcUtil.calcAbsoluteCapacity(usedAMResource,queue.getYarnQuota().getVcoresCapacity(),parentRatio,"vCores");
                memoryGCapacity= ResourceCalcUtil.calcAbsoluteCapacity(usedAMResource,queue.getYarnQuota().getMemoryGCapacity(),parentRatio,"memory",true);

                globalRatio = childQueueJsonObejct.getDoubleValue("absoluteCapacity") / 100;
                globalMaxRatio = childQueueJsonObejct.getDoubleValue("absoluteMaxCapacity") / 100;

                yarnQuota = new YarnQuota(queueName, queue.getYarnQuota().getFullQueueName() + "." + queueName, vcoresCapacity, memoryGCapacity, vcoresMaxCapacity, memoryMaxCapacity, globalRatio, globalMaxRatio, parentRatio, parentMaxRatio);
            }
            Queue child = new Queue(queueName, yarnQuota, null);
            getChildrenQueue(childQueueJsonObejct, child, detail);
            children.add(child);
        }

        queue.setChildren(children);
    }

    @Override
    public String getSchedulerFromRest() {
        LOG.info(kerberos+","+platform);
        if (kerberos&& StringUtils.equals(platform,"ambari")) {
            LOG.info("ambari kerberos enabled");
            Configuration conf = new Configuration();
            conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
            conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
            UserGroupInformation.setConfiguration(conf);
            try {
                UserGroupInformation.loginUserFromKeytab(kerberosUser, kerberosKeytab);

                URL url = new URL(yarnSchema + "/ws/v1/cluster/scheduler");
                AuthenticatedURL.Token token = new AuthenticatedURL.Token();
                HttpURLConnection conn = new AuthenticatedURL().openConnection(url, token);
                conn.setRequestMethod("GET");
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    LOG.error("yarn 8088 web page request failed, code:"+responseCode);
                    return null;
                }
                InputStream inputStream = conn.getInputStream();
                List<String> lines = IOUtils.readLines(inputStream);
                return String.join("", lines);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("get response from yarn web :"+e.getMessage());
                return null;
            }
        } else {
            return clientRequest.request(yarnSchema + "/ws/v1/cluster/scheduler");
        }
    }

    /**
     * 获取所有队列列表
     *
     * @param detail 是否需要quota信息
     * @return
     * @throws IOException
     * @throws YarnException
     */
    @Override
    public Queue getQueueList(boolean detail) {
        String result = getSchedulerFromRest();
        if (result == null) return null;
        JSONObject resultJsonObejct = JSONObject.parseObject(result);
        JSONObject scheduler = (JSONObject) resultJsonObejct.get("scheduler");
        JSONObject schedulerInfo = (JSONObject) scheduler.get("schedulerInfo");
        String queueName = schedulerInfo.getString("queueName");
        YarnQuota yarnQuota = null;
        if (detail) {
            long vcoresCapacity = 0;
            long memoryGCapacity = 0;
            long vcoresMaxCapacity = 0;
            long memoryMaxCapacity = 0;
            double globalRatio = 1.0;
            double globalMaxRatio = 1.0;
            double parentRatio = 1.0;
            double parentMaxRatio = 1.0;

            String metrics = clientRequest.request(yarnSchema + "/ws/v1/cluster/metrics");
            JSONObject metricsJsonObject = (JSONObject) JSONObject.parseObject(metrics).get("clusterMetrics");
            vcoresCapacity = metricsJsonObject.getLongValue("availableVirtualCores");
            vcoresMaxCapacity = metricsJsonObject.getLongValue("totalVirtualCores");
            memoryGCapacity = SizeConvertUtil.mbToGB(metricsJsonObject.getLongValue("availableMB"));
            memoryMaxCapacity = SizeConvertUtil.mbToGB(metricsJsonObject.getLongValue("totalMB"));

            yarnQuota = new YarnQuota(queueName, queueName, vcoresCapacity, memoryGCapacity, vcoresMaxCapacity, memoryMaxCapacity, globalRatio, globalMaxRatio, parentRatio, parentMaxRatio);
        }
        Queue root = new Queue(queueName, yarnQuota, null);
        try {
            getChildrenQueue(schedulerInfo, root, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
    /**
     * 按照队列名字在root中搜索队列
     *
     * @param currentName 当前队列的名字
     * @param root        root队列
     * @return
     */
    public Queue searchQueue(String currentName, Queue root) {
        if (StringUtils.equals(currentName, root.getYarnQuota().getFullQueueName())) {
            return root;
        } else if (root.getChildren() == null || root.getChildren().size() == 0)
            return null;
        else {
            for (Queue queue : root.getChildren()) {
                Queue result = searchQueue(currentName, queue);
                if (result != null) return result;
            }
            return null;
        }
    }
    /**
     * 获取队列的详细信息
     *
     * @param queueName 队列名字
     * @return
     */
    @Override
    public Queue getQueueDetail(String queueName) {
        Queue root = getQueueList(true);
        Queue currentQueue = searchQueue(queueName, root);
        return currentQueue;
    }

}
