package com.example.yedocb.admin.login;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.yedocb.admin.entity.Admin;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    public AdminLoginController(@Qualifier("adminLoginServiceImpl") AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody Admin loginRequest) {
        boolean success = adminLoginService.loginAdmin(loginRequest.getAId(), loginRequest.getAPwd());
        if (success) {
            return ResponseEntity.ok("로그인 성공!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }
}
