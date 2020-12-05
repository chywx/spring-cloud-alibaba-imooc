package cn.chendahai.center.user;

import cn.chendahai.center.user.dao.user.UserMapper;
import cn.chendahai.center.user.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    /**
     * // 作业1：课后研究一下@Resource和@Autowired的区别
     * // 面试题
     */
    private final UserMapper userMapper;

    @GetMapping("/test")
    public User testInsert() {
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        this.userMapper.insertSelective(user);

        return user;
    }

    // q?id=1&wxId=aaa&...
    @GetMapping("/q")
    public User query(User user) {
        return user;
    }
}
