package com.example.yedocb.noticeevent.user;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import com.example.yedocb.noticeevent.service.NoticeEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/noticeEvent")
public class NoticeEventUserController {

    private final NoticeEventService noticeEventService;

    public NoticeEventUserController(NoticeEventService noticeEventService) {
        this.noticeEventService = noticeEventService;
    }

    @GetMapping
    public List<NoticeEvent> getAllNoticeEvent() {
        return noticeEventService.getAllNoticeEvent();
    }

    @GetMapping("/{neId}")
    public NoticeEvent getNoticeEventById(@PathVariable int neId) {
        return noticeEventService.getNoticeEventById(neId);
    }
}
