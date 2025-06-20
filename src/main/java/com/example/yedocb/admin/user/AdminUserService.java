package com.example.yedocb.admin.user;

import java.util.List;

import com.example.yedocb.user.entity.User;

public interface AdminUserService {
	// 회원 목록 조회
    List<User> getAllUsers();
    
    // 회원 등록
    void registerUser(User user);
    
    // 회원 삭제
    void deleteUser(String uId);
}