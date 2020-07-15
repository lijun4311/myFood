package com.mhs66;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *description:
 * springboot启动类
 *@author 76442
 *@date 2020-07-15 18:54
 */
@SpringBootApplication
@MapperScan("com.mhs66.mapper")
@ComponentScan(basePackages = {"com.mhs66", "org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
