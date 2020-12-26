package cn.chendahai.center.user.controller.user;

import cn.chendahai.center.user.domain.dto.messaging.UserAddBonusMsgDTO;
import cn.chendahai.center.user.domain.dto.user.UserAddBonseDTO;
import cn.chendahai.center.user.domain.entity.user.User;
import cn.chendahai.center.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusController {

    private final UserService userService;

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
