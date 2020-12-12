package cn.chendahai.center.content.service.content;

import cn.chendahai.center.content.dao.content.ShareMapper;
import cn.chendahai.center.content.dao.messaging.RocketmqTransactionLogMapper;
import cn.chendahai.center.content.domain.dto.content.ShareAuditDTO;
import cn.chendahai.center.content.domain.dto.content.ShareDTO;
import cn.chendahai.center.content.domain.dto.messaging.UserAddBonusMsgDTO;
import cn.chendahai.center.content.domain.dto.user.UserDTO;
import cn.chendahai.center.content.domain.entity.content.Share;
import cn.chendahai.center.content.domain.enums.AuditStatusEnum;
import cn.chendahai.center.content.feignclient.UserCenterFeignClient;
import com.alibaba.fastjson.JSONObject;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final UserCenterFeignClient userCenterFeignClient;
    //    private final RocketMQTemplate rocketMQTemplate;
    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        log.error(">>>share {}", JSONObject.toJSONString(share));
        // 发布人id
        Integer userId = share.getUserId();

        // 1. 代码不可读
        // 2. 复杂的url难以维护：https://user-center/s?ie={ie}&f={f}&rsv_bp=1&rsv_idx=1&tn=baidu&wd=a&rsv_pq=c86459bd002cfbaa&rsv_t=edb19hb%2BvO%2BTySu8dtmbl%2F9dCK%2FIgdyUX%2BxuFYuE0G08aHH5FkeP3n3BXxw&rqlang=cn&rsv_enter=1&rsv_sug3=1&rsv_sug2=0&inputT=611&rsv_sug4=611
        // 3. 难以相应需求的变化，变化很没有幸福感
        // 4. 编程体验不统一
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);

        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        // 用HTTP GET方法去请求，并且返回一个对象
        ResponseEntity<String> forEntity = restTemplate.getForEntity(
            "http://localhost:8081/users/{id}",
            String.class, 2
        );

        System.out.println(forEntity.getBody());
        // 200 OK
        // 500
        // 502 bad gateway...
        System.out.println(forEntity.getStatusCode());
    }

    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }

        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            // 发送半消息。。

            UserAddBonusMsgDTO userAddBonusMsgDTO = UserAddBonusMsgDTO.builder()
                .userId(share.getUserId())
                .bonus(5)
                .build();
            userCenterFeignClient.receive(userAddBonusMsgDTO);
//            this.source.output()
//                .send(
//                    MessageBuilder
//                        .withPayload(
//                            UserAddBonusMsgDTO.builder()
//                                .userId(share.getUserId())
//                                .bonus(50)
//                                .build()
//                        )
//                        // header也有妙用...
//                        .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
//                        .setHeader("share_id", id)
//                        .setHeader("dto", JSON.toJSONString(auditDTO))
//                        .build()
//                );
        } else {
            this.auditByIdInDB(id, auditDTO);
        }
        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    public void auditByIdInDB(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
            .id(id)
            .auditStatus(auditDTO.getAuditStatusEnum().toString())
            .reason(auditDTO.getReason())
            .build();
        this.shareMapper.updateByPrimaryKeySelective(share);

        // 4. 把share写到缓存
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
//        this.auditByIdInDB(id, auditDTO);
//
//        this.rocketmqTransactionLogMapper.insertSelective(
//            RocketmqTransactionLog.builder()
//                .transactionId(transactionId)
//                .log("审核分享...")
//                .build()
//        );
//    }
}

