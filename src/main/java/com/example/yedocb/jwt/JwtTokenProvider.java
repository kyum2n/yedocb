package com.example.yedocb.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey; // 다른것들은 자카르타 사용(최신버전)을 쓰는데 이건 그대로 써도 됨
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * packageName    : com.example.yedocb.jwt
 * fileName       : JwtTokenProvider
 * author         : [ysg]
 * date           : [작성일자 : 2025-06-02]
 * description    : JWT 토큰 생성 및 검증 기능 제공
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-02        [ysg]              최초 생성
 */

@Component // 스프링 빈으로 등록 (다른 클래스에서 주입받아 사용 가능)
public class JwtTokenProvider {

    private SecretKey secretKey; // JWT 서명에 사용할 비밀 키 (HMAC-SHA256 알고리즘에 사용)

    // application.properties 에서 주입 -> jwt.secret 속성
    @Value("${jwt.secret}")
    private String secret;
    
    // application.properties 에서 주입 -> jwt.expiration 속성
    @Value("${jwt.expiration}")
    private long tokenValidTime;

    // 빈 초기화 시점에 호출됨 -> SecretKey 를 초기화
    @PostConstruct
    protected void init() {
    	// properties 에 Base64 인코딩된 시크릿 키가 들어가기 때문에 decode 해서 SecretKey 생성
        secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    // JWT 토큰 생성
    public String createToken(String userId, List<String> roles) {
    	
    	// Claims = JWT 에 들어가는 payload 데이터
        Claims claims = Jwts.claims().setSubject(userId); // "sub" : userId
        claims.put("roles", roles); // Role 정보 넣기 [권한 목록]

        Date now = new Date(); // 현재 시간
        Date validity = new Date(now.getTime() + tokenValidTime); // 만료 시간 계산

        // JWT 토큰 생성
        return Jwts.builder()
                .setClaims(claims) // payload 데이터 설정
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 토큰 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 (비밀키 + 알고리즘으로 처리)
                .compact(); // 최종적으로 문자열 형태의 JWT 토큰 생성
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 토큰 검증을 위한 secretKey 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody()
                .getSubject(); // "sub" 값 반환
    }

    // 토큰에서 Role 정보 추출
    public List<String> getRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // "roles" 라는 키로 저장된 List<String> 반환
        return (List<String>) claims.get("roles");
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
        	// 토큰을 파싱해서 정상적으로 파싱되면 유효
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true; // 유효한 토큰
        } catch (Exception e) {
        	// 파싱 중 예외 발생하면 잘못된 토큰
            return false;
        }
    }
}