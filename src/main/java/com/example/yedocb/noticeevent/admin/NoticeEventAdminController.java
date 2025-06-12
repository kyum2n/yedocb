package com.example.yedocb.noticeevent.admin;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import com.example.yedocb.noticeevent.service.NoticeEventService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/noticeEvent")
public class NoticeEventAdminController {

    private final NoticeEventService noticeEventService;

    public NoticeEventAdminController(NoticeEventService noticeEventService) {
        this.noticeEventService = noticeEventService;
    }

    @PostMapping
    public void createNoticeEvent(@RequestBody NoticeEvent noticeEvent) {
        noticeEventService.insertNoticeEvent(noticeEvent);
    }

    @PutMapping("/{neId}")
    public void updateNoticeEvent(@PathVariable int neId, @RequestBody NoticeEvent noticeEvent) {
        noticeEvent.setNeId(neId);
        noticeEventService.updateNoticeEvent(noticeEvent);
    }

    @DeleteMapping("/{neId}")
    public void deleteNoticeEvent(@PathVariable int neId) {
        noticeEventService.deleteNoticeEvent(neId);
    }
}
