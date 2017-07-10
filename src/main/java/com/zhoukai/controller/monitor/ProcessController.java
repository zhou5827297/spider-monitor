package com.zhoukai.controller.monitor;


import com.zhoukai.controller.CommonAbstractControllerImpl;
import com.zhoukai.service.monitor.ProcessService;
import com.zhoukai.entity.Process;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("process")
public class ProcessController extends CommonAbstractControllerImpl<Process, ProcessService> {


}