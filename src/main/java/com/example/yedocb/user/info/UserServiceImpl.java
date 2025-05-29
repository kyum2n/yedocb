package com.example.yedocb.user.info;

import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.yedocb.user.info
 * fileName       : UserServiceImpl
 * author         : [ysg]
 * date           : [작성일자 : 2025-05-28]
 * description    : 사용자 정보 수정 기능 구현 클래스 및 회원가입 및 탈퇴 기능 추가
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-28        [ysg] 		        최초 생성
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    //사용자 전화번호 or 비밀번호를 수정하는 메서드 (전달된 값 중 null이 아닌 항목 수정함)
    @Override
    public void updatePhoneAndPassword(String uId, String phone, String pwd) {
        userMapper.updateUserPhonePwd(uId, phone, pwd);
    }
    
    // 사용자 회원가입을 처리하는 메서드 (전달받은 사용자 정보 DB에 저장함)
    @Override
    public void registerUser(User user) {
        userMapper.insertUser(user);
    }
    
    // 사용자 회원탈퇴를 처리하는 메서드 (지정된 사용자 ID의 계정 삭제함)
    @Override
    public void deleteUser(String uId) {
        userMapper.deleteUser(uId);
    }
    
    // 이메일을 기반으로 사용자 ID를 조회하는 메서드
    @Override
    public String findUserId(String email) {
        return userMapper.selectByUEmail(email);
    }
    
    // 사용자 ID를 기반으로 임시 비밀번호를 생성하고 DB에 저장하는 메서드
    // 실제 이메일 전송은 별도로 구현 필요 (현재는 콘솔 출력으로 대체)
    @Override
    public String findUserPassword(String uId, String email) {
        String emailFromDB = userMapper.selectByUId(uId);
        if (emailFromDB != null && emailFromDB.equals(email)) {
            String tempPwd = UUID.randomUUID().toString().substring(0, 8);
            userMapper.updatePassword(uId, tempPwd);
            System.out.println("임시 비밀번호: " + tempPwd); // 테스트용 출력
            return tempPwd;
        }
        return null;
    }
    
}