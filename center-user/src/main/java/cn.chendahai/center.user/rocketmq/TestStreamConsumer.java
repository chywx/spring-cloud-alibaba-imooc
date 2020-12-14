package cn.chendahai.center.user.rocketmq;/**
 * @author lql
 * @date 2020/12/14 0014 下午 17:53
 * Description：
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/12/14 0014
 */
@Service
@Slf4j
public class TestStreamConsumer {

    @StreamListener(Sink.INPUT)
    public void receive(String messageBody) {
        log.info("通过stream收到消息了： message {}", messageBody);
    }

}
