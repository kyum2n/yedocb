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
    public boolean login(String uId, String uPwd) {
        // 비밀번호 없이 사용자 조회
        User user = userMapper.selectByUId(uId);

        // 사용자 존재하고 비밀번호 일치하면 true
        if (user != null && passwordEncoder.matches(uPwd, user.getUPwd())) {
            return true;
        }

        return false;
    }
}
