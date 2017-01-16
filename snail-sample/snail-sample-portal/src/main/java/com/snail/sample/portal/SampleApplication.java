package com.snail.sample.portal;

import com.snail.dao.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.snail.sample.dao", markerInterface = BaseMapper.class)
@ComponentScan(basePackages = {"com.snail.sample.controller", "com.snail.sample.impl"})
public class SampleApplication {


    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

}
