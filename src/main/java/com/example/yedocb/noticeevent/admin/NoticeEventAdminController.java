package com.example.yedocb.noticeevent.admin;

import com.example.yedocb.noticeevent.entity.NoticeEvent;
import com.example.yedocb.noticeevent.service.NoticeEventService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/noticeEvent")
public class NoticeEventAdminController {

	// 필드 및 생성자 주입
    private final NoticeEventService noticeEventService;

    public NoticeEventAdminController(NoticeEventService noticeEventService) {
        this.noticeEventService = noticeEventService;
    }

    // 모든 공지사항/이벤트 목록 불러오기
    @GetMapping
    public List<NoticeEvent> getAllNoticeEvent() {
        return noticeEventService.getAllNoticeEvent();
    }
    
    // 공지사항/이벤트 등록하기
    @PostMapping
    public void createNoticeEvent(@RequestBody NoticeEvent noticeEvent) {
        noticeEventService.insertNoticeEvent(noticeEvent);
    }

    // 공지사항/이벤트 수정하기
    @PutMapping("/{neId}")
    public void updateNoticeEvent(@PathVariable("neId") int neId, @RequestBody NoticeEvent noticeEvent) {
        noticeEvent.setNeId(neId);
        noticeEventService.updateNoticeEvent(noticeEvent);
    }

    // 공지사항/이벤트 삭제하기
    @DeleteMapping("/{neId}")
    public void deleteNoticeEvent(@PathVariable("neId") int neId) {
        noticeEventService.deleteNoticeEvent(neId);
    }
}
