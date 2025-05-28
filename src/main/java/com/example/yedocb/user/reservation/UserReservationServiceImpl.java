package com.example.yedocb.user.reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.yedocb.reservation.entity.Reservation;
import com.example.yedocb.reservation.repository.ReservationMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.example.yedocb.user.reservation
 * fileName       : UserReservationServiceImpl
 * author         : [ysg]
 * date           : [작성일자 : 2025-05-28]
 * description    : 사용자 예약 등록/조회/수정/삭제 기능 구현 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-28        [ysg]              최초 생성
 */

@Service
@RequiredArgsConstructor
public class UserReservationServiceImpl implements UserReservationService {

    private final ReservationMapper reservationMapper;
    private final EmailService emailService;

    @Override
    public void registerReservation(Reservation reservation) {
        reservation.setStatus("대기");
        reservationMapper.insertReservation(reservation);
    }

    @Override
    public boolean checkAvailableTime(String date, String time) {
        int count = reservationMapper.countByDateTime(date, time);
        return count == 0;
    }

    @Override
    public List<Reservation> getReservationsByUser(String uId) {
        return reservationMapper.selectByUser(uId);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        reservationMapper.updateReservation(reservation);

        // 예약이 '확정'으로 바뀌면 이메일 알림
        if ("확정".equals(reservation.getStatus())) {
            emailService.sendConfirmEmail(reservation.getUId(), reservation);
        }
    }

    @Override
    public void deleteReservation(int rId) {
        reservationMapper.deleteReservation(rId);
    }
}