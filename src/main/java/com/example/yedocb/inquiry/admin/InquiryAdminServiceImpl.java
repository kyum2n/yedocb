package com.example.yedocb.inquiry.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.yedocb.admin.info.AdminEmailService;
import com.example.yedocb.inquiry.entity.Inquiry;
import com.example.yedocb.inquiry.repository.InquiryMapper;

/**
 * packageName    : com.exmple.yedocb.inquiry.admin
 * fileName       : InquiryAdminServiceImpl.java
 * author         : lkm
 * date           : 250613
 * description    : 서비스 구현체(1:1 문의 - 관리자)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Service
public class InquiryAdminServiceImpl implements InquiryAdminService{

	// 필드 및 생성자 주입
    private final InquiryAnswerEmailService inquiryAnswerEmailService;
	private InquiryMapper inquiryMapper;
	
	public InquiryAdminServiceImpl(InquiryMapper inquiryMapper, InquiryAnswerEmailService inquiryAnswerEmailService) {
		this.inquiryMapper = inquiryMapper;
		this.inquiryAnswerEmailService = inquiryAnswerEmailService;
	}
	
	// 전체 문의 목록 조회(관리자)
	@Override
	public List<Inquiry> getAllInquiries(){
		return inquiryMapper.getAllInquiries();
	};
			
	// 문의 답변 작성 및 이메일 전송(관리자-문의 관리 페이지)
	@Override
	public int answerInquiry(int qId, String qAnswer, LocalDateTime answeredAt, String qStatus) {
		// 문의 정보 조회
		Inquiry inquiry = inquiryMapper.getInquiriesByQId(qId);
		if(inquiry == null) {
			throw new IllegalArgumentException("해당 문의가 존재하지 않습니다. qId : " + qId);
		}
		
		// 답변 이메일 전송
		inquiryAnswerEmailService.sendInquiryAnswer(inquiry.getUEmail(), inquiry.getUName(), qAnswer);
		
		return inquiryMapper.updateInquiryAnswer(qId, qAnswer, answeredAt, qStatus);
	};
			
}
