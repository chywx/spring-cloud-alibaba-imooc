package cn.chendahai.ray.controller;

import cn.chendahai.ray.entity.Notice;
import cn.chendahai.ray.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeConroller {

    private final NoticeService noticeService;

    @GetMapping("/newest")
    public Notice newest() {
        return noticeService.newest();
    }

}
