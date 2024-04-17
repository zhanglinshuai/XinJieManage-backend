package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.demos.mapper"})
public class XinJieManageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(XinJieManageBackendApplication.class, args);
    }

}
