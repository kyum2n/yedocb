package com.example.yedocb.admin.login;

import org.springframework.stereotype.Service;

import com.example.yedocb.admin.entity.Admin;
import com.example.yedocb.admin.repository.AdminMapper;

/**
 * packageName    : com.exmple.yedocb.admin.login
 * fileName       : AdminLoginServiceImpl
 * author         : lkm
 * date           : 250527
 * description    : 서비스 구현체 (관리자 로그인)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Service("adminLoginServiceImpl")  // 빈 이름 명시
public class AdminLoginServiceImpl implements AdminLoginService {
    private final AdminMapper adminMapper;

    public AdminLoginServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

	// 관리자 로그인
	@Override
	public boolean loginAdmin(String aId, String aPwd) {
		System.out.println("aId: " + aId + ", aPwd: " + aPwd);
		Admin admin = adminMapper.loginAdmin(aId, aPwd);
		System.out.println("조회 결과: " + admin);
		return admin != null;
	};
}
