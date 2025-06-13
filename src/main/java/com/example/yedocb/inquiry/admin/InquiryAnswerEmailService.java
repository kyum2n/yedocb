package com.example.yedocb.inquiry.admin;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

/**
 * packageName    : com.exmple.yedocb.inquiry.admin
 * fileName       : InquiryAnswerEmailService.java
 * author         : lkm
 * date           : 250613
 * description    : 이메일 서비스(1:1 문의)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Service
public class InquiryAnswerEmailService {
	// 필드 및 생성자 주입
	private JavaMailSender mailSender;
		
	public InquiryAnswerEmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
		
	// 1:1 문의 답변 이메일로 발송
	public void sendInquiryAnswer(String uEmail, String uName, String qAnswer) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setTo(uEmail);
			helper.setSubject("[연세 BT]1:1 문의에 대한 답변입니다.");
			
			String mailContent = """
					<div style='font-family:Arial, sans-serif; font-size: 14px;'>
						<p>안녕하세요 %s님!</p>
						<p>문의 주신 내용에 대한 답변입니다.</p>
						<p>%s</p>
						<p>감사합니다.</p>
						<p>연세 BT 드림</p>
					</div>
					""".formatted(uName, qAnswer);
			
			helper.setText(mailContent, true);
			mailSender.send(message);
					
		}catch(Exception e) {
			System.err.println("메일 전송 오류: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("메일 전송 실패: " + e.getMessage(), e);
		}
	}
}
