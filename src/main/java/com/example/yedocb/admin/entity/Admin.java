package com.example.yedocb.admin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	
	@JsonProperty
	private String aId; //관리자 아이디
	
	@JsonProperty
	private String aEmail; //관리자 이메일
	
	@JsonProperty
	private String aPwd; //관리자 비밀번호
	
	@JsonProperty
	private String role; //관리자 권한 - 최고관리자/직원관리자
	
	@JsonProperty
	private String createdBy; //최고관리자 아이디
	

}
