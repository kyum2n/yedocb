package com.example.yedocb.inquiry.user;

import java.util.List;

import com.example.yedocb.inquiry.entity.Inquiry;
import com.example.yedocb.inquiry.repository.InquiryMapper;

/**
 * packageName    : com.exmple.yedocb.inauiry.user
 * fileName       : InquiryUserService.java
 * author         : lkm
 * date           : 250613
 * description    : 서비스(1:1 문의-사용자)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

public interface InquiryUserService {
	
	// 사용자별 문의 내역 조회(사용자-마이페이지)
	public List<Inquiry> getInquiriesByUId(String uId);
	
	// 문의 작성 및 등록(사용자-1:1 문의 페이지)
	public int createInquiry(Inquiry inquiry);
}
