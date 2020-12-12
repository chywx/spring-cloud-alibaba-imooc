package cn.chendahai.center.content.feignclient;

import cn.chendahai.center.content.domain.dto.messaging.UserAddBonusMsgDTO;
import cn.chendahai.center.content.domain.dto.user.UserDTO;
import cn.chendahai.center.content.feignclient.fallbackfactory.UserCenterFeignClientFallbackFactory;
import cn.chendahai.center.content.feignclient.interceptor.TokenRelayRequestIntecepor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "user-center", configuration = GlobalFeignConfiguration.class)
@FeignClient(name = "center-user",
//    fallback = UserCenterFeignClientFallback.class,
    fallbackFactory = UserCenterFeignClientFallbackFactory.class
    , configuration = TokenRelayRequestIntecepor.class
)
public interface UserCenterFeignClient {

    /**
     * http://user-center/users/{id}
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);

    @PutMapping("/users/add-bonus")
    void addBonus(@RequestBody UserAddBonusMsgDTO userAddBonusMsgDTO);
}
