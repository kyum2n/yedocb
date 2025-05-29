package com.example.yedocb.user.reservation;

import com.example.yedocb.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserve")
@RequiredArgsConstructor
public class UserReservationController {

    private final UserReservationService userReservationService;

    // 상담 예약 등록
    @PostMapping
    public ResponseEntity<String> registerReservation(@RequestBody Reservation reservation) {
        userReservationService.makeReservation(reservation);
        return ResponseEntity.ok("예약이 등록되었습니다.");
    }

    // 예약 내역 조회 (사용자별) - 설계서 기준 PathVariable
    @GetMapping("/{uId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable("uId") String uId) {
        return ResponseEntity.ok(userReservationService.getMyReservations(uId));
    }

    // 예약 수정
    @PostMapping("/{rId}/update")  // 설계서 기준 POST 방식 사용
    public ResponseEntity<String> updateReservation(@PathVariable("rId") int rId, @RequestBody Reservation reservation) {
        reservation.setRId(rId); // rId 주입
        userReservationService.updateReservation(reservation);
        return ResponseEntity.ok("예약이 수정되었습니다.");
    }

    // 예약 취소
    @PostMapping("/{rId}/cancel")
    public ResponseEntity<String> cancelReservation(@PathVariable("rId") int rId) {
        userReservationService.cancelReservation(rId);
        return ResponseEntity.ok("예약이 취소되었습니다.");
    }
    
//    // 예약 가능 시간 조회 (나중에 추가해야함)
//    @GetMapping("/check")
//    public ResponseEntity<Boolean> checkAvailable(@RequestParam String consultDate,
//                                                  @RequestParam String consultTime) {
//        boolean available = userReservationService.checkAvailableTime(consultDate, consultTime);
//        return ResponseEntity.ok(available);
//    }
}