package com.peter.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lcc
 * @date 2020/7/9 下午7:32
 */
public class ResourceCalcUtil {
    public static long calcAbsoluteCapacity(JSONObject jsonObject, long parentCapacity, double parentRatio, String key) {
        return calcAbsoluteCapacity(jsonObject,parentCapacity,parentRatio,key,false);
    }
    public static long calcAbsoluteCapacity(JSONObject jsonObject, long parentCapacity, double parentRatio, String key, boolean needed){
        if (jsonObject==null){
            double value = parentCapacity * parentRatio;
            return Math.round(value);
        }else {
            long value = jsonObject.getLongValue(key);
            return needed? SizeConvertUtil.mbToGB(value):value;
        }
    }
}
