package com.example.yedocb.admin.info;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Service("adminServiceImpl")
@Primary
public class AdminServiceImpl implements AdminService {

	// 필드 및 생성자 주입
    private final AdminMapper adminMapper;
    private final AdminEmailService adminEmailService;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminMapper adminMapper, AdminEmailService adminEmailService, PasswordEncoder passwordEncoder) {
        this.adminMapper = adminMapper;
        this.adminEmailService = adminEmailService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public List<Admin> getAllAdmins(){
    	return adminMapper.selectAllAdmins();
    }
    
    @Override
    public void registerStaff(Admin admin) {
    	admin.setAPwd(passwordEncoder.encode(admin.getAPwd())); // 암호화
        adminMapper.insertAdmin(admin);
    }

    @Override
    public void deleteStaff(String aId) {
        adminMapper.deleteAdmin(aId);
    }

    @Override
    public Admin findAdminId(String aEmail) {
        return adminMapper.findAdminId(aEmail);
    }

    @Override
    public String findAdminPassword(String aId, String aEmail) {
        String aPwd = adminMapper.findAdminPassword(aId, aEmail);
        if(aPwd != null) {
        	adminEmailService.sendPasswordEmail(aEmail, aPwd);
        }
        
        return aPwd;
    }
}

