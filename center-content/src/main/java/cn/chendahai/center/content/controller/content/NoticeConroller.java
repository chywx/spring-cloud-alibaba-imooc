package cn.chendahai.center.content.controller.content;

import cn.chendahai.center.content.domain.dto.content.Notice;
import cn.chendahai.center.content.service.content.NoticeService;
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
