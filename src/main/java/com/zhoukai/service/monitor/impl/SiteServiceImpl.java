package com.zhoukai.service.monitor.impl;

import com.zhoukai.service.monitor.SiteService;
import com.zhoukai.service.CommonAbstractServiceImpl;
import com.zhoukai.entity.Site;
import com.zhoukai.mapper.monitor.SiteMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SiteServiceImpl extends CommonAbstractServiceImpl<Site, SiteMapper> implements SiteService {


}
