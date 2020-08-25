package com.peter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peter.service.BaseService;
import com.peter.utils.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lcc
 * @date 2020/8/24 下午5:17
 */
public class BaseServiceImpl<T,M extends BaseMapper> implements BaseService<T> {

    @Autowired
    private M baseMapper;

    @Override
    public boolean insert(T bean) {
        int i = baseMapper.insertSelective(bean);
        return i==1;
    }

    @Override
    public boolean delete(Integer id) {
        int d = baseMapper.deleteByPrimaryKey(id);
        return d==1;
    }

    @Override
    public boolean update(T bean) {
        int u = baseMapper.updateByPrimaryKeySelective(bean);
        return u==1;
    }

    @Override
    public T get(Integer id) {
        Object o = baseMapper.selectByPrimaryKey(id);
        return (T) o;
    }

    @Override
    public List<T> getAll() {
        List list = baseMapper.selectByExample(null);
        return list;
    }

    @Override
    public PageInfo<T> getPage(int pageCurrent, int pageSize) {
        PageHelper.startPage(pageCurrent,pageSize);
        List list = baseMapper.selectByExample(null);
        PageInfo<T> pageInfo=new PageInfo<>(list);

        return pageInfo;
    }
}
