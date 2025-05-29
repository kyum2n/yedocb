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
    
    private String formatPhoneNumber(String phone) {
        // 01012345678 → 010-1234-5678 형식으로 변환
        if (phone == null) return null;
        return phone.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
    }

    //사용자 전화번호 or 비밀번호를 수정하는 메서드 (전달된 값 중 null이 아닌 항목 수정함)
    @Override
    public void updatePhoneAndPassword(String uId, String phone, String pwd) {
        // phone 값이 들어온 경우에만 포맷 적용
        if (phone != null && !phone.isBlank()) {
            phone = formatPhoneNumber(phone); // 내부 전용 포맷 메서드
        }

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
    public User findUserId(String email) {
        return userMapper.selectByUEmail(email);
    }
    
    // 사용자 ID를 기반으로 임시 비밀번호를 생성하고 DB에 저장하는 메서드
    // 실제 이메일 전송은 별도로 구현 필요
    @Override
    public User findUserPassword(String uId, String email) {
        User user = userMapper.selectByUId(uId); // 기존 사용자 조회
        if (user != null && user.getUEmail().equals(email)) {
            String tempPwd = UUID.randomUUID().toString().substring(0, 8);
            userMapper.updatePassword(uId, tempPwd); // 비밀번호 업데이트

            // DB에서 새 비밀번호 반영된 사용자 다시 조회
            User updatedUser = userMapper.selectByUId(uId);
            return updatedUser;
        }
        return null;
    }
    
}