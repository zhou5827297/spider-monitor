package com.zhoukai.controller.monitor;


import com.zhoukai.controller.CommonAbstractControllerImpl;
import com.zhoukai.entity.Task;
import com.zhoukai.service.monitor.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController extends CommonAbstractControllerImpl<Task, TaskService> {


}