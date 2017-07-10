package com.zhoukai.service.spider;

import com.zhoukai.service.CommonService;
import com.zhoukai.vo.Data;

public interface SpiderFetchCountService extends CommonService<String> {
    Data pushSourceFetchCountByDate(String date);

    Data pushSourceFetchCountRentenly(String date);
}
