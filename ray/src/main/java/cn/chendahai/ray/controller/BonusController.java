package cn.chendahai.ray.controller;

import cn.chendahai.ray.auth.CheckLogin;
import cn.chendahai.ray.dto.UserAddBonseDTO;
import cn.chendahai.ray.dto.UserAddBonusMsgDTO;
import cn.chendahai.ray.entity.User;
import cn.chendahai.ray.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        int count = userService.checkSign(userId);
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

    @PutMapping("/add-bonus")
    public User addBonus(@RequestBody UserAddBonseDTO userAddBonseDTO) {
        System.out.println(">>>enter controller add-bonus");
        if (userAddBonseDTO.getBonus() < 0) {
            userAddBonseDTO.setEvent("BUY");
            userAddBonseDTO.setDescription("兑换分享。。。");
        } else {
            userAddBonseDTO.setEvent("CONTRIBUTE");
            userAddBonseDTO.setDescription("投稿加积分。。。");
        }
        Integer userId = userAddBonseDTO.getUserId();
        userService.addBonus(
            UserAddBonusMsgDTO.builder()
                .userId(userId)
                .bonus(userAddBonseDTO.getBonus())
                .description(userAddBonseDTO.getDescription())
                .event(userAddBonseDTO.getEvent())
                .build()
        );
        return this.userService.findById(userId);
    }

    @PutMapping("/receive")
    public void receive(@RequestBody UserAddBonseDTO userAddBonseDTO) {
        userService.receive(userAddBonseDTO);
    }
}
