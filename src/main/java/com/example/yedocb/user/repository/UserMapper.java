package com.example.yedocb.user.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.yedocb.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
	
	List<User> selectAllUsers();

    // 전화번호 또는 비밀번호 수정
    int updateUserPhonePwd(@Param("uId") String uId,
            @Param("phone") String phone,
            @Param("pwd") String pwd);
    
    // 회원 가입
    int insertUser(User user);
    
    // 회원 탈퇴
    int deleteUser(@Param("uId") String uId);

    // 이메일로 사용자 아이디 조회
    User selectByUEmail(@Param("uEmail") String uEmail);
    
    // 아이디로 이메일 조회
    User selectByUId(@Param("uId") String uId);

    // 임시 비밀번호로 업데이트
    int updatePassword(@Param("uId") String uId, @Param("pwd") String pwd);
    
    // 마이페이지 정보
    User selectUserInfoForMypage(String uId);
}