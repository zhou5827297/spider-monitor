package com.zhoukai.service.monitor.impl;

import com.zhoukai.service.monitor.TaskPushService;
import com.zhoukai.service.CommonAbstractServiceImpl;
import com.zhoukai.entity.TaskPush;
import com.zhoukai.mapper.monitor.TaskPushMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TaskPushServiceImpl extends CommonAbstractServiceImpl<TaskPush, TaskPushMapper> implements TaskPushService {

}
