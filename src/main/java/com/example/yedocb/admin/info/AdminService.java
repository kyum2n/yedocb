package com.example.yedocb.admin.info;

import java.util.List;

import com.example.yedocb.user.entity.User;

public interface AdminService {
    List<User> getAllUsers();
    void registerUser(User user);
    void deleteUser(String uId);
}
