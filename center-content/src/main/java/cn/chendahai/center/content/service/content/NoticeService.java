package cn.chendahai.center.content.service.content;

import cn.chendahai.center.content.dao.content.NoticeMapper;
import cn.chendahai.center.content.domain.dto.content.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public Notice newest() {
        return noticeMapper.newest();
    }

}

