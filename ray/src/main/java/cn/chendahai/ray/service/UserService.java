package cn.chendahai.ray.service;

import cn.chendahai.ray.dao.BonusEventLogMapper;
import cn.chendahai.ray.dao.UserMapper;
import cn.chendahai.ray.dto.UserAddBonusMsgDTO;
import cn.chendahai.ray.dto.UserLoginDTO;
import cn.chendahai.ray.entity.BonusEventLog;
import cn.chendahai.ray.entity.User;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    public static void main(String[] args) {
        String str = "\uD83C\uDDE9 \uD83C\uDDFE \uD83C\uDDF1 \uD83C\uDDE6 \uD83C\uDDF3";
        System.out.println(str);
    }

    public User findById(Integer id) {
        // select * from user where id = #{id}
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO msgDTO) {
        System.out.println(">>> <<< enter controller add-bonus");
        System.out.println(">>>addBonus>>>" + JSONObject.toJSONString(msgDTO));
        // 1. 为用户加积分
        Integer userId = msgDTO.getUserId();
        Integer bonus = msgDTO.getBonus();
        User user = this.userMapper.selectByPrimaryKey(userId);

        user.setBonus(user.getBonus() + bonus);
        this.userMapper.updateByPrimaryKeySelective(user);

        // 2. 记录日志到bonus_event_log表里面
        this.bonusEventLogMapper.insert(
            BonusEventLog.builder()
                .userId(userId)
                .value(bonus)
                .event(msgDTO.getEvent())
                .createTime(new Date())
                .description(msgDTO.getDescription())
                .build()
        );
        log.info("积分添加完毕...");
    }

    public User login(UserLoginDTO loginDTO, String openId) {
        User user = this.userMapper.selectOne(
            User.builder()
                .wxId(openId)
                .build()
        );
        if (user == null) {
            User userToSave = User.builder()
                .wxId(openId)
                .bonus(300)
                .wxNickname(loginDTO.getWxNickname())
                .avatarUrl(loginDTO.getAvatarUrl())
                .roles("user")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
            this.userMapper.insertSelective(
                userToSave
            );
            return userToSave;
        }
        return user;
    }

    public int checkSign(Integer userId) {
        return userMapper.checkSign(userId);
    }
}
