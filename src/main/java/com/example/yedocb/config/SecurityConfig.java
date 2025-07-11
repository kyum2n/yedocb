/*
 * package com.example.yedocb.config;
 * 
 * import com.example.yedocb.jwt.JwtAuthenticationFilter; import
 * lombok.RequiredArgsConstructor;
 * 
 * import java.util.List;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.authentication.AuthenticationManager; import
 * org.springframework.security.config.annotation.authentication.configuration.
 * AuthenticationConfiguration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.http.SessionCreationPolicy; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.authentication.
 * UsernamePasswordAuthenticationFilter;
 * 
 * import org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.CorsConfigurationSource; import
 * org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 * 
 *//**
	 * packageName : com.example.yedocb.config fileName : SecurityConfig author :
	 * [ysg] date : [작성일자 : 2025-06-02] description : Spring Security 설정 (JWT + 권한별
	 * 경로 설정) =========================================================== DATE
	 * AUTHOR NOTE -----------------------------------------------------------
	 * 2025-06-02 [ysg] 최초 생성
	 *//*
		 * 
		 * @Configuration
		 * 
		 * @EnableWebSecurity
		 * 
		 * @RequiredArgsConstructor public class SecurityConfig {
		 * 
		 * private final JwtAuthenticationFilter jwtAuthenticationFilter;
		 * 
		 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
		 * throws Exception { return http .cors(cors ->
		 * cors.configurationSource(corsConfigurationSource())) .csrf(csrf ->
		 * csrf.disable()) .sessionManagement(session ->
		 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 * .authorizeHttpRequests(auth -> auth
		 * 
		 * // 사용자 인증 없이 접근 가능한 요청 .requestMatchers("/api/user/login").permitAll()
		 * .requestMatchers("/api/user/find_id").permitAll()
		 * .requestMatchers("/api/user/find_password").permitAll()
		 * .requestMatchers("/api/user/register").permitAll()
		 * .requestMatchers("/api/user/refresh").permitAll()
		 * .requestMatchers("/api/noticeEvent/**").permitAll()
		 * 
		 * 
		 * .requestMatchers("/api/admin/login").permitAll()
		 * .requestMatchers("/api/admin/find_id").permitAll()
		 * .requestMatchers("/api/admin/find_password").permitAll()
		 * 
		 * .requestMatchers("/api/reserve/disabled-times",
		 * "/api/reserve/disabled-times/**").permitAll()
		 * 
		 * // 관리자 예약/회원 기능용 API 허용 (프론트 403 대응)
		 * .requestMatchers("/api/admin/reserve").permitAll()
		 * .requestMatchers("/api/admin/reserve/**").permitAll()
		 * .requestMatchers("/api/admin/users").permitAll()
		 * .requestMatchers("/api/admin/users/**").permitAll()
		 * .requestMatchers("/api/admin/user").permitAll()
		 * .requestMatchers("/api/admin/user/**").permitAll()
		 * 
		 * // 권한 기반 접근 제한 .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN",
		 * "SUPERADMIN") .requestMatchers("/api/reserve/**").hasAnyRole("USER", "ADMIN",
		 * "SUPERADMIN") .requestMatchers("/api/admin/staff",
		 * "/api/admin/staff/**").hasRole("SUPERADMIN")
		 * .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
		 * 
		 * // 그 외 모든 요청은 인증 필요 .anyRequest().authenticated() )
		 * .addFilterBefore(jwtAuthenticationFilter,
		 * UsernamePasswordAuthenticationFilter.class) .build(); }
		 * 
		 * @Bean public AuthenticationManager
		 * authenticationManager(AuthenticationConfiguration
		 * authenticationConfiguration) throws Exception { return
		 * authenticationConfiguration.getAuthenticationManager(); }
		 * 
		 * @Bean public CorsConfigurationSource corsConfigurationSource() {
		 * CorsConfiguration config = new CorsConfiguration();
		 * config.setAllowedOrigins(List.of("http://localhost:5173")); // React 개발 서버
		 * config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		 * config.setAllowedHeaders(List.of("*")); config.setAllowCredentials(true);
		 * 
		 * UrlBasedCorsConfigurationSource source = new
		 * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
		 * config); return source; } }
		 */


package com.example.yedocb.config;

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


/**
 * packageName : com.example.yedocb.config
 * fileName : SecurityConfig
 * author : [ysg]
 * date : [작성일자 : 2025-06-02]
 * description : Spring Security 설정 (JWT + 권한별 경로 설정)
 * ===========================================================
 * DATE AUTHOR NOTE
 * -----------------------------------------------------------
 * 2025-06-02 [ysg] 최초 생성
 */

import com.example.yedocb.jwt.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/login").permitAll()
                .requestMatchers("/api/user/register").permitAll()
                .requestMatchers("/api/user/find_id").permitAll()
                .requestMatchers("/api/user/find_password").permitAll()
                .requestMatchers("/api/user/refresh").permitAll()

                .requestMatchers("/api/oauth2/**").permitAll() // 소셜 로그인
                .requestMatchers("/api/user/send-code").permitAll() // 회원가입 중 이메일
                .requestMatchers("/api/user/verify-code").permitAll() // 회원가입 중 이메일 / 중복방지

                .requestMatchers("/api/admin/login").permitAll()
                .requestMatchers("/api/admin/find_id").permitAll()
                .requestMatchers("/api/admin/find_password").permitAll()
                .requestMatchers("/api/hello").permitAll()
                .requestMatchers("/api/noticeEvent/**").permitAll()  // 공지사항 permitAll
                .requestMatchers("/api/admin/noticeEvent/**").hasAnyRole("ADMIN", "SUPERADMIN")  // 공지사항 관리자 페이지
                .requestMatchers("/api/reserve/disabled-times", "/api/reserve/disabled-times/**").permitAll()
                .requestMatchers("/api/admin/reserve").hasAnyRole("ADMIN", "SUPERADMIN")
                .requestMatchers("/api/admin/reserve/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .requestMatchers("/api/admin/users").hasAnyRole("ADMIN", "SUPERADMIN")
                .requestMatchers("/api/admin/users/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .requestMatchers("/api/admin/user").hasAnyRole("ADMIN", "SUPERADMIN")
                .requestMatchers("/api/admin/user/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                .requestMatchers("/api/reserve/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                .requestMatchers("/api/inquiry/**").hasAnyRole("USER", "ADMIN", "SUPERADMIN")
                .requestMatchers("/api/admin/inquiry/**").hasAnyRole("ADMIN", "SUPERADMIN")
//                .requestMatchers("/api/admin/inquiry/**").permitAll()
                .requestMatchers("/api/admin/staff", "/api/admin/staff/**").hasRole("SUPERADMIN")
                .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }))
            // UsernamePasswordAuthenticationFilter 이전에 JWT 인증 필터를 등록
            // => 요청 도착 시 먼저 JWT 유효성 검사 → 인증 정보 저장 
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://3.37.209.69"));  // 프론트 포트 확인
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

