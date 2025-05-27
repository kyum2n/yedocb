package com.example.yedocb.admin.entity;

import lombok.Data;

@Data
public class Admin {
	String aId; //관리자 아이디
	String aEmail; //관리자 이메일
	String aPwd; //관리자 비밀번호
	String role; //관리자 권한 - 최고관리자/직원관리자
	String createdBy; //최고관리자 아이디
}
