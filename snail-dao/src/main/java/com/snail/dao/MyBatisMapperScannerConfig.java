package com.snail.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig {

    @Autowired
    private  MybatisProperties properties;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(properties.getBasePackage());
        Properties properties = new Properties();
        properties.setProperty("mappers", Mapper.class.getName());
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

}
