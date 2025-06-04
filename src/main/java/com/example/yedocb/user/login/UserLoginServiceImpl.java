package com.example.yedocb.user.login;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.example.yedocb.user.login
 * fileName       : UserLoginServiceImpl
 * author         : [ysg]
 * date           : [작성일자 : 2025-06-02]
 * description    : 사용자 로그인 기능 구현 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-02        [ysg]              최초 생성
 */

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService{

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String uId, String uPwd) {
        User user = userMapper.selectByUId(uId);

        if (user != null && passwordEncoder.matches(uPwd, user.getUPwd())) {
            return user; // 성공 시 User 객체 반환
        }

        return null; // 실패 시 null
    }
}
