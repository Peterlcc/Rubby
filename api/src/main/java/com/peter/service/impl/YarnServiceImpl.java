package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.bean.Yarn;
import com.peter.mapper.YarnMapper;
import com.peter.service.YarnService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lcc
 * @date 2020/8/24 下午5:22
 */
@Service
public class YarnServiceImpl implements YarnService {
    private static final Log LOG= LogFactory.getLog(YarnServiceImpl.class);

    @Autowired
    private YarnMapper yarnMapper;

    @Override
    public boolean insert(Yarn bean) {
        int i = yarnMapper.insertSelective(bean);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int d = yarnMapper.deleteByPrimaryKey(id);
        return d==1;
    }

    @Override
    public boolean update(Yarn bean) {
        int u = yarnMapper.updateByPrimaryKeySelective(bean);
        return u==1;
    }

    @Override
    public Yarn get(Integer id) {
        Yarn yarn = yarnMapper.selectByPrimaryKey(id);
        return yarn;
    }

    @Override
    public List<Yarn> getAll() {
        List<Yarn> yarns = yarnMapper.selectByExample(null);
        return yarns;
    }

    @Override
    public PageInfo<Yarn> getPage(int pageCurrent, int pageSize) {
        PageHelper.startPage(pageCurrent,pageSize);
        List list = yarnMapper.selectByExample(null);
        PageInfo<Yarn> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
