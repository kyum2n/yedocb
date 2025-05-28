package com.example.yedocb.admin.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.admin.entity.Admin;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {
	// 필드 및 생성자 주입
	private final AdminLoginService adminLoginService;
	
	public AdminLoginController(AdminLoginService adminLoginService) {
		this.adminLoginService = adminLoginService;
	}
	
	// 관리자 로그인
	@PostMapping("/login")
	public ResponseEntity<String> loginAdmin(@RequestBody Admin loginRequest) {
		boolean success = adminLoginService.loginAdmin(loginRequest.getAId(), loginRequest.getAPwd());
		
		if(success) {
			return ResponseEntity.ok("로그인 성공!");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
	}
}
