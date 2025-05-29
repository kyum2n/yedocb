package com.example.yedocb.user.info;

import com.example.yedocb.user.entity.User;

public interface UserService {
	// 전화번호 및 비밀번호 수정
    void updatePhoneAndPassword(String uId, String phone, String pwd);

    // 회원가입
    void registerUser(User user);

    // 회원 탈퇴
    void deleteUser(String uId);
    
    // 사용자 아이디 찾기
    String findUserId(String email);
    
    // 사용자 비밀번호 찾기
    String findUserPassword(String uId, String uEmail); // 임시 비밀번호를 직접 반환
}