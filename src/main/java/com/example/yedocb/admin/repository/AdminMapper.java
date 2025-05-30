package com.example.yedocb.admin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.yedocb.admin.entity.Admin;

@Mapper
public interface AdminMapper {
	// 모든 관리자 목록 조회
	List<Admin> selectAllAdmins();

	// 관리자 등록
	int insertAdmin(Admin admin);
	
	// 관리자 삭제
	int deleteAdmin(String aId);
	
	// 관리자 아이디로 관리자 조회
	Admin selectByAId(String aId);
	
	// 관리자 로그인
	Admin loginAdmin(@Param("aId") String aId, @Param("aPwd") String aPwd);
	
	// 관리자 이메일로 관리자 아이디 찾기
	String findAdminId(String aEmail);
	
	// 관리자 아이디로 비밀번호 찾기
	String findAdminPassword(@Param("aId") String aId,@Param("aEmail") String aEmail);
}
