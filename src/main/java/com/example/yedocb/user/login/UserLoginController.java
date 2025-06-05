package com.example.yedocb.user.login;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.yedocb.jwt.JwtTokenProvider;
import com.example.yedocb.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserLoginController {

    private final UserLoginService userLoginService;
    private final JwtTokenProvider jwtTokenProvider; // JwtTokenProvider 주입

    // RefreshToken 저장소 (현재 In-Memory Map 사용 중 → 운영 시 반드시 DB 또는 Redis 사용해야 함)
    private final Map<String, String> refreshTokenStore = new ConcurrentHashMap<>();

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> loginData) {
        String uId = loginData.get("uId");
        String uPwd = loginData.get("uPwd");

        User user = userLoginService.login(uId, uPwd);

        if (user != null) {

            // 여기서 Role 은 임시로 "USER" (나중에 DB 에서 가져와도 됨)
            List<String> roles = List.of("USER");

            // JWT 발급
            String token = jwtTokenProvider.createToken(uId, roles);

            // Refresh Token 발급
            String refreshToken = jwtTokenProvider.createRefreshToken(uId);

            // Refresh Token 저장 (현재 Map 사용, 추후 DB/Redis 교체 필요)
            refreshTokenStore.put(uId, refreshToken);

            // JWT 토큰 반환
            // 로그인 성공 시 프론트엔드에 전달할 응답 데이터 구성
            Map<String, String> responseBody = Map.of(
                    "token", token, // JWT 토큰 값
                    "refreshToken", refreshToken, // 추가: Refresh Token
                    "uId", uId, // 로그인한 사용자 ID (프론트에서 사용자 표시나 관리용으로 사용함)
                    "uName", user.getUName() // 사용자 이름 추가
            );

            return ResponseEntity.ok(responseBody);
        } else {
            Map<String, String> errorResponse = Map.of("error", "Login failed");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String token) {

        // 프론트에서 JWT 삭제하도록 유도 (설계서: 클라이언트에 저장된 JWT 삭제 후 인증 종료 처리)
        return ResponseEntity.ok("로그아웃 완료 (프론트에서 토큰 삭제 필요)");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") String refreshToken) {

        // Refresh Token 유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken)) {
        	throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token 유효하지 않음");
        }

        // 사용자 ID 추출
        String userId = jwtTokenProvider.getUserId(refreshToken);

        // 저장된 Refresh Token 과 비교
        String savedRefreshToken = refreshTokenStore.get(userId);

        if (savedRefreshToken == null || !refreshToken.equals(savedRefreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token 불일치");
        }

        // 새 Access Token 발급
        List<String> roles = List.of("USER"); // 현재 고정값 → 필요 시 DB 에서 role 조회 가능
        String newAccessToken = jwtTokenProvider.createToken(userId, roles);

        // 새 Access Token 반환 (Refresh Token은 그대로 유지)
        return ResponseEntity.ok(newAccessToken);
    }
}