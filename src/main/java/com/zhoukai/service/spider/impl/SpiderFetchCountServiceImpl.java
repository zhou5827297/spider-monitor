package com.zhoukai.service.spider.impl;

import com.zhoukai.entity.SpiderFetchCount;
import com.zhoukai.mapper.spider.SpiderFetchCountMapper;
import com.zhoukai.service.CommonAbstractServiceImpl;
import com.zhoukai.service.spider.SpiderFetchCountService;
import com.zhoukai.util.DateUtils;
import com.zhoukai.vo.Data;
import com.zhoukai.vo.Data.ItemBar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderFetchCountServiceImpl extends CommonAbstractServiceImpl<String, SpiderFetchCountMapper> implements SpiderFetchCountService {

    public Data pushSourceFetchCountByDate(String date) {
        List<SpiderFetchCount> spiderFetchCounts = mapper.pushSourceFetchCountByDate(date);
        Data data = translatBarData(spiderFetchCounts);
        long total = getTotalByDate(date, DateUtils.YMD_FORMAT);
        data.setTotal(total);
        return data;
    }

    public Data pushSourceFetchCountRentenly(String date) {
        List<SpiderFetchCount> spiderFetchCounts = mapper.pushSourceFetchCountRentenly(date);
        Data data = translatBarData(spiderFetchCounts);
        long total = getTotalByDate(date, DateUtils.YMDHMS_FORMAT);
        data.setTotal(total);
        return data;
    }

    /**
     * line数据转换
     */
    private Data translatBarData(List<SpiderFetchCount> spiderFetchCounts) {
        Data data = new Data();
        ItemBar itemBar = new ItemBar();
        String[] names = new String[spiderFetchCounts.size()];
        Long[] values = new Long[spiderFetchCounts.size()];
        for (int i = 0, j = spiderFetchCounts.size(); i < j; i++) {
            SpiderFetchCount spiderFetchCount = spiderFetchCounts.get(i);
            names[i] = spiderFetchCount.getName();
            values[i] = spiderFetchCount.getCount();
        }
        itemBar.setNames(names);
        itemBar.setValues(values);
        data.setItemBar(itemBar);
        return data;
    }
}
