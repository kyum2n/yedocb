package com.example.yedocb.user.info;

import jakarta.validation.Valid;

import com.example.yedocb.jwt.JwtTokenProvider;
import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.reservation.EmailService;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final EmailService emailService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> codeStorage = new ConcurrentHashMap<>();

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(new java.util.Random().nextInt(chars.length())));
        }
        return sb.toString();
    }

    // 전화번호 수정 : uId 기준으로 연락처 정보만 수정
    @PostMapping("/phone")
    public ResponseEntity<String> updatePhone(@RequestBody User user,
                                              @RequestHeader("Authorization") String token) {
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
        if (!user.getUId().equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정할 수 있습니다.");
        }
        String rawPhone = user.getUPhone();

        // "-" 제거 (숫자만 남김)
        String onlyDigitsPhone = rawPhone.replaceAll("[^0-9]", "");

        // 검증
        if (onlyDigitsPhone == null || !onlyDigitsPhone.matches("^010\\d{8}$")) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (예: 01012345678 또는 010-1234-5678)");
        }

        // 변환 -> "010-xxxx-xxxx" 형태
        String formattedPhone = onlyDigitsPhone.replaceFirst("(010)(\\d{4})(\\d{4})", "$1-$2-$3");

        userService.updatePhone(user.getUId(), formattedPhone);
        return ResponseEntity.ok("연락처가 수정되었습니다.");
    }

    // 비밀번호 수정 : uId 기준으로 비밀번호만 수정
    @PostMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> passwordMap,
                                                 @RequestHeader("Authorization") String token) {
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
        String uId = passwordMap.get("uId");
        String oldPwd = passwordMap.get("oldPwd");
        String newPwd = passwordMap.get("newPwd");
        if (!uId.equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정할 수 있습니다.");
        }
        try {
            userService.updatePassword(uId, oldPwd, newPwd);
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
    @PostMapping("/Delete/{uId}")
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
        String email = payload.get("uEmail");
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
    public ResponseEntity<String> findUserPassword(@RequestBody Map<String, String> payload) {
        String uId = payload.get("uId");

        // uId로 사용자 조회
        User user = userService.findUserPassword(uId);

        if (user != null) {
            String tempPassword = user.getUPwd();

            // 기존 이메일 예약확인 발송 외에 "임시 비밀번호 발송용" 메서드 추가 호출
            emailService.sendTemporaryPasswordEmail(uId, tempPassword);

            return ResponseEntity.ok("임시 비밀번호가 등록된 이메일로 발송되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 아이디로 사용자를 찾을 수 없습니다.");
        }
    }

    // 마이페이지 정보 조회 / JWT 토큰으로 본인 정보 반환
    @GetMapping("/myinfo")
    public ResponseEntity<User> getMyInfo(@RequestHeader("Authorization") String token) {
        // JWT에서 uId 추출
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));
        // uId로 DB 조회
        User user = userService.getUserInfoForMypage(userIdFromToken);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(user);
    }

    // 사용자 인증 확인용 (프론트에서 토큰 유효성 확인 시 사용)
    @GetMapping("/{uId}")
    public ResponseEntity<String> verifyUser(@PathVariable("uId") String uId,
                                             @RequestHeader("Authorization") String token) {
        String userIdFromToken = jwtTokenProvider.getUserId(token.replace("Bearer ", ""));

        if (!uId.equals(userIdFromToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "토큰 사용자와 요청한 ID가 일치하지 않습니다.");
        }

        return ResponseEntity.ok("사용자 인증 성공");
    }

    // 인증 코드 발송 / 회원가입시 이메일로 인증 코드 발송함 / 6자리 코드 전송 + codeStorage에 저장함
    @PostMapping("/send-code")
    public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> request) {
        String uEmail = request.get("email");

        if (uEmail == null || uEmail.isBlank()) {
            return ResponseEntity.badRequest().body("이메일이 누락되었습니다.");
        }

        String code = generateRandomCode();
        emailService.sendVerificationCode(uEmail, code);
        codeStorage.put(uEmail, code); // TTL 없이 임시 저장

        return ResponseEntity.ok("인증 코드가 전송되었습니다.");
    }

    // 인증 코드 검증 / 사용자가 입력한 인증 코드와 저장된 코드 비교 후 성공 시 200 / 실패시 401에러 발생
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> request) {
        String uEmail = request.get("email");
        String inputCode = request.get("code");

        if (uEmail == null || inputCode == null) {
            return ResponseEntity.badRequest().body("이메일과 인증 코드가 필요합니다.");
        }

        String storedCode = codeStorage.get(uEmail);
        if (storedCode != null && storedCode.equals(inputCode)) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증번호가 맞지 않습니다.");
        }
    }
    
    // 이메일 중복 방지
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmailDuplicate(@RequestParam("email") String email) {
        User existing = userService.findUserId(email); // 또는 userMapper.selectByUEmail(uEmail)

        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입되어있는 이메일입니다.");
        }
        return ResponseEntity.ok("사용 가능한 이메일입니다.");
    }
}