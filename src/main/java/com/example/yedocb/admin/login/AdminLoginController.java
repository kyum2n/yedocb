package com.example.yedocb.admin.login;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.admin.entity.Admin;
import com.example.yedocb.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminLoginController {
	
    // 필드 주입
    private final AdminLoginService adminLoginService;
    private final JwtTokenProvider jwtTokenProvider;
	
//    // 생성자 주입 (지금은 필요없음)
//	public AdminLoginController(AdminLoginService adminLoginService) {
//		this.adminLoginService = adminLoginService;
//		this.jwtTokenProvider = jwtTokenProvider;
//	}
	
	// 관리자 로그인
	@PostMapping("/login")
	public ResponseEntity<String> loginAdmin(@RequestBody Admin loginRequest) {
		boolean success = adminLoginService.loginAdmin(loginRequest.getAId(), loginRequest.getAPwd());
		
        if (success) {
            // 여기서 Role 은 임시로 "ADMIN"
            List<String> roles = List.of("ADMIN");

            // JWT 발급
            String token = jwtTokenProvider.createToken(loginRequest.getAId(), roles);

            // JWT 토큰 반환
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
	}

}
