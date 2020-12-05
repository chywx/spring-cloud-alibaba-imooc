package cn.chendahai.center.content.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// 脱离ribbon的使用
@FeignClient(name = "baidu", url = "http://www.baidu.com")
public interface TestBaiduFeignClient {
    @GetMapping("")
    String index();
}
