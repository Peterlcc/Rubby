package com.peter.hdfs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HDFSDir implements Serializable {
    private String path=null;
    @JSONField(name = "hdfs_quota")
    private HDFSQuota hdfsQuota=null;
    private List<Acl> acls=null;
}
