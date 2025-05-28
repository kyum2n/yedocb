package com.example.yedocb.user.reservation;

import com.example.yedocb.reservation.entity.Reservation;

import java.util.List;
//변경 가능성이 높음 (아직 ReservationMapper.java가 완성되지 않았기에 완성 후 제작)

public interface UserReservationService {
    void registerReservation(Reservation reservation);
    boolean checkAvailableTime(String date, String time);
    List<Reservation> getReservationsByUser(String uId);
    void updateReservation(Reservation reservation);
    void deleteReservation(int rId);
}