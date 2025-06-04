package com.example.yedocb.admin.info;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.admin.entity.Admin;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	// 필드 및 생성자 주입
	 private final AdminService adminService;

	 public AdminController(@Qualifier("adminServiceImpl") AdminService adminService) {
		 this.adminService = adminService;
	 }
	
	// 관리자 등록
	@PostMapping
	public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
		adminService.registerStaff(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body("새 관리자 등록 완료!");
	}
	
	// 관리자 삭제
	@DeleteMapping("/{aId}")
	public ResponseEntity<String> deleteAdmin(@RequestParam("aId") String aid) {
	    adminService.deleteStaff(aid);
	    return ResponseEntity.ok("관리자 삭제 완료");
	}
	
	// 관리자 아이디 찾기
	@PostMapping("/find_id")
	public ResponseEntity<String> findAdminId(@RequestParam("aEmail") String aEmail) {
		String aId = adminService.findAdminId(aEmail);
		if(aId == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(aId);
	}
	
	// 관리자 비밀번호 찾기
	@PostMapping("/find_password")
	public ResponseEntity<String> findAdminPassword(@RequestParam("aId") String aId, @RequestParam("aEmail") String aEmail){
		String aPwd = adminService.findAdminPassword(aId, aEmail);
		if(aPwd == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 관리자 정보를 찾을 수 없습니다.");
		}
		return ResponseEntity.ok("관리자 정보가 이메일로 발송되었습니다.");
	}

}
