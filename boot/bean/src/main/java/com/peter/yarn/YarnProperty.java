package com.peter.yarn;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lcc
 * @date 2020/7/24 下午7:09
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class YarnProperty implements Serializable {
    private String yarnSchema="http://ambari:8088";
    private String kerberosUser="hdfs";
    private String kerberosKeytab="/home/hdfs/hdfs.keytab";
    private boolean kerberos=false;
    private String platform="ambari";
}
