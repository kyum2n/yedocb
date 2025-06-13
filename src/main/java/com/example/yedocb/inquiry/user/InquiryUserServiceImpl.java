package com.example.yedocb.inquiry.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.yedocb.inquiry.entity.Inquiry;
import com.example.yedocb.inquiry.repository.InquiryMapper;

/**
 * packageName    : com.exmple.yedocb.inauiry.user
 * fileName       : InquiryUserServiceImpl.java
 * author         : lkm
 * date           : 250613
 * description    : 서비스 구현체(1:1 문의-사용자)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Service
public class InquiryUserServiceImpl implements InquiryUserService{
	// 필드 및 생성자 주입
	private InquiryMapper inquiryMapper;
	
	public InquiryUserServiceImpl(InquiryMapper inquiryMapper) {
		this.inquiryMapper = inquiryMapper;
	}
	
	
	// 사용자별 문의 내역 조회(사용자-마이페이지)
	@Override
	public List<Inquiry> getInquiriesByUId(String uId){
		return inquiryMapper.getInquiriesByUId(uId);
	}
		
	// 문의 작성 및 등록(사용자-1:1 문의 페이지)
	@Override
	public int createInquiry(Inquiry inquiry) {
		inquiry.setCreatedAt(LocalDateTime.now());
		inquiry.setQStatus("답변대기");
		
		return inquiryMapper.insertInquiry(inquiry);
	}
	
}
