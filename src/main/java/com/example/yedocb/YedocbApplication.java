package com.example.yedocb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.example.yedocb")
public class YedocbApplication {
    public static void main(String[] args) {
        SpringApplication.run(YedocbApplication.class, args);
    }
}
