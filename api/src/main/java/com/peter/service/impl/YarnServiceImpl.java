package com.peter.service.impl;

import com.peter.bean.Yarn;
import com.peter.mapper.YarnMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * @author lcc
 * @date 2020/8/24 下午5:22
 */
@Service
public class YarnServiceImpl extends BaseServiceImpl<Yarn, YarnMapper> {
    private static final Log LOG= LogFactory.getLog(YarnServiceImpl.class);
}
