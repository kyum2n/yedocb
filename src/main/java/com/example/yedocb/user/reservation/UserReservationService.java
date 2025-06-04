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
    
    // 날짜별 예약된 시간 조회
    List<LocalTime> getReservedTimesByDate(Date consultDate);
    
    // 예약 수정
    void updateReservation(Reservation reservation);
    
    // 예약 취소
    void cancelReservation(int rId);
    
    //해당 날짜의 비어있는 시간 조회
    boolean isAvailableTime(Date consultDate, LocalTime consultTime);
}