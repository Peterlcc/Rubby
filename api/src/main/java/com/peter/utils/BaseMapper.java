package com.peter.utils;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lcc
 * @date 2020/8/24 下午5:14
 */
public interface BaseMapper<R,E> {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Integer id);

    int insert(R record);

    int insertSelective(R record);

    List<R> selectByExample(E example);

    R selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") R record, @Param("example") E example);

    int updateByExample(@Param("record") R record, @Param("example") E example);

    int updateByPrimaryKeySelective(R record);

    int updateByPrimaryKey(R record);
}
