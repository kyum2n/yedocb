package com.example.yedocb.user.login;

import com.example.yedocb.user.entity.User;

public interface UserLoginService {
    User login(String uId, String uPwd);
}