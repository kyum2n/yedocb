package com.example.yedocb.admin.login;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Primary
@Service("adminLoginServiceImpl")  // 빈 이름 명시
public class AdminLoginServiceImpl implements AdminLoginService {
	
	// 필드 및 생성자 주입
	private final AdminMapper adminMapper;
	private final PasswordEncoder passwordEncoder;

    public AdminLoginServiceImpl(AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
	// 관리자 로그인
	@Override
	public Admin loginAdmin(String aId, String aPwd) {
		System.out.println("aId: " + aId + ", aPwd: " + aPwd);
		
		Admin admin = adminMapper.loginAdmin(aId);
		
		if (admin != null 
				&& passwordEncoder.matches(aPwd, admin.getAPwd())
				&& (admin.getRole().equals("SUPERADMIN")) || admin.getRole().equals("ADMIN")){
            return admin; // 로그인 성공
        }
		
		System.out.println("조회 결과: " + admin);
		
		return null; // 로그인 실패
	};
}
