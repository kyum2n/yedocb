package com.example.yedocb.admin.info;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.admin.entity.Admin;
import com.example.yedocb.admin.info.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	// 필드 및 생성자 주입
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	// 관리자 등록
	@PostMapping
	public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
		adminService.registerStaff(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body("새 관리자 등록 완료!");
	}
	
	// 관리자 삭제
	@PostMapping("/{aId}")
	public ResponseEntity<String> deleteAdmin(@PathVariable String aId) {
		adminService.deleteStaff(aId);
		return ResponseEntity.ok("관리자 삭제 완료!");
	}
	
	// 관리자 아이디 찾기
	@PostMapping("/find_id")
	public ResponseEntity<Admin> findAdminId(@RequestParam("aEmail") String aEmail) {
		Admin admin = adminService.findAdminId(aEmail);
		if(admin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(admin);
	}
	
	// 관리자 비밀번호 찾기
	@PostMapping("/find_password")
	public ResponseEntity<String> findAdminPassword(@RequestParam("aId") String aId, @RequestParam("aEmail") String aEmail){
		String aPwd = adminService.findAdminPassword(aId, aEmail);
		if(aPwd == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(aPwd);
	}
	
	
	
}
