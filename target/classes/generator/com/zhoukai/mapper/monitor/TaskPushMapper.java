package com.zhoukai.mapper.monitor;

import com.zhoukai.entity.TaskPush;

public interface TaskPushMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskPush record);

    int insertSelective(TaskPush record);

    TaskPush selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskPush record);

    int updateByPrimaryKeyWithBLOBs(TaskPush record);

    int updateByPrimaryKey(TaskPush record);
}