package com.example.yedocb.admin.info;

import com.example.yedocb.admin.entity.Admin;

public interface AdminService {
	// 관리자 등록
	void registerStaff(Admin admin);
	
	// 관리자 삭제
	void deleteStaff(String aId);
	
	// 관리자 이메일로 관리자 아이디 찾기
	Admin findAdminId(String aEmail);
	
	// 관리자 아이디로 비밀번호 찾아서 이메일로 임시 비밀번호 발송하기
	String findAdminPassword(String aId, String aEmail);
}
