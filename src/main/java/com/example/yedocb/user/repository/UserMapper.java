package com.example.yedocb.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.yedocb.user.entity.User;

@Mapper
public interface UserMapper {
	List<User> selectAllUsers();
	
	// 로그인용 ID, 비밀번호 확인
    User selectByUIdAndPwd(@Param("uId") String uId, @Param("uPwd") String uPwd);
    
    // 전화번호 또는 비밀번호 수정
    int updateUserPhonePwd(@Param("uId") String uId,
            @Param("phone") String phone,
            @Param("pwd") String pwd);
    
    // 회원 가입
    int insertUser(User user);
    User selectByUId(String uId);
    User selectByUEmail(String uEmail);
    int updateUserPhonePwd(String uId, String phone, String pwd);
    int deleteUser(String uId);
    List<User> selectAllUsers();
}