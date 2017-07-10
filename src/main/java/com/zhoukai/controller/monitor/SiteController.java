package com.zhoukai.controller.monitor;


import com.zhoukai.controller.CommonAbstractControllerImpl;
import com.zhoukai.service.monitor.SiteService;
import com.zhoukai.entity.Site;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("site")
public class SiteController extends CommonAbstractControllerImpl<Site, SiteService> {


}