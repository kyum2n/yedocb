package com.example.yedocb.user.repository;

import com.example.yedocb.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectByUIdAndPwd(@Param("uId") String uId, @Param("uPwd") String uPwd);
}