package com.example.yedocb.user.reservation;

import com.example.yedocb.jwt.JwtTokenProvider;
import com.example.yedocb.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reserve")
@RequiredArgsConstructor
public class UserReservationController {

    private final UserReservationService userReservationService;
    private final JwtTokenProvider jwtTokenProvider;

    // 상담 예약 등록
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
    		 										  @RequestHeader("Authorization") String token) {
    	
        // JWT에서 로그인한 사용자 uId 추출
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
        
        // Reservation 객체의 uId를 강제로 로그인한 사용자로 설정
        reservation.setUId(userIdFromToken);
        
        // 예약 등록
        userReservationService.makeReservation(reservation);
        return ResponseEntity.ok(reservation);
    }

    // 예약 내역 조회 (사용자별) - 설계서 기준 PathVariable
    @GetMapping("/{uId}")
    public ResponseEntity<List<Reservation>> getUserReservations(
    		@PathVariable("uId") String uId, 
    		@RequestHeader("Authorization") String token) {
    	
        // JWT 에서 로그인한 사용자 uId 추출
    	String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
    	
        // 본인 확인
        if (!uId.equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 예약만 조회할 수 있습니다.");
        }
        
        // 본인 예약 조회
        return ResponseEntity.ok(userReservationService.getMyReservations(uId));
    }

    // 예약 수정
    @PostMapping("/{rId}/update")  // 설계서 기준 POST 방식 사용
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable("rId") int rId,
            @RequestBody Reservation reservation,
            @RequestHeader("Authorization") String token) {

        reservation.setRId(rId);

        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));

        // 본인 예약인지 체크
        Reservation existing = userReservationService.getMyReservations(userIdFromToken)
                .stream()
                .filter(r -> r.getRId() == rId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 예약만 수정할 수 있습니다."));

        // 본인 예약이면 업데이트 호출
        userReservationService.updateReservation(reservation);

        return ResponseEntity.ok(reservation);
    }

    // 예약 취소
    @PostMapping("/{rId}/cancel")
    public ResponseEntity<String> cancelReservation(
            @PathVariable("rId") int rId,
            @RequestHeader("Authorization") String token) {

        // JWT에서 로그인한 사용자 uId 추출
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));

        // 본인 예약인지 체크
        Reservation existing = userReservationService.getMyReservations(userIdFromToken)
                .stream()
                .filter(r -> r.getRId() == rId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 예약만 취소할 수 있습니다."));

        // 본인 예약이면 취소
        userReservationService.cancelReservation(rId);

        return ResponseEntity.ok("예약이 취소되었습니다.");
    }
    
 // 특정 날짜에 이미 예약된 시간 목록 반환
    @GetMapping("/disabled-times")
    public ResponseEntity<List<String>> getDisabledTimes(
        @RequestParam("consultDate")
        @DateTimeFormat(pattern = "yyyy-MM-dd") Date consultDate) {

        List<LocalTime> reservedTimes = userReservationService.getReservedTimesByDate(consultDate);

        // 프론트에서 쓰기 쉽게 HH:mm 문자열로 변환
        List<String> disabled = reservedTimes.stream()
            .map(time -> time.toString().substring(0, 5)) // "14:30:00" → "14:30"
            .toList();

        return ResponseEntity.ok(disabled);
    }
    
    
    // 예약 가능 시간 조회 (나중에 추가해야함)
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailable(
    @RequestParam("consultDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date consultDate,

    @RequestParam("consultTime")
    @DateTimeFormat(pattern = "HH:mm") LocalTime consultTime) {

    boolean available = userReservationService.isAvailableTime(consultDate, consultTime);
    return ResponseEntity.ok(available);
    }
}