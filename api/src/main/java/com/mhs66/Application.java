package com.mhs66;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *description:
 * springboot启动类
 *@author 76442
 *@date 2020-07-15 18:54
 */
@SpringBootApplication
@MapperScan("com.mhs66.mapper")
public class Application {
    /**
     * 启动入口
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
