package com.example.yedocb.admin.reservation;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.yedocb.reservation.entity.Reservation;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reserve")
@RequiredArgsConstructor
public class AdminReserveController {

    private final AdminReserveService adminReserveService;


    // 예약 목록 조회
    @GetMapping("/reserves")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(adminReserveService.getAllReservations());
    }


    // 예약 상태 변경
    @PostMapping("/{rId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable("rId") int rId, @RequestBody Map<String, String> statusMap) {
       String status = statusMap.get("status");
    	adminReserveService.updateReservationStatus(rId, status);
        return ResponseEntity.ok("상태 변경 완료");
    }

    // 예약 등록
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        adminReserveService.modifyReservationSchedule(reservation);
        return ResponseEntity.ok(reservation);
    }


    // 예약 변경
    @PostMapping("/{rId}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable("rId") int rId,
            @RequestBody Reservation reservation) {
        reservation.setRId(rId);
        adminReserveService.modifyReservationSchedule(reservation);
        return ResponseEntity.ok(reservation);
    }

    // 예약 삭제
    @PostMapping("/delete/{rId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("rId") int rId) {
        adminReserveService.deleteReservation(rId);
        return ResponseEntity.ok().build();
    }


    // 예약 알림 메일 발송
    @PostMapping("/notify")
    public ResponseEntity<String> sendReminderEmails() {
        adminReserveService.sendReminderEmails();
        return ResponseEntity.ok("알림 메일 발송 완료");
    }
}

