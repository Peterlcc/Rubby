package com.peter.hdfs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Acl implements Serializable {
    private String type=null;
    private String user=null;
    private String tenementName=null;
    private List<String> privileges=null;

    private static final Map<String,Integer> permissionMap=new HashMap<>();
    static {
        permissionMap.put("read",4);
        permissionMap.put("write",2);
        permissionMap.put("execute",1);
    }

    public int generatePermission(){
        int num=0;
        for (String p:privileges){
            num+=permissionMap.get(p);
        }
        return num;
    }
}
