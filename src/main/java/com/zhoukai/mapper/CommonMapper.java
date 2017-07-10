package com.zhoukai.mapper;

import com.github.pagehelper.Page;
import com.zhoukai.entity.SpiderFetchCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公共mapper
 */
public interface CommonMapper<E> {
    int deleteByPrimaryKey(Long id);

    int insert(E record);

    int insertSelective(E record);

    E selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);

    Page<E> select(E e);

    List<SpiderFetchCount> pushFetchCountByDate(@Param("date") String date);

    List<SpiderFetchCount> pushFetchCountRentenly(@Param("date") String date);

    Long getTotalByDate(@Param("date") String date);

    List<SpiderFetchCount> pushFetchResultByDate(@Param("date") String date);
}
