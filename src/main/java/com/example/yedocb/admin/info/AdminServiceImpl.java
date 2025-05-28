package com.example.yedocb.admin.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.yedocb.admin.entity.Admin;
import com.example.yedocb.admin.repository.AdminMapper;

/**
 * packageName    : com.exmple.yedocb.admin.info
 * fileName       : AdminServiceImpl
 * author         : lkm
 * date           : 250527
 * description    : 서비스 구현체 (관리자 등록, 삭제, 아이디/비번 찾기)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminEmailService adminEmailService;
	
	// 필드 및 생성자 주입
	private AdminMapper adminMapper;
	
	public AdminServiceImpl(AdminMapper adminmapper, AdminEmailService adminEmailService) {
		this.adminMapper = adminMapper;
		this.adminEmailService = adminEmailService;
	}

	// 관리자 등록
	@Override
	public void registerStaff(Admin admin) {
		adminMapper.insertAdmin(admin);
	};
	
	// 관리자 삭제
	@Override
	public void deleteStaff(String aId) {
		adminMapper.deleteAdmin(aId);
	};
	
	// 관리자 이메일로 관리자 아이디 찾기
	@Override
	public Admin findAdminId(String aEmail) {
		return adminMapper.findAdminId(aEmail);
	};
	
	// 관리자 아이디로 비밀번호 찾아서 이메일로 임시 비밀번호 발송하기
	@Override
	public String findAdminPassword(String aId, String aEmail) {
		String aPwd = adminMapper.findAdminPassword(aId, aEmail);
		
		if(aPwd != null) {
			adminEmailService.sendPasswordEmail(aEmail, aPwd);
		}
		
		return aPwd;
	};

}
