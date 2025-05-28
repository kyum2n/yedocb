package com.example.yedocb.user.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

import lombok.Data;

@Data
public class User {
    @NotBlank
    @Size(min = 4, max = 20)
    private String uId;
    // 4~20자 작성

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$", message = "비밀번호는 영문자+숫자+특수문자 조합 8자 이상이어야 합니다.")
    private String uPwd;
    // 8~15자 작성 (특수문자 사용 가능)
    
    @Email
    private String uEmail;

    private String uName;
    private String uPhone;
}