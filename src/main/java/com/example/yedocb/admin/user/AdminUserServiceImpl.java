package com.example.yedocb.admin.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.exmple.yedocb.admin.user
 * fileName       : AdminUserServiceImpl
 * author         : jkw
 * date           : 25.05.28
 * description    : 
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public void registerUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void deleteUser(String uId) {
        userMapper.deleteUser(uId);
    }
}
