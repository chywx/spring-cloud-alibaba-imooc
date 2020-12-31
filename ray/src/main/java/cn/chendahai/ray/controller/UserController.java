package cn.chendahai.ray.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.chendahai.ray.auth.CheckLogin;
import cn.chendahai.ray.dao.BonusEventLogMapper;
import cn.chendahai.ray.dto.JwtTokenRespDTO;
import cn.chendahai.ray.dto.LoginRespDTO;
import cn.chendahai.ray.dto.UserLoginDTO;
import cn.chendahai.ray.dto.UserRespDTO;
import cn.chendahai.ray.entity.BonusEventLog;
import cn.chendahai.ray.entity.User;
import cn.chendahai.ray.service.UserService;
import cn.chendahai.ray.util.JwtOperator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    private final UserService userService;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;
    private final BonusEventLogMapper bonusEventLogMapper;

    @GetMapping("/{id}")
    @CheckLogin
    public User findById(@PathVariable Integer id) {
        log.info("我被请求了...");
        return this.userService.findById(id);
    }

    /**
     * 模拟生成token(假的登录)
     */
    @GetMapping("/gen-token")
    public String genToken() {
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", 12);
        userInfo.put("wxNickname", "Dylan");
        userInfo.put("role", "admin");
        return this.jwtOperator.generateToken(userInfo);
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        // 微信小程序服务端校验是否已经登录的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
            .getSessionInfo(loginDTO.getCode());

        // 微信的openId，用户在微信这边的唯一标示
        String openid = result.getOpenid();

        // 看用户是否注册，如果没有注册就（插入）
        // 如果已经注册
        User user = this.userService.login(loginDTO, openid);

        // 颁发token
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getId());
        userInfo.put("wxNickname", user.getWxNickname());
        userInfo.put("role", user.getRoles());

        String token = jwtOperator.generateToken(userInfo);

        log.info(
            "用户{}登录成功，生成的token = {}, 有效期到:{}",
            loginDTO.getWxNickname(),
            token,
            jwtOperator.getExpirationTime()
        );

        // 构建响应
        return LoginRespDTO.builder()
            .user(
                UserRespDTO.builder()
                    .id(user.getId())
                    .avatarUrl(user.getAvatarUrl())
                    .bonus(user.getBonus())
                    .wxNickname(user.getWxNickname())
                    .build()
            )
            .token(
                JwtTokenRespDTO.builder()
                    .expirationTime(jwtOperator.getExpirationTime().getTime())
                    .token(token)
                    .build()
            )
            .build();
    }

    @GetMapping("/bonus-logs")
    @CheckLogin
    public List<BonusEventLog> bonusLogs(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        List<BonusEventLog> bonusEventLogs = bonusEventLogMapper.selectByUserId(userId);
        return bonusEventLogs;
    }
}
