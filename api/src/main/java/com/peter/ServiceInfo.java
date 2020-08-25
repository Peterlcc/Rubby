package com.peter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lcc
 * @date 2020/8/25 下午4:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceInfo {
    private String name;
    private String host;
    private int port;
    private String uri;
}
