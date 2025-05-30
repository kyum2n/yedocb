package com.example.yedocb.user.reservation;

import com.example.yedocb.reservation.entity.Reservation;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
//변경 가능성이 높음 (아직 ReservationMapper.java가 완성되지 않았기에 완성 후 제작)

public interface UserReservationService {
	
	// 예약 등록
    void makeReservation(Reservation reservation);
    
    // 사용자 예약 목록 조회
    List<Reservation> getMyReservations(String uId);
    
    // 예약 수정
    void updateReservation(Reservation reservation);
    
    // 예약 취소
    void cancelReservation(int rId);
    
    boolean isAvailableTime(Date consultDate, LocalTime consultTime);
}