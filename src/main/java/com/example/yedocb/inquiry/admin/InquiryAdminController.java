package com.example.yedocb.inquiry.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

import com.example.yedocb.admin.info.AdminEmailService;
import com.example.yedocb.inquiry.entity.Inquiry;
import com.example.yedocb.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.exmple.yedocb.inquiry.admin
 * fileName       : InquiryAdminController.java
 * author         : lkm
 * date           : 250613
 * description    : 컨트롤러(1:1 문의 - 관리자)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@RestController
@RequestMapping("/api/admin/inquiry")
public class InquiryAdminController {
	
	// 필드 및 생성자 주입
	private final InquiryAdminService inquiryAdminService;
	private final JwtTokenProvider jwtTokenProvider;
	
	public InquiryAdminController(InquiryAdminService inquiryAdminService,
								JwtTokenProvider jwtTokenProvider) {
		this.inquiryAdminService = inquiryAdminService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	// 전체 문의 목록 조회 (관리자)
	@GetMapping
	public ResponseEntity<?> getAllInquiries(@RequestHeader("Authorization") String authHeader){
		// 토큰&권한 추출
		String token = authHeader.replace("Bearer", "").trim();
		List<String> roles = jwtTokenProvider.getRoles(token);
		
		if(roles == null || !roles.contains("ADMIN") && !roles.contains("SUPERADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
		}
		
		// 문의 목록 불러오기
		List<Inquiry> inquiries = inquiryAdminService.getAllInquiries();
		return ResponseEntity.ok(inquiries);
	}
	
	// 문의 답변 작성 및 이메일 전송(관리자-문의 관리 페이지)
	@PostMapping("/{qId}/answer")
	public ResponseEntity<String> answerInquiry(@PathVariable int qId,
												@RequestBody Map<String, String> request,
												@RequestHeader("Authorization") String authHeader){
		// 토큰&권한 추출
		String token = authHeader.replace("Bearer", "").trim();
		List<String> roles = jwtTokenProvider.getRoles(token);
				
		if(roles == null || !roles.contains("ADMIN") && !roles.contains("SUPERADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
		}
		
		// 문의 답변하기
		String qAnswer = request.get("qAnswer");
		if(qAnswer == null || qAnswer.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("믄의 답변이 누락되었습니다.");			
		}
		
		LocalDateTime answeredAt = LocalDateTime.now();
		String qStatus = "답변 완료";
		
		inquiryAdminService.answerInquiry(qId, qAnswer, answeredAt, qStatus);
		return ResponseEntity.ok("문의에 대한 답변이 전송되었습니다.");
		
	}
	
}
