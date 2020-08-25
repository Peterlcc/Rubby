package com.peter.service.impl;

import com.peter.bean.HDFS;
import com.peter.mapper.HDFSMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * @author lcc
 * @date 2020/8/24 下午4:55
 */
@Service("hdfsService")
public class HDFSServiceImpl extends BaseServiceImpl<HDFS,HDFSMapper>{
    private static final Log LOG= LogFactory.getLog(HDFSServiceImpl.class);

}
