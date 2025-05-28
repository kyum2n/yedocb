package com.example.yedocb.admin.reservation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.yedocb.reservation.entity.Reservation;
import com.example.yedocb.reservation.repository.ReservationMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com/example/yedocb/admin/reservation
 * fileName       : AdminReserveServiceImpl.java 
 * author         : JKW
 * date           : 2025/05/27
 * description    :
 * ===========================================================
 * DATE          AUTHOR                 NOTE
 * -----------------------------------------------------------
 * 2025/05/27     JKW                  최초작성          
 */
@Service
@RequiredArgsConstructor
public class AdminReserveServiceImpl implements AdminReserveService {
	
    private final ReservationMapper reservationMapper;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationMapper.selectAllReservation();
    }

    @Override
    public void updateReservationStatus(int rId, String status) {
        reservationMapper.updateStatus(rId, status);
    }

    @Override
    public void modifyReservationSchedule(Reservation reservation) {
        reservationMapper.updateReservation(reservation);
    }

    @Override
    public void deleteReservation(int rId) {
        reservationMapper.deleteReservation(rId);
    }

    @Override
    public void sendReminderEmails() {
        // TODO: JavaMailSender로 구현
    }
}