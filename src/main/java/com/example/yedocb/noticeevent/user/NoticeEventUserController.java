package com.example.yedocb.noticeevent.user;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import com.example.yedocb.noticeevent.service.NoticeEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticeEvent")
public class NoticeEventUserController {

	// 필드 및 생성자 주입
    private final NoticeEventService noticeEventService;

    public NoticeEventUserController(NoticeEventService noticeEventService) {
        this.noticeEventService = noticeEventService;
    }

    // 모든 공지사항/이벤트 목록 불러오기
    @GetMapping
    public List<NoticeEvent> getAllNoticeEvent() {
        return noticeEventService.getAllNoticeEvent();
    }

    // 특정 공지사항/이벤트 항목 불러오기
    @GetMapping("/{neId}")
    public NoticeEvent getNoticeEventById(@PathVariable("neId") int neId) {
        return noticeEventService.getNoticeEventById(neId);
    }
}
