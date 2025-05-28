package com.example.yedocb.reservation.repository;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.yedocb.reservation.entity.Reservation;

@Mapper
public interface ReservationMapper {
    int insertReservation(Reservation reservation);
    List<Reservation> selectByUserId(String uId);
    int updateReservation(Reservation reservation);
    int deleteReservation(int rId);
    List<Reservation> selectAllReservation();
    List<Reservation> selectByDate(Date consultDate);
    int updateStatus(int rId, String status);

}
