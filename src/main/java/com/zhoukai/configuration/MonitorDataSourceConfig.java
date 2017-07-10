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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = MonitorDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "monitorSqlSessionFactory")
public class MonitorDataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(MonitorDataSourceConfig.class);

    static final String PACKAGE = "com.zhoukai.mapper.monitor";
    static final String MAPPER_LOCATION = "classpath:mybatis/monitor/*-mapper.xml";
    static final String CONFIG_LOCATION = "classpath:mybatis/mybatis-conf.xml";

    @Value("${spring.monitor-datasource.url}")
    private String dbUrl;

    @Value("${spring.monitor-datasource.username}")
    private String username;

    @Value("${spring.monitor-datasource.password}")
    private String password;

    @Value("${spring.monitor-datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.monitor-datasource.initialSize}")
    private int initialSize;

    @Value("${spring.monitor-datasource.minIdle}")
    private int minIdle;

    @Value("${spring.monitor-datasource.maxActive}")
    private int maxActive;

    @Value("${spring.monitor-datasource.maxWait}")
    private int maxWait;

    @Value("${spring.monitor-datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.monitor-datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.monitor-datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.monitor-datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.monitor-datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.monitor-datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.monitor-datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.monitor-datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.monitor-datasource.filters}")
    private String filters;

    @Value("${spring.monitor-datasource.connectionProperties}")
    private String connectionProperties;

    @Value("${spring.monitor-datasource.removeAbandoned}")
    private boolean removeAbandoned;

    @Value("${spring.monitor-datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;

    @Value("${spring.monitor-datasource.logAbandoned}")
    private boolean logAbandoned;

    @Bean(name = "monitorDataSource")     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
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


    @Bean(name = "monitorTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "monitorSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolve = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolve.getResources(MAPPER_LOCATION));
        sessionFactory.setConfigLocation(resolve.getResource(CONFIG_LOCATION));
        return sessionFactory.getObject();
    }

}
