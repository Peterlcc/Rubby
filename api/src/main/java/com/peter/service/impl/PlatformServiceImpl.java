package com.peter.service.impl;

import com.peter.bean.Platform;
import com.peter.mapper.PlatformMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * @author lcc
 * @date 2020/8/24 下午5:24
 */
@Service
public class PlatformServiceImpl extends BaseServiceImpl<Platform, PlatformMapper> {
    private static final Log LOG= LogFactory.getLog(YarnServiceImpl.class);
}
