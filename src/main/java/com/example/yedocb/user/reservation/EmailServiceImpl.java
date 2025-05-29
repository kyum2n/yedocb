package com.example.yedocb.user.reservation;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.yedocb.reservation.entity.Reservation;
import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.example.yedocb.user.reservation
 * fileName       : EmailServiceImpl
 * author         : [ysg]
 * date           : [작성일자 : 2025-05-28]
 * description    : 이메일 발송 기능 구현 클래스 (예약 확정 시 알림 발송)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-28        [ysg]              최초 생성
 */

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final UserMapper userMapper;

    @Override
    public void sendConfirmEmail(String uId, Reservation reservation) {
    	User user = userMapper.selectByUId(uId);
    	String email = user.getUEmail(); 
    	
        String subject = "[YeDoc] 예약 확정 알림";
        String text = String.format(
            "안녕하세요 %s님,\n\n예약이 확정되었습니다.\n\n시술 항목: %s\n일시: %s %s\n\n감사합니다.",
            uId,
            reservation.getTName(),
            reservation.getConsultDate(),
            reservation.getConsultTime()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}