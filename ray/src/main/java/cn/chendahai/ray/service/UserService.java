package cn.chendahai.ray.service;

import cn.chendahai.ray.dao.BonusEventLogMapper;
import cn.chendahai.ray.dao.UserMapper;
import cn.chendahai.ray.dto.UserAddBonusMsgDTO;
import cn.chendahai.ray.dto.UserLoginDTO;
import cn.chendahai.ray.entity.BonusEventLog;
import cn.chendahai.ray.entity.User;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.java.emoji.EmojiConverter;
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
//        String content = "abc";
        String content = "\uD83C\uDDE9 \uD83C\uDDFE \uD83C\uDDF1 \uD83C\uDDE6 \uD83C\uDDF3";

        System.out.println(content);
        EmojiConverter emojiConverter = EmojiConverter.getInstance();
        content = emojiConverter.toAlias(content);//将聊天内容进行转义
        System.out.println(content);

        System.out.println(emojiConverter.toUnicode(content));

        User user = new User();
        user.setWxNickname("abc");
        System.out.println(user.getWxNickname());

        String str = "\uD83C\uDF6D";
        System.out.println(str);
        System.out.println(emojiConverter.toAlias(str));

        User userToSave = User.builder()
            .wxId("11")
            .bonus(300)
            .wxNickname(emojiConverter.toAlias(str))
            .build();
        System.out.println(userToSave);


    }

    public User findById(Integer id) {
        // select * from user where id = #{id}
        User user = this.userMapper.selectByPrimaryKey(id);
        if (user != null) {
            user.buildUnicodeWxNickname();
        }
        return user;
    }

    public void updateByUser(User user) {
        user.buildAliaseWxNickname();
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO msgDTO) {
        System.out.println(">>> <<< enter controller add-bonus");
        System.out.println(">>>addBonus>>>" + JSONObject.toJSONString(msgDTO));
        // 1. 为用户加积分
        Integer userId = msgDTO.getUserId();
        Integer bonus = msgDTO.getBonus();
        User user = findById(userId);

        user.setBonus(user.getBonus() + bonus);
        updateByUser(user);

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
            System.out.println("loginDTO:" + JSONObject.toJSONString(loginDTO));
            String wxNickname = loginDTO.getWxNickname();
            EmojiConverter emojiConverter = EmojiConverter.getInstance();
            wxNickname = emojiConverter.toAlias(wxNickname);//将聊天内容进行转义
            User userToSave = User.builder()
                .wxId(openId)
                .bonus(300)
                .wxNickname(wxNickname)
                .avatarUrl(loginDTO.getAvatarUrl())
                .roles("user")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
            System.out.println("wxNickname:" + wxNickname);
            System.out.println("userToSave:" + JSONObject.toJSONString(userToSave));
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
