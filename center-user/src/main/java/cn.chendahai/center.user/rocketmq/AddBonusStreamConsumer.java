//package cn.chendahai.center.user.rocketmq;
//
//import cn.chendahai.center.user.service.user.UserService;
//import com.itmuch.usercenter.dao.bonus.BonusEventLogMapper;
//import com.itmuch.usercenter.dao.user.UserMapper;
//import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
//import com.itmuch.usercenter.domain.entity.bonus.BonusEventLog;
//import com.itmuch.usercenter.domain.entity.user.User;
//import com.itmuch.usercenter.service.user.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Slf4j
//public class AddBonusStreamConsumer {
//    private final UserService userService;
//
//    @StreamListener(Sink.INPUT)
//    public void receive(UserAddBonusMsgDTO message) {
//        message.setEvent("CONTRIBUTE");
//        message.setDescription("投稿加积分..");
//        this.userService.addBonus(message);
//    }
//}
