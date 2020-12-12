//package cn.chendahai.center.content.feignclient.fallback;
//
//import cn.chendahai.center.content.domain.dto.messaging.UserAddBonusMsgDTO;
//import cn.chendahai.center.content.domain.dto.user.UserDTO;
//import cn.chendahai.center.content.feignclient.UserCenterFeignClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class UserCenterFeignClientFallback implements UserCenterFeignClient {
//
//    @Override
//    public UserDTO findById(Integer id) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setWxNickname("UserCenterFeignClientFallback>>>流控/降级返回的用户");
//        return userDTO;
//    }
//
//    @Override
//    public void addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO) {
//        log.warn("UserCenterFeignClientFallback>>>远程调用被限流/降级了", userAddBonusMsgDTO);
//    }
//}
