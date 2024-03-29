package com.ht.bpr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 16:58
 * @description
 */
@SpringBootApplication
@MapperScan("com.ht.bpr.mapper")
public class BprServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BprServiceApplication.class, args);
    }

}
