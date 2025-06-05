package com.example.yedocb.config;

import com.example.yedocb.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * packageName    : com.example.yedocb.config
 * fileName       : SecurityConfig
 * author         : [ysg]
 * date           : [작성일자 : 2025-06-02]
 * description    : Spring Security 설정 (JWT + 권한별 경로 설정)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-02        [ysg]              최초 생성
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JwtAuthenticationFilter 를 주입받음 (JWT 토큰 검증용 필터)
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // SecurityFilterChain Bean 등록 (Spring Security 필터 설정)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

        		// CORS 활성화 (아래 corsConfigurationSource()와 연결됨)
        		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
          
                // CSRF 비활성화 (JWT 기반에서는 필요 없음)
                .csrf(csrf -> csrf.disable())
                
                // 세션 사용하지 않음 (JWT 방식은 Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입 요청은 인증 없이 허용 (권한 자체가 있으면 안됨 절때로)
                        .requestMatchers("/api/user/login").permitAll() // 사용자 로그인
                        .requestMatchers("/api/user/find_id").permitAll() // 아이디 찾기
                        .requestMatchers("/api/user/find_password").permitAll() // 비밀번호 찾기
                        .requestMatchers("/api/user/register").permitAll() // 사용자 회원가입
                        .requestMatchers("/api/admin/login").permitAll() // 관리자 로그인

                        .requestMatchers("/api/reserve/disabled-times", "/api/reserve/disabled-times/**").permitAll()// 예약확인용
                        
                        // User 권한
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/reserve/**").hasRole("USER")

                        // User, Admin, SuperAdmin 권한
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                        
                        // User, Admin, SuperAdmin 예약 권한
                        .requestMatchers("/api/reserve/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")

                        // Admin, SuperAdmin 권한
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")

                        // SuperAdmin 권한
                        .requestMatchers("/api/superadmin/**").hasRole("SUPERADMIN")
                        
                        // 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    

    // AuthenticationManager Bean 등록 / (CustomUserDetailsService 를 사용한 인증 처리를 위해 필요)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    

    //CORS설정 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // React dev 서버 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 필요 시 쿠키도 전송 가능

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}