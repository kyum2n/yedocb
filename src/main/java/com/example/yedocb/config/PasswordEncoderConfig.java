package com.example.yedocb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * packageName    : com.example.yedocb.config
 * fileName       : PasswordEncoderConfig
 * author         : [ysg]
 * date           : [작성일자 : 2025-06-02]
 * description    : 비밀번호 암호화를 위한 PasswordEncoder Bean 등록 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-02        [ysg]              최초 생성
 */

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}