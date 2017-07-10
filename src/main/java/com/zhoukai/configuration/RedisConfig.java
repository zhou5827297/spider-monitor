package com.zhoukai.configuration;


import com.zhoukai.util.RedisInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.default.db}")
    private int default_db;

    @Value("${redis.pool.maxActive}")
    private int pool_maxactive;

    @Value("${redis.pool.maxIdle}")
    private int pool_maxidle;

    @Value("${redis.pool.maxWait}")
    private int pool_maxWait;

    @Value("${redis.pool.testOnBorrow}")
    private boolean pool_testOnBorrow;

    @Bean(name = "redisInstance")
    @Primary
    public RedisInstance dataSource() {
        RedisInstance redisInstance = new RedisInstance();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(pool_maxactive);
        jedisPoolConfig.setMaxIdle(pool_maxidle);
        jedisPoolConfig.setMaxWaitMillis(pool_maxWait);
        jedisPoolConfig.setTestOnBorrow(pool_testOnBorrow);
        JedisShardInfo shardInfo = new JedisShardInfo(host, port, timeout);
        if (StringUtils.hasText(password)) {
            shardInfo.setPassword(password);
        }
        JedisPool pool = new JedisPool(jedisPoolConfig, shardInfo.getHost(), shardInfo.getPort(), shardInfo.getTimeout(), shardInfo.getPassword(), default_db);
        redisInstance.setPool(pool);
        redisInstance.setShardInfo(shardInfo);
        return redisInstance;
    }
}
