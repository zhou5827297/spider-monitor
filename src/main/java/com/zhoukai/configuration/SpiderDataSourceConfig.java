package com.zhoukai.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = SpiderDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "spiderSqlSessionFactory")
public class SpiderDataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(SpiderDataSourceConfig.class);

    static final String PACKAGE = "com.zhoukai.mapper.spider";
    static final String MAPPER_LOCATION = "classpath:mybatis/spider/*-mapper.xml";
    static final String CONFIG_LOCATION = "classpath:mybatis/mybatis-conf.xml";

    @Value("${spring.spider-datasource.url}")
    private String dbUrl;

    @Value("${spring.spider-datasource.username}")
    private String username;

    @Value("${spring.spider-datasource.password}")
    private String password;

    @Value("${spring.spider-datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.spider-datasource.initialSize}")
    private int initialSize;

    @Value("${spring.spider-datasource.minIdle}")
    private int minIdle;

    @Value("${spring.spider-datasource.maxActive}")
    private int maxActive;

    @Value("${spring.spider-datasource.maxWait}")
    private int maxWait;

    @Value("${spring.spider-datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.spider-datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.spider-datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.spider-datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.spider-datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.spider-datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.spider-datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.spider-datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.spider-datasource.filters}")
    private String filters;

    @Value("${spring.spider-datasource.connectionProperties}")
    private String connectionProperties;

    @Value("${spring.spider-datasource.removeAbandoned}")
    private boolean removeAbandoned;

    @Value("${spring.spider-datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;

    @Value("${spring.spider-datasource.logAbandoned}")
    private boolean logAbandoned;

    @Bean("spiderDataSource")     //声明其为Bean实例
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setRemoveAbandoned(removeAbandoned);
        datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        datasource.setLogAbandoned(logAbandoned);

        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean(name = "spiderTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "spiderSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolve = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolve.getResources(MAPPER_LOCATION));
        sessionFactory.setConfigLocation(resolve.getResource(CONFIG_LOCATION));
        return sessionFactory.getObject();
    }

}
