package com.example.yedocb.user.oauth;

import com.example.yedocb.jwt.JwtTokenProvider;
import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.yedocb.user.oauth
 * fileName       : OAuthController
 * author         : [ysg]
 * date           : [작성일자 : 2025-06-11]
 * description    : Google OAuth2 로그인 처리를 위한 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-11        [ysg]              최초 생성 (Google OAuth2 로그인)
 */

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${google.redirect-uri}")
    private String REDIRECT_URI;

    @PostMapping("/google")
    public ResponseEntity<?> handleGoogleLogin(@RequestParam("code") String code) {
        try {
            // 1. 액세스 토큰 요청
            RestTemplate restTemplate = new RestTemplate();
            String tokenUri = "https://oauth2.googleapis.com/token";

            MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
            tokenParams.add("code", code);
            tokenParams.add("client_id", GOOGLE_CLIENT_ID);
            tokenParams.add("client_secret", GOOGLE_CLIENT_SECRET);
            tokenParams.add("redirect_uri", REDIRECT_URI);
            tokenParams.add("grant_type", "authorization_code");

            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenParams, tokenHeaders);
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, tokenRequest, Map.class);

            String accessToken = (String) tokenResponse.getBody().get("access_token");

            // 2. 사용자 정보 요청
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setBearerAuth(accessToken);
            HttpEntity<?> userRequest = new HttpEntity<>(userHeaders);

            ResponseEntity<Map> userResponse = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, userRequest, Map.class
            );

            Map userInfo = userResponse.getBody();
            String googleId = (String) userInfo.get("sub");
            String email = ((String) userInfo.get("email")).trim();

            // 3. DB 사용자 확인 및 생성
            User user = userMapper.selectByUEmail(email);
            if (user == null) {
                // 자동 가입 없이 프론트로 회원가입 유도시킴
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("해당 이메일로 가입된 사용자가 없습니다. 회원가입이 필요합니다.");
            }
            
            // 4. JWT 발급 (uId 기준)
            String jwtToken = jwtTokenProvider.createToken(user.getUId(), Collections.singletonList("USER"));

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", jwtToken);
            response.put("uId", user.getUId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}