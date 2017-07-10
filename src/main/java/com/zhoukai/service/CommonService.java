package com.zhoukai.service;

import com.zhoukai.vo.Data;
import com.zhoukai.vo.Response;

import java.util.List;

/**
 * 公共sevice方法
 */
public interface CommonService<E> {
    int deleteByPrimaryKey(Long id);

    int insert(E record);

    int insertSelective(E record);

    E selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);

    List<E> select(E e);

    Response select(E e, int pageNum, int pageSize);

    Data pushFetchCountByDate(String date);

    Data pushFetchCountRentenly(String date);

    Data pushFetchResultByDate(String date);
}
