package com.example.yedocb.inquiry.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.yedocb.inquiry.entity.Inquiry;


/**
 * packageName    : com.exmple.yedocb.inquiry.repository
 * fileName       : InquiryMapper.java
 * author         : lkm
 * date           : 250613
 * description    : 리포지토리(1:1 문의)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Mapper
public interface InquiryMapper {
	// 전체 문의 목록 조회(관리자)
	List<Inquiry> getAllInquiries();
	
	// 사용자별 문의 내역 조회(사용자-마이페이지)
	List<Inquiry> getInquiriesByUId(@Param("uId") String uId);
	
	// 특정 문의 정보 조회
	Inquiry getInquiriesByQId(@Param("qId") int qId);
	
	// 문의 작성 및 등록(사용자-1:1 문의 페이지)
	int insertInquiry(Inquiry inquiry);
	
	// 문의 답변 작성(관리자-문의 관리 페이지)
	int updateInquiryAnswer(@Param("qId") int qId,
							@Param("qAnswer") String qAnswer,
							@Param("answeredAt") LocalDateTime answeredAt,
							@Param("qStatus") String qStatus);
	
}
