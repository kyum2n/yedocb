package com.example.yedocb.user.reservation;

import com.example.yedocb.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// 변경 가능성이 높음 (아직 ReservationMapper.java가 완성되지 않았기에 완성 후 제작)

@RestController
@RequestMapping("/api/user/reserve")
@RequiredArgsConstructor
public class UserReservationController {

    private final UserReservationService userReservationService;

    // 상담 예약 등록
    @PostMapping
    public ResponseEntity<String> registerReservation(@RequestBody Reservation reservation) {
        userReservationService.registerReservation(reservation);
        return ResponseEntity.ok("예약이 등록되었습니다.");
    }

    // 예약 가능 시간 조회
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailable(@RequestParam String consultDate,
                                                  @RequestParam String consultTime) {
        boolean available = userReservationService.checkAvailableTime(consultDate, consultTime);
        return ResponseEntity.ok(available);
    }

    // 예약 내역 조회 (사용자별)
    @GetMapping("/list")
    public ResponseEntity<List<Reservation>> getUserReservations(@RequestParam String uId) {
        return ResponseEntity.ok(userReservationService.getReservationsByUser(uId));
    }

    // 예약 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateReservation(@RequestBody Reservation reservation) {
        userReservationService.updateReservation(reservation);
        return ResponseEntity.ok("예약이 수정되었습니다.");
    }

    // 예약 취소
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReservation(@RequestParam int rId) {
        userReservationService.deleteReservation(rId);
        return ResponseEntity.ok("예약이 취소되었습니다.");
    }
}