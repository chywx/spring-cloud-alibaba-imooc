package cn.chendahai.ray.controller;

import cn.chendahai.ray.auth.CheckLogin;
import cn.chendahai.ray.dto.UserAddBonusMsgDTO;
import cn.chendahai.ray.entity.User;
import cn.chendahai.ray.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BonusController {

    private final UserService userService;

    @GetMapping("/me")
    @CheckLogin
    public User me(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        User user = userService.findById(userId);
        return user;
    }

    @GetMapping("/sign")
    @CheckLogin
    public User sign(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        int count = userService.checkSign(userId, "sign");
        log.info("sign userId: {},count: {}", userId, count);
        if (count == 0) {
            userService.addBonus(
                UserAddBonusMsgDTO.builder()
                    .userId(userId)
                    .bonus(20)
                    .description("签到")
                    .event("sign")
                    .build()
            );
        } else {
            log.error("用于已经签到过了");
        }
        User user = userService.findById(userId);
        return user;
    }

}
