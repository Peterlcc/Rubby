package com.peter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Yarn {
    private Integer id;

    private String name;

    private String url;

    private Integer platformId;

    private Boolean kerberos;

    private String kerberosUser;

    private String kerberosKeytab;

}