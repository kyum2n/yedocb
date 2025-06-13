package com.example.yedocb.inquiry.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.exmple.yedocb.inquiry.entity
 * fileName       : Inquiry.java
 * author         : lkm
 * date           : 250613
 * description    : 엔티티(1:1 문의)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {
	@JsonProperty
	private int qId;
	
	@JsonProperty
	private String uId;
	
	@JsonProperty
	private String uName;
	
	@JsonProperty
	private String uEmail;
	
	@JsonProperty
	private Boolean visit;
	
	@JsonProperty
	private String qContent;
	
	@JsonProperty
	private LocalDateTime createdAt;
	
	@JsonProperty
	private String qAnswer;
	
	@JsonProperty
	private LocalDateTime answeredAt;
	
	@JsonProperty
	private String qStatus;
}
