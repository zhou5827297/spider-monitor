package com.zhoukai.controller;

import com.zhoukai.service.CommonService;
import com.zhoukai.vo.Data;
import com.zhoukai.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public abstract class CommonAbstractControllerImpl<T, E extends CommonService> implements CommonController<T> {

    @Autowired
    protected E service;

    @RequestMapping("/get")
    @Override
    public Response get(T model,
                        @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return service.select(model, pageNum, pageSize);
    }

    @RequestMapping("/pushFetchCountByDate")
    public Data pushFetchCountByDate(@RequestParam String date) {
        Data data = service.pushFetchCountByDate(date);
        return data;
    }

    @RequestMapping("/pushFetchCountRentenly")
    public Data pushFetchCountRentenly(@RequestParam String date) {
        Data data = service.pushFetchCountRentenly(date);
        return data;
    }

    @RequestMapping("/pushFetchResultByDate")
    public Data pushFetchResultByDate(String date) {
        return service.pushFetchResultByDate(date);
    }

}
