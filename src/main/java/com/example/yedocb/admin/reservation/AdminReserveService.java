package com.example.yedocb.admin.reservation;

import java.util.List;

import com.example.yedocb.reservation.entity.Reservation;

public interface AdminReserveService {
	// 예약 목록 조회
    List<Reservation> getAllReservations();
    
    // 예약 등록
    void updateReservationStatus(int rId, String status);
    
    // 예약 변경
    void modifyReservationSchedule(Reservation reservation);
    
    // 예약 삭제
    void deleteReservation(int rId);
    
    // 예약 알림 메일 발송
    void sendReminderEmails();
}