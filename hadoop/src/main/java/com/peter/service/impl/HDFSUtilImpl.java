package com.peter.service.impl;


import com.peter.hdfs.Acl;
import com.peter.hdfs.HDFSProperty;
import com.peter.hdfs.HDFSQuota;
import com.peter.service.HDFSUtil;
import com.peter.utils.SizeConvertUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("hdfsUtilImpl")
public class HDFSUtilImpl implements HDFSUtil {
    private static final Log LOG = LogFactory.getLog(HDFSUtilImpl.class);
    
    private HDFSProperty hdfsProperty=null;

    private String strUri = null;
    private String strHadoopTmp = null;
    private FileSystem fs = null;
    private String user = null;
    private Configuration conf = new Configuration();

    private static final Map<String, AclEntryType> typeMap=new HashMap<String,AclEntryType>();
    private static final String[] permissions={"user","group","mask","other"};
    static {
        typeMap.put("user",AclEntryType.USER);
        typeMap.put("group",AclEntryType.GROUP);
        typeMap.put("mask",AclEntryType.MASK);
        typeMap.put("other",AclEntryType.OTHER);
    }

    private boolean login(){
        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
        conf.setBoolean("hadoop.security.authentication",true);
        conf.set("hadoop.security.authentication","kerberos");
        conf.set("dfs.namenode.kerberos.principal.pattern", "nn/ambari@HADOOP.COM");
        String kerUser =hdfsProperty.getKeyUser();
        String keyPath = hdfsProperty.getKeyPath();
        UserGroupInformation.setConfiguration(conf);
        try {
            UserGroupInformation.loginUserFromKeytab(kerUser, keyPath);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    private void initFS() throws IOException, URISyntaxException, InterruptedException {
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", LocalFileSystem.class.getName());
        conf.set("fs.webhdfs.impl",org.apache.hadoop.hdfs.web.WebHdfsFileSystem.class.getName());
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");

        System.setProperty("HADOOP_USER_NAME", "hdfs");
        if (hdfsProperty.isKerberos()){
            login();
        }
        if (System.getProperty("os.name").equalsIgnoreCase("windows")) {
            System.setProperty("hadoop.tmp.dir", strHadoopTmp);
        }
        this.fs = FileSystem.get(new URI(strUri), conf);
        LOG.info("filesystem inited,with "+String.join("-",strUri,user));
    }

    @Override
    public boolean init(HDFSProperty hdfsProperty) {
        if (this.hdfsProperty!=null&&this.hdfsProperty.equals(hdfsProperty)){
            LOG.info("hdfsProperty is same,no need to init again!");
            return true;
        }
        LOG.info("hdfsProperty init:[origin:"+this.hdfsProperty+",now:"+hdfsProperty);
        this.hdfsProperty = hdfsProperty;
        this.strUri = hdfsProperty.getUrl();
        this.strHadoopTmp = null;
        this.user = hdfsProperty.getUser();
        try {
            initFS();
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    public String getStrUri() {
        return strUri;
    }

    public void setStrUri(String strUri) {
        this.strUri = strUri;
    }

    public String getStrHadoopTmp() {
        return strHadoopTmp;
    }

    public void setStrHadoopTmp(String strHadoopTmp) {
        this.strHadoopTmp = strHadoopTmp;
    }

    public FileSystem getFs() {
        return fs;
    }

    /**
     * @param filePath 文件的路径
     * @return 文件是否存在
     *
     */
    public boolean checkFileExist(String filePath) {
        try {
            return fs.exists(new Path(filePath));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return false;
    }



    /**
     * 创建文件夹
     *
     * @param dir 文件夹的路径
     * @return 是否成功创建
     */
    public boolean mkdirs(String dir) throws IOException {
        if (checkFileExist(dir)) {
            LOG.info("dir "+dir+" is already exist!");
            return false;
        }
        Path f = new Path(dir);
        LOG.info("Create and Write :" + f.getName() + " to hdfs");
        return fs.mkdirs(f);
    }

    /**
     * 创建文件
     *
     * @param file 文件路径
     * @return 是否成功创建文件
     */
    public boolean touchz(String file) {
        Path f = new Path(file);
        FSDataOutputStream oStream = null;
        try {
            oStream = fs.create(f, true);
            oStream.close();
            return true;
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            if (oStream != null) {
                try {
                    oStream.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     *
     * @param src 原路径
     * @param des 目标路径
     * @return 是否成功复制
     */
    public boolean cp(String src, String des) {
        Path srcPath = new Path(src);
        Path desPath = new Path(des);
        try {
            FileUtil.copy(srcPath.getFileSystem(conf), srcPath, desPath.getFileSystem(conf), desPath, false, conf);
            return true;
        } catch (IOException e) {
            LOG.error(e.getMessage());;
            return false;
        }
    }

    /**
     *
     * @param src 原路径
     * @param des 目标路径
     */
    public void mv(String src, String des) {
        Path srcPath = new Path(src);
        Path desPath = new Path(des);
        try {
            fs.rename(srcPath, desPath);
        } catch (IOException e) {
            LOG.error(e.getMessage());;
        }
    }

    /**
     *
     * @param file 文件或者文件夹
     */
    public void rm(String file, boolean r) {
        Path f = new Path(file);
        try {
            fs.delete(f, r);
            LOG.info("delete file : " + file);
        } catch (IOException e) {
            LOG.error(e.getMessage());;
        }
    }

    /**
     *
     * @param file 文件路径
     * @throws IOException
     */
    public String cat(String file) throws IOException {
        if (!checkFileExist(file)) {
            LOG.info("file not exits!");
            return null;
        }
        Path path = new Path(file);
        FSDataInputStream inputStream = fs.open(path);
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer);
        return writer.toString();
    }

    /**
     *
     * @param file 路径
     * @param r    是否递归
     * @return 文件路径列表
     * @throws IOException
     */
    public Map<String,List<String>> lsMessily(String file, boolean r) throws IOException {
        List<String> ls = ls(file, r);
        Path path = new Path(file);
        List<String> files = new ArrayList<String>();
        List<String> dirs = new ArrayList<String>();
        Map<String,List<String>> result=new HashMap<>();
        FileStatus status = fs.getFileStatus(path);
        if (status.isFile()) {
            files.add(path.getName());
            result.put("hdfs_file_names",files);
            return result;
        }
        FileStatus[] listStatus = fs.listStatus(path);
        for (FileStatus fileStatus:listStatus){
            if (fileStatus.isDirectory()){
                dirs.add(fileStatus.getPath().getName());
            }else {
                files.add(fileStatus.getPath().getName());
            }
        }
        result.put("hdfs_dir_names",dirs);
        result.put("hdfs_file_names",files);
        return result;
    }

    /**
     *
     * @param file 路径
     * @param r    是否递归
     * @return 文件路径列表
     * @throws IOException
     */
    public List<String> ls(String file, boolean r) throws IOException {
        Path path = new Path(file);
        List<String> list = new ArrayList<String>();
        FileStatus status = fs.getFileStatus(path);
        if (status.isFile()) {
            list.add(path.getName());
            return list;
        }
        FileStatus[] listStatus = fs.listStatus(path);
        for (FileStatus fileStatus:listStatus){
            list.add(fileStatus.getPath().toUri().getPath());
        }
        return list;
    }

    /**
     *
     * @param src 本地路径
     * @param des hdfs路径
     * @throws IOException
     */
    public void put(String src, String des) throws IOException {
        Path srcPath = new Path(src);
        Path desPath = new Path(des);
        fs.copyFromLocalFile(srcPath, desPath);
        LOG.info("upload "+src+" --> "+des);
    }

    /**
     *
     * @param src hdfs路径
     * @param des 本地路径
     * @throws IOException
     */
    public void get(String src, String des) throws IOException {
        Path srcPath = new Path(src);
        Path desPath = new Path(des);
        fs.copyToLocalFile(srcPath, desPath);
        LOG.info("download "+src+" --> "+des);
    }

    /**
     *
     * @param file 路径
     * @return 文件最后修改时间
     * @throws IOException
     */
    public String stat(String file) throws IOException {
        Path path = new Path(file);
        FileStatus fileStatus = fs.getFileStatus(path);
        long time = fileStatus.getModificationTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    /**
     *
     * @param file 文件路径
     * @return
     * @throws IOException
     */
    public String tail(String file) throws IOException {
        Path path = new Path(file);
        FileStatus fileStatus = fs.getFileStatus(path);
        long len = fileStatus.getLen();
        FSDataInputStream inputStream = fs.open(path);
        inputStream.seek(len - 1024);
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer);
        return writer.toString();
    }

    /**
     * 获取文件或者文件夹的大小信息
     *
     * @param file 文件或者文件夹路径
     * @return 信息
     * @throws IOException
     */
    public String du(String file) throws IOException {
        FileStatus[] files = fs.listStatus(new Path(file));
        StringBuilder stringBuilder = new StringBuilder();
        for (FileStatus f : files) {
            Path path = f.getPath();
            long len = getChildFileLen(path.toUri().getPath());
            String convert = SizeConvertUtil.Convert(len);
            stringBuilder.append(convert);
            stringBuilder.append("\t");
            stringBuilder.append(path.toUri().getPath());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
    /**
     * 获取目录或者文件的大小
     * @param childFile 子目录或者文件
     * @return 大小(bytes)
     * @throws IOException
     */
    public long getChildFileLen(String childFile) throws IOException {
        long len = 0;
        List<String> list = ls(childFile, true);
        for (String f : list) {
            Path path = new Path(f);
            FileStatus fileStatus = fs.getFileStatus(path);
            if (fileStatus.isFile()) {
                len += fileStatus.getLen();
            } else
                len += getChildFileLen(f);
        }
        return len;
    }

    /**
     * 查看Hadoop空间大小信息
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String df() throws IOException {
        Path homeDirectory = fs.getHomeDirectory();
        FsStatus status = fs.getStatus(homeDirectory);
        long capacity = status.getCapacity();
        long used = status.getUsed();
        long available = capacity - used;
        double percentUsed = (double) used / capacity;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Filesystem\t\t");
        stringBuilder.append("Size\t");
        stringBuilder.append("Used\t");
        stringBuilder.append("Available\t");
        stringBuilder.append("Use%\n");
        stringBuilder.append(strUri + "\t");
        stringBuilder.append(SizeConvertUtil.Convert(capacity) + "\t");
        stringBuilder.append(SizeConvertUtil.Convert(used) + "\t");
        stringBuilder.append(SizeConvertUtil.Convert(available) + "\t");
        NumberFormat nbf = NumberFormat.getInstance();
        nbf.setMinimumFractionDigits(2);
        stringBuilder.append(nbf.format(percentUsed) + "\t");

        return stringBuilder.toString();
    }

    /**
     * 设置dir的name quota和space quota
     * @param dir 目录
     * @param namespaceQuota 名称配额 对某个目录中文件和目录的总数目进行严格限制 -1表示没有限制
     * @param diskspaceQuota 空间配额 对某个目录下文件总大小进行的严格限制 -1表示没有限制
     * @throws IOException
     */
    public void setQuota(String dir,long namespaceQuota,long diskspaceQuota) throws IOException {
        HdfsAdmin hdfsAdmin=new HdfsAdmin(fs.getUri(),fs.getConf());
        Path path=new Path(dir);
        hdfsAdmin.setQuota(path,namespaceQuota);
        hdfsAdmin.setSpaceQuota(path,diskspaceQuota);
    }

    /**
     * 获取dir目录的配额信息
     * @param dir hdfs目录
     * @return
     * @throws IOException
     */
    public HDFSQuota getQuota(String dir) throws IOException {
        Path path=new Path(dir);
        ContentSummary contentSummary = fs.getContentSummary(path);
        long quota = contentSummary.getQuota();
        long fileCount = contentSummary.getFileCount()+contentSummary.getDirectoryCount();
        long spaceQuota = contentSummary.getSpaceQuota();
        long spaceConsumed = contentSummary.getSpaceConsumed();
        return new HDFSQuota(dir, SizeConvertUtil.bytesToGB(spaceQuota),quota, SizeConvertUtil.bytesToGB(spaceConsumed),fileCount);
    }

    /**
     * 根据aclEntries列表生成<aclEntry.toString(),aclEntry>的map
     * @param aclEntries
     * @return
     */
    private Map<String,AclEntry> getMapFromAcls(List<AclEntry> aclEntries){
        Map<String,AclEntry> aclEntryMap=new HashMap<>();
        aclEntries.forEach(aclEntry -> {
            if (aclEntry.getType()==AclEntryType.USER){
                aclEntryMap.put(aclEntry.toString(),aclEntry);
            }
        });
        return aclEntryMap;
    }
    /**
     * 设置访问dir的控制列表
     * @param dir 目录
     * @param acls acl列表
     * @throws IOException
     */
    public void setAcl(String dir,List<Acl> acls) throws IOException {
        Path path = new Path(dir);
        List<AclEntry> aclEntries=new ArrayList<>();
        for (Acl acl:acls){
            AclEntry.Builder builder = new AclEntry.Builder();
            builder.setPermission(FsAction.values()[acl.generatePermission()]);
            builder.setType(typeMap.get(acl.getType()));
            builder.setScope(AclEntryScope.ACCESS);
            builder.setName(acl.getUser());
            AclEntry aclEntry = builder.build();
            aclEntries.add(aclEntry);
        }
        AclStatus aclStatus = fs.getAclStatus(path);
        List<AclEntry> aclEntriesOld = aclStatus.getEntries();
        Map<String, AclEntry> mapFromAcls = getMapFromAcls(aclEntries);
        Map<String, AclEntry> mapFromAclsOld = getMapFromAcls(aclEntriesOld);
        Set<String> oldSet = new HashSet<>(mapFromAclsOld.keySet());
        Set<String> newSet = new HashSet<>(mapFromAcls.keySet());
        oldSet.removeAll(newSet);
        if (oldSet.size()>0){
            List<AclEntry> aclDeleteEntries=new ArrayList<>();
            for (String key:oldSet){
                aclDeleteEntries.add(mapFromAclsOld.get(key));
            }
            fs.removeAclEntries(path,aclDeleteEntries);
        }
        fs.modifyAclEntries(path,aclEntries);
    }

    /**
     * 解析rwx这样的字符串，生成Privileges列表
     * @param permission 权限字符串
     * @return
     */
    private List<String> getPrivilegesFromPermission(String permission){
        if (permission.length()!=3) return null;
        List<String> privileges=new ArrayList<>();
        for (int i=0;i<permission.length();i++)
        {
            switch (permission.charAt(i)){
                case '-':
                    break;
                case 'r':
                    privileges.add("read");
                    break;
                case 'w':
                    privileges.add("write");
                    break;
                case 'x':
                    privileges.add("execute");
                    break;
            }
        }
        return privileges;
    }

    /**
     * 获取dir目录的访问控制列表
     * @param dir hdfs目录
     * @return
     * @throws IOException
     */
    public List<Acl> getAcls(String dir, String user) throws IOException {
        List<Acl> acls=new ArrayList<>();
        Path path = new Path(dir);
        AclStatus aclStatus = fs.getAclStatus(path);
        List<AclEntry> aclEntries = aclStatus.getEntries();
//
//        String group = aclStatus.getGroup();
//        String owner = aclStatus.getOwner();
//        LOG.info(aclEntries.size());
//        LOG.info(owner);
//        LOG.info(group);

        for (AclEntry aclEntry:aclEntries){
//            LOG.info(aclEntry);
            Acl acl = new Acl(permissions[aclEntry.getType().ordinal()], aclEntry.getName(),null,
                    getPrivilegesFromPermission(aclEntry.getPermission().SYMBOL));
            acls.add(acl);
        }
        return acls;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        fs.close();
    }

}
