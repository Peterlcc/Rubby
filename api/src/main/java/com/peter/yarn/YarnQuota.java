package com.peter.yarn;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class YarnQuota implements Serializable {
    private String queue=null;
    @JSONField(name = "full_queue_name")
    private String fullQueueName=null;
    @JSONField(name = "vcores_capacity")
    private long vcoresCapacity=0;
    @JSONField(name = "memory_g_capacity")
    private long memoryGCapacity=0;
    @JSONField(name = "vcores_max_capacity")
    private long vcoresMaxCapacity=0;
    @JSONField(name = "memory_g_max_capacity")
    private long memoryMaxCapacity=0;
    @JSONField(name = "global_ratio")
    private double globalRatio=0;
    @JSONField(name = "global_max_ratio")
    private double globalMaxRatio=0;
    @JSONField(name = "parent_ratio")
    private double parentRatio=0;
    @JSONField(name = "parent_max_ratio")
    private double parentMaxRatio=0;

    public static YarnQuota GetDefault()
    {
        return new YarnQuota("default","root.default",0,0,0,0,0,0,0,0);
    }


    public boolean checkParentRatio(){
        if (parentRatio<=0||parentMaxRatio<=0){
            return false;
        }
        if (parentMaxRatio>=parentRatio)
            return true;
        else
            return false;
    }
}
