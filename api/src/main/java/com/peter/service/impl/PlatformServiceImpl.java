package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Platform;
import com.peter.mapper.PlatformMapper;
import com.peter.service.PlatformService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/8/24 下午5:24
 */
@Service
public class PlatformServiceImpl implements PlatformService {
    private static final Log LOG= LogFactory.getLog(YarnServiceImpl.class);

    @Autowired
    private PlatformMapper platformMapper;

    @Override
    public boolean insert(Platform bean) {
        int i = platformMapper.insertSelective(bean);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int d = platformMapper.deleteByPrimaryKey(id);
        return d==1;
    }

    @Override
    public boolean update(Platform bean) {
        int u = platformMapper.updateByPrimaryKeySelective(bean);
        return u==1;
    }

    @Override
    public Platform get(Integer id) {
        Platform platform = platformMapper.selectByPrimaryKey(id);
        return platform;
    }

    @Override
    public List<Platform> getAll() {
        List<Platform> platforms = platformMapper.selectByExample(null);
        return platforms;
    }

    @Override
    public PageInfo<Platform> getPage(int pageCurrent, int pageSize) {
        PageHelper.startPage(pageCurrent,pageSize);
        List list = platformMapper.selectByExample(null);
        PageInfo<Platform> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
