package com.example.yedocb.user.reservation;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // 예약 등록 (상태 : 대기)
    @Override
    public void makeReservation(Reservation reservation) {
        // 이미 같은 날짜+시간에 예약이 있는지 검사
        boolean available = isAvailableTime(reservation.getConsultDate(), reservation.getConsultTime());

        if (!available) {
            LocalTime now = LocalTime.now();
            if (reservation.getConsultTime().isBefore(now)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 지난 시간입니다. 다른 시간을 선택해주세요.");
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 예약된 날짜/시간입니다.");
            }
        }

        // 예약 가능하면 상태 지정 후 등록
        reservation.setStatus("대기");
        reservationMapper.insertReservation(reservation);
    }
    
    // 사용자 예약 목록 조회
    @Override
    public List<Reservation> getMyReservations(String uId) {
        return reservationMapper.selectByUserId(uId);
    }

    // 예약 수정 (확정 시 이메일 발송함)
    @Override
    public void updateReservation(Reservation reservation) {
        reservationMapper.updateReservation(reservation);

        // 예약이 '확정'으로 바뀌면 이메일 알림
        if ("확정".equals(reservation.getStatus())) {
            emailService.sendConfirmEmail(reservation.getUId(), reservation);
        }
    }

    // 예약 취소
    @Override
    public void cancelReservation(int rId) {
        reservationMapper.deleteReservation(rId);
    }
    
    @Override
    public boolean isAvailableTime(Date consultDate, LocalTime consultTime) {
    List<Reservation> list = reservationMapper.selectByDateTime(consultDate, consultTime);
    return list.isEmpty(); // 예약 없으면 가능함
    }
}