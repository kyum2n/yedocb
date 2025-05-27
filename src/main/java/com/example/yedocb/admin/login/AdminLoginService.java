package com.example.yedocb.admin.login;

import com.example.yedocb.admin.entity.Admin;

public interface AdminLoginService {
	// 관리자 로그인
	boolean loginAdmin(String aId, String aPwd);
}
