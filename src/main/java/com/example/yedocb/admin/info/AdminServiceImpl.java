package com.example.yedocb.admin.info;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.yedocb.user.entity.User;
import com.example.yedocb.admin.repository.AdminMapper;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com/example/yedocb/admin/info
 * fileName       : AdminServiceImpl.java 
 * author         : JKW
 * date           : 2025/05/27
 * description    :
 * ===========================================================
 * DATE          AUTHOR                 NOTE
 * -----------------------------------------------------------
 * 2025/05/27     JKW                  최초작성          
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    
    public AdminServiceImpl(AdminMapper adminMapper) {
    	this.adminMapper = adminMapper;
    }
    
    @Override
    public List<User> getAllUsers() {
        return adminMapper.selectAllUsers();
    }

    @Override
    public void registerUser(User user) {
        adminMapper.insertUser(user);
    }

    @Override
    public void deleteUser(String uId) {
        adminMapper.deleteUser(uId);
    }
}
