

package com.snail.dao;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Mapper 配置
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(MapperProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@EnableTransactionManagement
public class MapperAutoConfiguration {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private MapperProperties  properties;

    @PostConstruct
    public void addPageInterceptor() {
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(properties);
        if (properties.getMappers().size() > 0) {
            for (Class mapper : properties.getMappers()) {
                mapperHelper.registerMapper(mapper);
            }

            mapperHelper.registerMapper(Mapper.class);
        } else {
            mapperHelper.registerMapper(Mapper.class);
        }
        mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
        
        PageInterceptor interceptor = new PageInterceptor();
        Properties pageProperties = new Properties();
        pageProperties.putAll(properties.getPagehelper());
        interceptor.setProperties(pageProperties);
        sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
