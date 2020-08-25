package com.peter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class HDFS {
    private Integer id;

    private String name;

    private String url;

    private String user;

    private String kerberos;

    private String kerberosUser;

    private String kerberosPath;
}