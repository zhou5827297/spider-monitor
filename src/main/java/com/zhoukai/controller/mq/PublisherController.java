package com.zhoukai.controller.mq;

import com.zhoukai.mq.publisher.Publisher;
import com.zhoukai.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 配置文件控制器
 */
@RestController
@RequestMapping("publisher")
public class PublisherController {

    private final static String CHANNEL = "spider-site"; //默认的渠道

    @Autowired
    private Publisher publisher;

    @RequestMapping("/publish")
    public Response publish(@RequestBody Map<String, String> publishMap) {
        String channel = publishMap.get("channel") == null ? CHANNEL : publishMap.get("channel");
        String content = publishMap.get("content");
        boolean success = publisher.publish(channel, content);
        Response response = new Response();
        response.setCode(success ? 1 : 0);
        return response;
    }

}
