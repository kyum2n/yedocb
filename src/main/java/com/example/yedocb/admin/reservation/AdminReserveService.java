package com.example.yedocb.admin.reservation;

import com.example.yedocb.reservation.entity.Reservation;
import java.util.List;

public interface AdminReserveService {
    List<Reservation> getAllReservations();
    void updateReservationStatus(int rId, String status);
    void modifyReservationSchedule(Reservation reservation);
    void deleteReservation(int rId);
    void sendReminderEmails();
}
