package com.example.yedocb.inquiry.admin;

import java.time.LocalDateTime;
import java.util.List;

import com.example.yedocb.inquiry.entity.Inquiry;

/**
 * packageName    : com.exmple.yedocb.inquiry.admin
 * fileName       : InquiryAdminService.java
 * author         : lkm
 * date           : 250613
 * description    : 서비스(1:1 문의 - 관리자)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

public interface InquiryAdminService {
	// 전체 문의 목록 조회(관리자)
	public List<Inquiry> getAllInquiries();
		
	// 문의 답변 작성 및 이메일 전송(관리자-문의 관리 페이지)
	public int answerInquiry(int qId, String qAnswer, LocalDateTime answeredAt, String qStatus);

}
