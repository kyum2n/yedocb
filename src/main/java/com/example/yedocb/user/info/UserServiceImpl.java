package com.example.yedocb.user.info;

import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.yedocb.user.entity.User;
import com.example.yedocb.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.example.yedocb.user.info
 * fileName       : UserServiceImpl
 * author         : [ysg]
 * date           : [작성일자 : 2025-05-28]
 * description    : 사용자 정보 수정 기능 구현 클래스 및 회원가입 및 탈퇴 기능 추가
 * description    : 비밀번호 암호화 주입 받기 코드 작성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-28        [ysg] 		        최초 생성
 * 2025-06-02        [ysg] 		        수정됨
 */

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 주입받기

    // 전화번호 수정
    @Override
    public void updatePhone(String uId, String phone) {
        userMapper.updateUserPhonePwd(uId, phone, null);
    }

    // 비밀번호 수정
    @Override
    public void updatePassword(String uId, String oldPwd, String newPwd) {
        User user = userMapper.selectByUId(uId);
        String currentPwd = user.getUPwd();

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(oldPwd, currentPwd)) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호가 현재 비밀번호와 동일한지 확인
        if (passwordEncoder.matches(newPwd, currentPwd)) {
            throw new IllegalArgumentException("현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }
        
        // 비밀번호는 8자 이상, 영문, 숫자, 특수문자를 반드시 포함해야 함
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\/\\-]).{8,}$";
        
        if (!newPwd.matches(passwordPattern)) {
        	throw new IllegalArgumentException("비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.");
        }

        // 암호화 후 저장
        String encodedNewPwd = passwordEncoder.encode(newPwd);
        userMapper.updateUserPhonePwd(uId, null, encodedNewPwd);
    }
    
    // 사용자 회원가입을 처리하는 메서드 (전달받은 사용자 정보 DB에 저장함)
    @Override
    public void registerUser(User user) {
    	
    	// 비밀번호 암호화 시킴
        String encodedPwd = passwordEncoder.encode(user.getUPwd());
        user.setUPwd(encodedPwd);
    	
        userMapper.insertUser(user); // 저장함
    }
    
    // 사용자 회원탈퇴를 처리하는 메서드 (지정된 사용자 ID의 계정 삭제함)
    @Override
    public void deleteUser(String uId) {
        userMapper.deleteUser(uId);
    }
    
    // 이메일을 기반으로 사용자 ID를 조회하는 메서드
    @Override
    public User findUserId(String email) {
        return userMapper.selectByUEmail(email);
    }
    
    // 사용자 ID를 기반으로 임시 비밀번호를 생성하고 DB에 저장하는 메서드
    // 실제 이메일 전송은 별도로 구현 필요
    @Override
    public User findUserPassword(String uId) {
        User user = userMapper.selectByUId(uId); // 기존 사용자 조회
        if (user != null) {
        	
        	// 임시 비밀번호 생성
            String tempPwd = UUID.randomUUID().toString().substring(0, 8);
            
            String encodedTempPwd = passwordEncoder.encode(tempPwd); // 임시 비밀번호 암호화
            userMapper.updatePassword(uId, encodedTempPwd); // 암호화된 비밀번호 업데이트
            
            // DB에서 새 비밀번호 반영된 사용자 다시 조회
            User updatedUser = userMapper.selectByUId(uId);

            // 실제 임시 비밀번호(평문)는 이메일 전송 등에 사용해야 하므로 반환할 때 같이 리턴하거나 따로 처리할 수 있음
            updatedUser.setUPwd(tempPwd); // 응답에 평문 임시비밀번호 세팅 (주의: 보안상 응답 노출은 신중히 결정)
            return updatedUser;
        }
        return null;
    }
    
    @Override
    public User getUserInfoForMypage(String uId) {
    	return userMapper.selectUserInfoForMypage(uId);
    }
}