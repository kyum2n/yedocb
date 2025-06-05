package com.example.yedocb.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : com.example.yedocb.jwt
 * fileName       : JwtAuthenticationFilter
 * author         : [ysg]
 * date           : [작성일자 : 2025-06-02]
 * description    : JWT 인증 필터 (JWT 검증 후 인증 정보 저장)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-02        [ysg]              최초 생성
 */

@Component // 스프링 빈으로 등록 (SecurityConfig 에서 필터 체인에 추가해서 사용)
@RequiredArgsConstructor // 생성자 주입 (jwtTokenProvider 주입받음)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    
    // 요청 들어올 시 실행되는 메서드 (1회성 필터임) / JWT 토큰 검증 후 인증 정보를 SecurityContextHolder에 저장함
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
    	
        String uri = request.getRequestURI();

        // 인증 없이 허용된 경로는 바로 다음 필터로 넘김
        if (uri.equals("/api/user/register") ||
            uri.equals("/api/user/login") ||
            uri.equals("/api/user/find_id") ||
            uri.equals("/api/user/find_password") ||
            uri.equals("/api/admin/login") ||
            uri.equals("/api/hello") ||
        	uri.equals("/api/user/refresh")){

            filterChain.doFilter(request, response);
            return;
        }
    	
    	// Authorization(인증) 헤더에서 JWT 토큰 추출함
        String token = resolveToken(request);

        // 토큰이 존재하고 유효한 경우에만 처리함
        if (token != null && jwtTokenProvider.validateToken(token)) {
        	
        	// 토큰에서 사용자 ID (subject)와 roles 추출함
            String userId = jwtTokenProvider.getUserId(token);
            List<String> roles = jwtTokenProvider.getRoles(token);

            // Roles 를 Spring Security 가 인식할 수 있는 권한 객체로 변환함
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            // Authentication(인증) 객체 생성 (주체: userId, 인증정보 없음, 권한 목록)
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);

            // SecurityContext 에 Authentication 객체 저장 후 컨트롤러에서 @AuthenticationPrincipal 사용 가능
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 계속 진행 (반드시 호출해야 체인 동작함)
        filterChain.doFilter(request, response);
    }

    // 요청 헤더에서 JWT 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // "Bearer " 로 시작하는 경우에만 유효한 토큰으로 처리
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        	// "Bearer " 이후 문자열 (실제 토큰 값) 반환
            return bearerToken.substring(7);
        }
        
        // 조건에 맞지 않으면 null 반환
        return null;
    }
}