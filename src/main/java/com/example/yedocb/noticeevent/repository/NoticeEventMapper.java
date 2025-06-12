package com.example.yedocb.noticeevent.repository;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeEventMapper {
    void insertNoticeEvent(NoticeEvent noticeEvent);
    NoticeEvent getNoticeEventById(int neId);
    List<NoticeEvent> getAllNoticeEvent();
    void updateNoticeEvent(NoticeEvent noticeEvent);
    void deleteNoticeEvent(int neId);
}
