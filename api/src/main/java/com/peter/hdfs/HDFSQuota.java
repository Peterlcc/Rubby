package com.peter.hdfs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class HDFSQuota  implements Serializable {
    private String dir=null;
    @JSONField(name = "space_quota_g")
    private long spaceQuotaG=0;
    @JSONField(name = "file_quota")
    private long fileQuota=0;
    private long used_space_quota_g=0;
    private long used_file_quota=0;
}
