package com.example.yedocb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({
    "com.example.yedocb.admin.repository",
    "com.example.yedocb.reservation.repository",
    "com.example.yedocb.user.repository"
})
public class YedocbApplication {
	public static void main(String[] args) {
		SpringApplication.run(YedocbApplication.class, args);
	}
}
