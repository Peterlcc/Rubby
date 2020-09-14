package com.peter.yarn;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lcc
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Queue implements Serializable {
    private String name=null;
    @JSONField(name = "yarn_quota")
    private YarnQuota yarnQuota=null;
    @JSONField(name="queues")
    private List<Queue> children=null;
}
