package com.example.yedocb.admin.info;

import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * packageName    : com.exmple.yedocb.admin.info
 * fileName       : AdminEmailService
 * author         : lkm
 * date           : 250528
 * description    : 서비스 (관리자 비밀번호 조회 시 비밀번호 발송)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Service
public class AdminEmailService {
	// 필드 및 생성자 주입
	private JavaMailSender mailSender;
	
	public AdminEmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	// 관리자 이메일로 비밀번호 발송
	public void sendPasswordEmail(String aEmail, String aPwd) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			helper.setTo(aEmail);
			helper.setSubject("[연세 BT]관리자 비밀번호 안내");
			
			String mailContent = "<div style='font-family:Arial, sans-serif; font-size: 14px;'>"
					+ "<p>안녕하세요 관리자님!</p>"
					+ "<p>요청하신 비밀번호는 다음과 같습니다.</p>"
					+ "<p><strong style='color:blue;'>" + aPwd + "</strong></p>"
					+ "</div>";
			
			helper.setText(mailContent, true);
			mailSender.send(message);
					
		}catch(Exception e) {
			System.out.println("메일 전송 오류");
			e.printStackTrace();
			throw new RuntimeException("메일 전송 실패: " + e.getMessage());
		}
	}
}
