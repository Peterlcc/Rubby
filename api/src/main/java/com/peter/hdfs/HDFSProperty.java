package com.peter.hdfs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lcc
 * @date 2020/7/23 下午3:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HDFSProperty  implements Serializable {
    private String keyUser = "hdfs";
    private String keyPath = "/home/hdfs/hdfs.keytab";
    private boolean isKerberos = false;

    private String user = "hdfs";
    private String url = "hdfs://ambari:8020";


}
