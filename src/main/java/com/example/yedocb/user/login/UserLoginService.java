package com.example.yedocb.user.login;

import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserMapper userMapper;

    public boolean login(String uId, String uPwd) {
        User user = userMapper.selectByUIdAndPwd(uId, uPwd);
        return user != null;
    }
}