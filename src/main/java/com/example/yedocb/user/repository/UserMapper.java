package com.example.yedocb.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.yedocb.user.entity.User;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    User selectByUId(String uId);
    User selectByUEmail(String uEmail);
    int updateUserPhonePwd(String uId, String phone, String pwd);
    int deleteUser(String uId);
    List<User> selectAllUsers();
}