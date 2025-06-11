package com.example.yedocb.admin.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
	
//    // 생성자 주입 (지금은 필요없음)
//	public AdminLoginController(AdminLoginService adminLoginService) {
//		this.adminLoginService = adminLoginService;
//		this.jwtTokenProvider = jwtTokenProvider;
//	}
	
	// 관리자 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginAdmin(@RequestBody Admin loginRequest) {

		Admin admin = adminLoginService.loginAdmin(loginRequest.getAId(), loginRequest.getAPwd());
		System.out.println(passwordEncoder.encode("super1234"));
		
        if (admin != null) {
            // DB에서 가져온 관리자 role 확인 후 JWT 발급
            String token = jwtTokenProvider.createToken(admin.getAId(), List.of(admin.getRole()));
            
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("role", admin.getRole());
            result.put("aId", admin.getAId());

            // JWT 토큰 반환
            return ResponseEntity.ok(result);
            
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
	}

}
