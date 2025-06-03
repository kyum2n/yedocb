package com.example.yedocb.user.info;

import jakarta.validation.Valid;

import com.example.yedocb.jwt.JwtTokenProvider;
import com.example.yedocb.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    
    // 전화번호 수정 : uId 기준으로 연락처 정보만 수정
    @PostMapping("/phone")
    public ResponseEntity<String> updatePhone(@RequestBody User user,
    										  @RequestHeader("Authorization") String token) {
    	
    	String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
    	
        if (!user.getUId().equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정할 수 있습니다.");
        }
    	
        // 직접 phone 형식 검증
        if (user.getUPhone() == null || !user.getUPhone().matches("^010-\\d{4}-\\d{4}$")) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }

        userService.updatePhoneAndPassword(user.getUId(), user.getUPhone(), null);
        return ResponseEntity.ok("연락처가 수정되었습니다.");
    }
    
    // 비밀번호 수정 : uId 기준으로 비밀번호만 수정
    @PostMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid User user, 
    											 @RequestHeader("Authorization") String token) {
    	
    	String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
    	
        if (!user.getUId().equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정할 수 있습니다.");
        }
    	
        try {
        	
            String rawPassword = user.getUPwd();
            String encodedPassword = passwordEncoder.encode(rawPassword); // 암호화
        	
            userService.updatePhoneAndPassword(user.getUId(), null, encodedPassword);
            
            return ResponseEntity.ok("비밀번호가 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // 회원 가입 : 사용자 정보를 받아 DB에 저장
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
    
    // 회원 탈퇴 : uId 기준으로 사용자 정보 삭제
    @PostMapping("/{uId}") 
    public ResponseEntity<String> deleteUser(@PathVariable("uId") String uId, 
    										 @RequestHeader("Authorization") String token) {
    	
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));

        if (!uId.equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 탈퇴할 수 있습니다.");
        }
    	
        userService.deleteUser(uId);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
    
    // 사용자 아이디 찾기 (아이디 일부 공개요청) : 이메일로 등록된 사용자 아이디 일부 반환
    @PostMapping("/find_id")
    public ResponseEntity<String> findUserId(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        User user = userService.findUserId(email);
        if (user != null) {
            String uId = user.getUId();

            String maskedId;
            if (uId.length() <= 3) {
                maskedId = "***";
            } else {
                int maskLength = uId.length() - 3;
                maskedId = uId.substring(0, 3) + "*".repeat(maskLength);
            }

            return ResponseEntity.ok(maskedId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일로 등록된 계정이 없습니다.");
        }
    }
    
    // 사용자 비밀번호 찾기 (임시 비밀번호 발급용) : uId 기준으로 임시 비밀번호 발급
    @PostMapping("/find_password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
    	String uId = payload.get("uId");
    	String email = payload.get("email");
    	User user = userService.findUserPassword(uId, email);
        if (user != null) {
            return ResponseEntity.ok(user.getUPwd()); // 임시 비밀번호 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 사용자를 찾을 수 없습니다.");
        }
    }
}