package com.example.yedocb.user.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;

import lombok.Data;

@Data
public class User {
	@JsonProperty
    @NotBlank
    @Size(min = 4, max = 20)
    private String uId;
    // 4~20자 작성

	@JsonProperty
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$", message = "비밀번호는 영문자+숫자+특수문자 조합 8자 이상이어야 합니다.")
    private String uPwd;
    // 8~15자 작성 (특수문자 사용 가능)
    
	@JsonProperty
    @Email
    private String uEmail;

	@JsonProperty
    private String uName;
	
	@JsonProperty
    private String uPhone;
}