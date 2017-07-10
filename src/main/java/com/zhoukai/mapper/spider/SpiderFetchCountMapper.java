package com.zhoukai.mapper.spider;

import com.zhoukai.entity.SpiderFetchCount;
import com.zhoukai.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpiderFetchCountMapper extends CommonMapper<String> {

    List<SpiderFetchCount> pushSourceFetchCountByDate(@Param("date") String date);

    List<SpiderFetchCount> pushSourceFetchCountRentenly(@Param("date") String date);

}
