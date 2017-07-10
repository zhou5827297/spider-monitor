package com.zhoukai.mq.publisher;

import com.zhoukai.util.RedisInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发布
 */
@Component
public class Publisher {
    private final static Logger LOG = LoggerFactory.getLogger(Publisher.class);

    @Autowired
    private RedisInstance redisInstance;

    public boolean publish(String channel, String json) {
        for (int i = 0; i < 3; i++) {
            try {
                redisInstance.getJedis().publish(channel, json);
                return true;
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            } finally {
                redisInstance.returnJedis();
            }
        }
        return false;
    }
}
