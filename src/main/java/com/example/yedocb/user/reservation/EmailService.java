package com.example.yedocb.user.reservation;

import com.example.yedocb.reservation.entity.Reservation;

/**
 * packageName    : com.example.yedocb.user.reservation
 * fileName       : EmailService
 * author         : [ysg]
 * date           : [작성일자 : 2025-05-28]
 * description    : 이메일 발송 서비스 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-28        [ysg]              최초 생성
 */

public interface EmailService {
    void sendConfirmEmail(String uId, Reservation reservation);
} 