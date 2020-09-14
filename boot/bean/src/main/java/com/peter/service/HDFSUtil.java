package com.peter.service;

import com.peter.hdfs.Acl;
import com.peter.hdfs.HDFSProperty;
import com.peter.hdfs.HDFSQuota;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lcc
 * @date 2020/7/23 下午5:18
 */
public interface HDFSUtil {
    boolean init(HDFSProperty hdfsProperty);
    boolean mkdirs(String dir)throws IOException;
    boolean touchz(String file);
    boolean cp(String src, String des);
    void mv(String src, String des);
    void rm(String file, boolean r);
    String cat(String file) throws IOException;
    Map<String, List<String>> lsMessily(String file, boolean r) throws IOException;
    List<String> ls(String file, boolean r) throws IOException;
    void put(String src, String des) throws IOException;
    void get(String src, String des) throws IOException;
    String stat(String file) throws IOException;
    String tail(String file) throws IOException;
    String du(String file) throws IOException;
    long getChildFileLen(String childFile) throws IOException;
    String df() throws IOException;
    void setQuota(String dir, long namespaceQuota, long diskspaceQuota) throws IOException;
    HDFSQuota getQuota(String dir) throws IOException;
    void setAcl(String dir, List<Acl> acls) throws IOException;
    List<Acl> getAcls(String dir, String user) throws IOException;

}
