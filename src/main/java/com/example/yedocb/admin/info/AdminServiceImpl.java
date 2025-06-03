package com.example.yedocb.admin.info;

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
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final AdminEmailService adminEmailService;

    // 생성자 주입 (직접 명시!)
    public AdminServiceImpl(AdminMapper adminMapper, AdminEmailService adminEmailService) {
        this.adminMapper = adminMapper;
        this.adminEmailService = adminEmailService;
    }

    @Override
    public void registerStaff(Admin admin) {
        adminMapper.insertAdmin(admin);
    }

    @Override
    public void deleteStaff(String aId) {
        adminMapper.deleteAdmin(aId);
    }

    @Override
    public String findAdminId(String aEmail) {
        return adminMapper.findAdminId(aEmail);
    }

    @Override
    public String findAdminPassword(String aId, String aEmail) {
        String aPwd = adminMapper.findAdminPassword(aId, aEmail);
        if (aPwd != null) {
            adminEmailService.sendPasswordEmail(aEmail, aPwd);
        }
        return aPwd;
    }
}

