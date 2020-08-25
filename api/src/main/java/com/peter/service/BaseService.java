package com.peter.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author lcc
 * @date 2020/7/13 下午1:01
 */
public interface BaseService<T>{
    boolean insert(T bean);
    boolean delete(Integer id);
    boolean update(T bean);

    T get(Integer id);
    List<T> getAll();
    PageInfo<T> getPage(int pageCurrent, int pageSize);
}
