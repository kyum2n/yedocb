package com.example.yedocb.admin.user;

import java.util.List;

import com.example.yedocb.user.entity.User;

public interface AdminUserService {
    List<User> getAllUsers();
    void registerUser(User user);
    void deleteUser(String uId);
}