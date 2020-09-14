package com.peter.service;

import com.peter.yarn.Queue;
import com.peter.yarn.YarnProperty;

/**
 * @author lcc
 * @date 2020/7/24 下午7:05
 */
public interface YarnUtil {
    String getSchedulerFromRest();
    Queue getQueueList(boolean detail);
    Queue getQueueDetail(String queueName);
    boolean init(YarnProperty yarnProperty);
}
