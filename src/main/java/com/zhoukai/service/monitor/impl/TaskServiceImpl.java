package com.zhoukai.service.monitor.impl;

import com.zhoukai.entity.Task;
import com.zhoukai.mapper.monitor.TaskMapper;
import com.zhoukai.service.CommonAbstractServiceImpl;
import com.zhoukai.service.monitor.TaskService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TaskServiceImpl extends CommonAbstractServiceImpl<Task, TaskMapper> implements TaskService {

}
