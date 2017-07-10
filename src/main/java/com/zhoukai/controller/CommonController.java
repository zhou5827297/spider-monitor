package com.zhoukai.controller;

import com.zhoukai.vo.Response;

/**
 * 公共controller
 */
public interface CommonController<E> {

    Response get(E e, int pageNum, int pageSize);
}
