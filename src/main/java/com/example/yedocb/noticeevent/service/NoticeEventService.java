package com.example.yedocb.noticeevent.service;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import com.example.yedocb.noticeevent.repository.NoticeEventMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeEventService {

	// 필드 및 생성자 주입
    private final NoticeEventMapper noticeEventMapper;

    public NoticeEventService(NoticeEventMapper noticeEventMapper) {
        this.noticeEventMapper = noticeEventMapper;
    }

    // 공지사항/이벤트 등록하기
    public void insertNoticeEvent(NoticeEvent noticeEvent) {
        noticeEventMapper.insertNoticeEvent(noticeEvent);
    }

    // 특정 공지사항/이벤트 항목 불러오기
    public NoticeEvent getNoticeEventById(int neId) {
        return noticeEventMapper.getNoticeEventById(neId);
    }

    // 모든 공지사항/이벤트 목록 불러오기
    public List<NoticeEvent> getAllNoticeEvent() {
        return noticeEventMapper.getAllNoticeEvent();
    }

    // 공지사항/이벤트 수정하기
    public void updateNoticeEvent(NoticeEvent noticeEvent) {
        noticeEventMapper.updateNoticeEvent(noticeEvent);
    }

    // 공지사항/이벤트 삭제하기
    public void deleteNoticeEvent(int neId) {
        noticeEventMapper.deleteNoticeEvent(neId);
    }
}
