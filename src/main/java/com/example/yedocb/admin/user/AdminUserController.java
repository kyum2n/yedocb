package com.example.yedocb.admin.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.yedocb.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    // 회원 목록 조회
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    // 회원 등록
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        adminUserService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    // 회원 삭제
    @PostMapping("/{uId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("uId") String uId) {
        adminUserService.deleteUser(uId);
        return ResponseEntity.ok().build();
    }
}