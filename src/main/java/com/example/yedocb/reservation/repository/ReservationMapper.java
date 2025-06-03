package com.example.yedocb.reservation.repository;


import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.yedocb.reservation.entity.Reservation;

@Mapper
public interface ReservationMapper {
    int insertReservation(Reservation reservation);
    List<Reservation> selectByUserId(String uId);
    int updateReservation(Reservation reservation);
    int deleteReservation(@Param("rId") int rId);
    List<Reservation> selectAllReservation();
    List<Reservation> selectByDate(Date consultDate);
    List<Reservation> selectByDateTime(@Param("consultDate") Date consultDate,
            						   @Param("consultTime") LocalTime consultTime);
    int updateStatus(@Param("rId") int rId, @Param("status") String status);

}