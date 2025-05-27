package com.example.yedocb.admin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.yedocb.reservation.entity.Reservation;
import com.example.yedocb.user.entity.User;

@Repository
@Mapper
public interface AdminMapper {
	// 회원 관리
    List<User> selectAllUsers();
    void insertUser(User user);
    void deleteUser(String uId);

    // 예약 관리
    List<Reservation> selectAllReservation();
    void updateStatus(@Param("rId") int rId, @Param("status") String status);
    void updateReservation(Reservation reservation);
    void deleteReservation(int rId);
}
