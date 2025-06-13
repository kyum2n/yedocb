package com.example.yedocb.inquiry.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.inquiry.entity.Inquiry;
import com.example.yedocb.jwt.JwtTokenProvider;

/**
 * packageName    : com.exmple.yedocb.inauiry.user
 * fileName       : InquiryUserController.java
 * author         : lkm
 * date           : 250613
 * description    : 컨트롤러(1:1 문의-사용자)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@RestController
@RequestMapping("/api/inquiry")
public class InquiryUserController {

	// 필드 및 생성자 주입
	private InquiryUserService inquiryUserService;
	private JwtTokenProvider jwtTokenProvider;
	
	public InquiryUserController(InquiryUserService inquiryUserService, JwtTokenProvider jwtTokenProvider) {
		this.inquiryUserService = inquiryUserService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	// 사용자별 문의 내역 조회(마이페이지)
	@GetMapping
	public ResponseEntity<List<Inquiry>> getInquiriesByUId(@RequestHeader("Authorization") String authHeader){
		
		// 토큰&권한 추출
		String token = authHeader.replace("Bearer", "").trim();
		String uId = jwtTokenProvider.getUserId(token);
		
		// 문의 내역 불러오기
		List<Inquiry> inquiries = inquiryUserService.getInquiriesByUId(uId);
				
		return ResponseEntity.ok(inquiries);
	}
	
	// 문의 작성(1:1 문의 페이지)
	@PostMapping
	public ResponseEntity<String> createInquiry(@RequestBody Inquiry inquiry, @RequestHeader("Authorization") String authHeader){
		
		// 토큰&권한 추출
		String token = authHeader.replace("Bearer", "").trim();
		String uId = jwtTokenProvider.getUserId(token);
		
		inquiry.setUId(uId);
		
		int result = inquiryUserService.createInquiry(inquiry);
		return ResponseEntity.ok("작성하신 문의가 등록되었습니다.");
	}
}
