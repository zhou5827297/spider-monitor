package com.zhoukai.controller.monitor;


import com.zhoukai.controller.CommonAbstractControllerImpl;
import com.zhoukai.service.monitor.TaskPushService;
import com.zhoukai.entity.TaskPush;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("taskPush")
public class TaskPushController extends CommonAbstractControllerImpl<TaskPush, TaskPushService> {

}