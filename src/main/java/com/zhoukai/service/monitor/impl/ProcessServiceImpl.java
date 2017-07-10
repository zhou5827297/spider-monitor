package com.zhoukai.service.monitor.impl;

import com.zhoukai.service.monitor.ProcessService;
import com.zhoukai.service.CommonAbstractServiceImpl;
import com.zhoukai.entity.Process;
import com.zhoukai.mapper.monitor.ProcessMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class ProcessServiceImpl extends CommonAbstractServiceImpl<Process, ProcessMapper> implements ProcessService {

}
