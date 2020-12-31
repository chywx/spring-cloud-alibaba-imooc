package cn.chendahai.ray.service;

import cn.chendahai.ray.dao.NoticeMapper;
import cn.chendahai.ray.entity.Notice;
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

