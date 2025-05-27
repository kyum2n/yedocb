package com.example.yedocb.admin.info;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class AdminController {
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        adminService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{uId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uId) {
        adminService.deleteUser(uId);
        return ResponseEntity.ok().build();
    }
}
