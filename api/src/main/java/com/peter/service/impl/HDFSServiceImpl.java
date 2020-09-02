package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.HDFS;
import com.peter.mapper.HDFSMapper;
import com.peter.service.HDFSService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/8/24 下午4:55
 */
@Service("hdfsService")
public class HDFSServiceImpl implements HDFSService {
    private static final Log LOG= LogFactory.getLog(HDFSServiceImpl.class);

    @Autowired
    private HDFSMapper hdfsMapper;

    @Override
    public boolean insert(HDFS bean) {
        int i = hdfsMapper.insertSelective(bean);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int d = hdfsMapper.deleteByPrimaryKey(id);
        return d==1;
    }

    @Override
    public boolean update(HDFS bean) {
        int u = hdfsMapper.updateByPrimaryKeySelective(bean);
        return u==1;
    }

    @Override
    public HDFS get(Integer id) {
        HDFS hdfs = hdfsMapper.selectByPrimaryKey(id);
        return hdfs;
    }

    @Override
    public List<HDFS> getAll() {
        List<HDFS> hdfsList = hdfsMapper.selectByExample(null);
        return hdfsList;
    }

    @Override
    public PageInfo<HDFS> getPage(int pageCurrent, int pageSize) {
        PageHelper.startPage(pageCurrent,pageSize);
        List list = hdfsMapper.selectByExample(null);
        PageInfo<HDFS> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
