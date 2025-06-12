package com.example.yedocb.noticeevent.service;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import com.example.yedocb.noticeevent.repository.NoticeEventMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeEventService {

    private final NoticeEventMapper noticeEventMapper;

    public NoticeEventService(NoticeEventMapper noticeEventMapper) {
        this.noticeEventMapper = noticeEventMapper;
    }

    public void insertNoticeEvent(NoticeEvent noticeEvent) {
        noticeEventMapper.insertNoticeEvent(noticeEvent);
    }

    public NoticeEvent getNoticeEventById(int neId) {
        return noticeEventMapper.getNoticeEventById(neId);
    }

    public List<NoticeEvent> getAllNoticeEvent() {
        return noticeEventMapper.getAllNoticeEvent();
    }

    public void updateNoticeEvent(NoticeEvent noticeEvent) {
        noticeEventMapper.updateNoticeEvent(noticeEvent);
    }

    public void deleteNoticeEvent(int neId) {
        noticeEventMapper.deleteNoticeEvent(neId);
    }
}
