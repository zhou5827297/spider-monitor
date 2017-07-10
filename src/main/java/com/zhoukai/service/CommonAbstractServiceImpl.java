package com.zhoukai.service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhoukai.service.CommonService;
import com.zhoukai.entity.SpiderFetchCount;
import com.zhoukai.mapper.CommonMapper;
import com.zhoukai.util.DateUtils;
import com.zhoukai.vo.Data;
import com.zhoukai.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公共sevice实现方法
 */
public abstract class CommonAbstractServiceImpl<E, T extends CommonMapper<E>> implements CommonService<E> {

    @Autowired
    protected T mapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(E record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(E record) {
        return mapper.insertSelective(record);
    }

    @Override
    public E selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(E record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(E record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<E> select(E record) {
        return mapper.select(record);
    }

    @Override
    public Response select(E e, int pageNum, int pageSize) {
        Page<E> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(new ISelect() {
            @Override
            public void doSelect() {
                select(e);
            }
        });
        Response response = new Response();
        response.setTotal(page.getTotal());
        response.setPages(page.getPages());
        response.setResult(page.getResult());
        return response;
    }

    @Override
    public Data pushFetchCountByDate(String date) {
        List<SpiderFetchCount> spiderFetchCounts = mapper.pushFetchCountByDate(date);
        Data data = translatLineData(spiderFetchCounts);
        long total = getTotalByDate(date, DateUtils.YMD_FORMAT);
        data.setTotal(total);
        return data;
    }

    @Override
    public Data pushFetchCountRentenly(String date) {
        List<SpiderFetchCount> spiderFetchCounts = mapper.pushFetchCountRentenly(date);
        Data data = translatLineData(spiderFetchCounts);
        long total = getTotalByDate(date, DateUtils.YMDHMS_FORMAT);
        data.setTotal(total);
        return data;
    }

    public Long getTotalByDate(String date, String originPartten) {
        String ymdDate = DateUtils.parseString(date, originPartten, DateUtils.YMD_FORMAT);
        Long total = mapper.getTotalByDate(ymdDate);
        return total;
    }

    @Override
    public Data pushFetchResultByDate(String date) {
        List<SpiderFetchCount> spiderFetchCounts = mapper.pushFetchResultByDate(date);
        Data data = translatPieData(spiderFetchCounts);
        return data;
    }

    /**
     * line数据转换
     */
    private Data translatLineData(List<SpiderFetchCount> spiderFetchCounts) {
        Data data = new Data();
        List<Data.Item> items = new ArrayList<Data.Item>();
        long lastTime = 0;
        for (SpiderFetchCount spiderFetchCount : spiderFetchCounts) {
            Data.Item item = new Data.Item();
            String dateStr = spiderFetchCount.getName();
            long time = 0L;
            Date date = DateUtils.parseDate(dateStr, DateUtils.YMDHMS_FORMAT);
            if (date != null) {
                time = date.getTime();
                if (time > lastTime) {
                    lastTime = time;
                }
                long addTime = 8 * 3600 * 1000; // 手动解决UTC时间问题
                time += addTime;
            }
            item.setName(time);
            Long[] value = item.getValue();
            value[0] = time;
            value[1] = spiderFetchCount.getCount();
            items.add(item);
        }
        data.setItems(items);
        data.setLastTime(lastTime);
        return data;
    }

    /**
     * pie数据转换
     */
    private Data translatPieData(List<SpiderFetchCount> spiderFetchCounts) {
        Data data = new Data();
        Data.ItemPie itemPie = new Data.ItemPie();
        String[] names = new String[spiderFetchCounts.size()];
        Data.ItemPie.KeyValue[] values = new Data.ItemPie.KeyValue[spiderFetchCounts.size()];
        for (int i = 0, j = spiderFetchCounts.size(); i < j; i++) {
            SpiderFetchCount spiderFetchCount = spiderFetchCounts.get(i);
            names[i] = spiderFetchCount.getName();
            Data.ItemPie.KeyValue keyValue = new Data.ItemPie.KeyValue();
            keyValue.setName(spiderFetchCount.getName());
            keyValue.setValue(spiderFetchCount.getCount());
            values[i] = keyValue;
        }
        itemPie.setNames(names);
        itemPie.setValues(values);
        data.setItemPie(itemPie);
        data.setLastTime(System.currentTimeMillis());
        return data;
    }

}
