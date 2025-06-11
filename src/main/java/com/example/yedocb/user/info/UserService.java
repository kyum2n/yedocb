package com.example.yedocb.user.info;

import com.example.yedocb.user.entity.User;

public interface UserService {

    // 전화번호 수정
    void updatePhone(String uId, String phone);

    // 비밀번호 수정
    void updatePassword(String uId, String oldPwd, String newPwd);
    
    // 회원가입
    void registerUser(User user);

    // 회원 탈퇴
    void deleteUser(String uId);
    
    // 사용자 아이디 찾기
    User findUserId(String email);
    
    // 사용자 비밀번호 찾기
    User findUserPassword(String uId); // 임시 비밀번호를 직접 반환
    
    // 사용자 정보
    User getUserInfoForMypage(String uid);
}