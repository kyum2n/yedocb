package com.example.yedocb.user.login;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.yedocb.jwt.JwtTokenProvider;
import com.example.yedocb.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserLoginController {
	
    private final UserLoginService userLoginService;
    private final JwtTokenProvider jwtTokenProvider; // JwtTokenProvider 주입

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

            // JWT 토큰 반환
            // 로그인 성공 시 프론트엔드에 전달할 응답 데이터 구성
            Map<String, String> responseBody = Map.of(
            	    "token", token, // JWT 토큰 값
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
}