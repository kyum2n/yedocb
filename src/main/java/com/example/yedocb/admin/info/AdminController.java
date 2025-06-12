package com.example.yedocb.admin.info;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.admin.entity.Admin;
import com.example.yedocb.jwt.JwtTokenProvider;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	// 필드 및 생성자 주입 
	 private final AdminService adminService;
	 private final JwtTokenProvider jwtTokenProvider;
	 private final AdminEmailService adminEmailService;

	 public AdminController(@Qualifier("adminServiceImpl") AdminService adminService, 
			 				JwtTokenProvider jwtTokenProvider,
			 				AdminEmailService adminEmailService) {
		 this.adminService = adminService;
		 this.jwtTokenProvider = jwtTokenProvider;
		 this.adminEmailService = adminEmailService;
	 }
	 
	// 직원 목록 조회
	@GetMapping("/staff")
	public ResponseEntity<List<Admin>> getAllAdmins(@RequestHeader("Authorization") String authHeader){
		
		// 요청 헤더에서 JWT 추출
		String token = authHeader.replace("Bearer", "").trim();

		// JWT에서 역할 추출
		List<String> roles = jwtTokenProvider.getRoles(token);
		
		// 최고관리자만 허용
		if(roles == null || !roles.contains("SUPERADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Admin> adminList = adminService.getAllAdmins();
		return ResponseEntity.ok(adminList);
	}
	
	// 관리자 등록
	@PostMapping("/staff")
	public ResponseEntity<String> createAdmin(@RequestBody Admin admin, @RequestHeader("Authorization") String authHeader) {
		
		String token = authHeader.replace("Bearer", "").trim(); 
		
		List<String> roles = jwtTokenProvider.getRoles(token);
		
		if(roles == null || !roles.contains("SUPERADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("최고 관리자만 등록 가능");
		}
		
		System.out.println("등록 대상 관리자: " + admin);
		adminService.registerStaff(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body("새 관리자 등록 완료!");
	}
	
	// 관리자 삭제
	@DeleteMapping("/staff/{aId}")
	public ResponseEntity<String> deleteAdmin(@PathVariable("aId") String aId, @RequestHeader("Authorization") String authHeader) {
		
		String token = authHeader.replace("Bearer", "").trim(); 
		
		List<String> roles = jwtTokenProvider.getRoles(token);
		
		if(roles == null || !roles.contains("SUPERADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("최고 관리자만 삭제 가능");
		}
		
		System.out.println("삭제 대상 관리자: " + aId);
	    adminService.deleteStaff(aId);
	    return ResponseEntity.ok("관리자 삭제 완료");
	}
	
	// 관리자 아이디 찾기
	@PostMapping("/find_id")
	public ResponseEntity<?> findAdminId(@RequestParam("aEmail") String aEmail) {
		Admin admin = adminService.findAdminId(aEmail);
		if(admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일로 등록된 관리자가 없습니다.");
		}
		return ResponseEntity.ok(Map.of("aId", admin.getAId()));
	}
	
	// 관리자 비밀번호 찾기
	@PostMapping("/find_password")
	public ResponseEntity<?> findAdminPassword(@RequestParam("aId") String aId, @RequestParam("aEmail") String aEmail){
		String aPwd = adminService.findAdminPassword(aId, aEmail);
		if(aPwd == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 관리자 비밀번호를 찾을 수 없습니다.");
		}

		return ResponseEntity.ok("비밀번호가 이메일로 전송되었습니다.");
	}

}
