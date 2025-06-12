package com.example.yedocb.noticeevent.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NoticeEvent {
	private int neId;
	private String neTitle;
	private String neContent;
	private String neImageUrl;
	private String neType;
	private String neStartDate;
	private String neCreatedAt;
	private String neUpdatedAt;
	private String neEndDate;
}
