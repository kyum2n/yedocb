package com.example.yedocb.admin.reservation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.reservation.entity.Reservation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reserve")
@RequiredArgsConstructor
public class AdminReserveController {
    private final AdminReserveService adminReserveService;

    @GetMapping("/reserves")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(adminReserveService.getAllReservations());
    }

    @PostMapping("/{rId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("rId") int rId, @RequestBody String status) {
        adminReserveService.updateReservationStatus(rId, status);
        return ResponseEntity.ok("상태 변경 완료");
    }

    @PostMapping
    public ResponseEntity<Reservation> createSchedule(@RequestBody Reservation reservation) {
        adminReserveService.modifyReservationSchedule(reservation);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/{rId}")
    public ResponseEntity<Reservation> updateSchedule(@PathVariable("rId") int rId, @RequestBody Reservation reservation) {
        reservation.setRId(rId);
        adminReserveService.modifyReservationSchedule(reservation);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/delete/{rId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("rId") int rId) {
        adminReserveService.deleteReservation(rId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notify")
    public ResponseEntity<String> sendReminderEmails() {
        adminReserveService.sendReminderEmails();
        return ResponseEntity.ok("알림 메일 발송 완료");
    }
}