package com.zhoukai.controller.zookeeper;

import com.zhoukai.vo.ZookeeperData;
import com.zhoukai.zookeeper.SpiderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 配置文件控制器
 */
@RestController
@RequestMapping("spiderConfig")
public class SpiderConfigController {

    @Autowired
    private SpiderConfig spiderConfig;

    @RequestMapping("/getValue")
    public List<ZookeeperData> getValue(@RequestParam(name = "path") String path) {
        return spiderConfig.getConfig(path);
    }

    @RequestMapping("/getAll")
    public List<ZookeeperData> getAll(@RequestParam(name = "path") String path) {
        return spiderConfig.getAll(path);
    }

    @RequestMapping("/setValue")
    public void setValue(@RequestParam(name = "path") String path, @RequestParam(name = "value") String value) {
        spiderConfig.setConfig(path, value);
    }
}
