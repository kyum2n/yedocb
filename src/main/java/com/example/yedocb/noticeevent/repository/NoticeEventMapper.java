package com.example.yedocb.noticeevent.repository;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeEventMapper {
	// 공지사항/이벤트 등록하기
    void insertNoticeEvent(NoticeEvent noticeEvent);
    
    // 모든 공지사항/이벤트 목록 불러오기    
    List<NoticeEvent> getAllNoticeEvent();

    // 특정 공지사항/이벤트 불러오기
    NoticeEvent getNoticeEventById(int neId);
    
    // 공지사항/이벤트 수정하기
    void updateNoticeEvent(NoticeEvent noticeEvent);
    
    // 공지사항/이벤트 삭제하기
    void deleteNoticeEvent(int neId);
}
