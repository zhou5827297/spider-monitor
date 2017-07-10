package com.zhoukai.controller.spider;


import com.zhoukai.controller.CommonAbstractControllerImpl;
import com.zhoukai.service.spider.SpiderFetchCountService;
import com.zhoukai.service.spider.impl.SpiderFetchCountServiceImpl;
import com.zhoukai.vo.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("spiderFetchCount")
public class SpiderFetchCountController extends CommonAbstractControllerImpl<String, SpiderFetchCountService> {

    @RequestMapping("/pushSourceFetchCountRentenly")
    public Data pushSourceFetchCountRentenly(@RequestParam String date) {
        Data data = service.pushSourceFetchCountRentenly(date);
        return data;
    }

    @RequestMapping("/pushSourceFetchCountByDate")
    public Data pushSourceFetchCountByDate(@RequestParam String date) {
        Data data = service.pushSourceFetchCountByDate(date);
        return data;
    }
}