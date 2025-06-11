package com.example.yedocb.reservation.repository;


import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.yedocb.reservation.entity.Reservation;

@Mapper
public interface ReservationMapper {
	// 예약 목록 불러오기
	List<Reservation> selectAllReservation();

	// 사용자별 예약 목록 불러오기
	List<Reservation> selectByUserId(String uId);
	
	// 예약 등록
    int insertReservation(Reservation reservation);
    
    // 예약 변경하기
    int updateReservation(Reservation reservation);
    
    // 예약 삭제하기
    int deleteReservation(@Param("rId") int rId);
    
    // 예약 가능한 날짜 불러오기
    List<Reservation> selectByDate(Date consultDate);
    
    // 해당 날짜의 예약 가능한 시간 불러오기
    List<Reservation> selectByDateTime(@Param("consultDate") Date consultDate,
            						   @Param("consultTime") LocalTime consultTime);
    
    // 예약 상태 변경하기
    int updateStatus(@Param("rId") int rId, @Param("status") String status);
    List<LocalTime> selectTimesByDate(Date consultDate);

}