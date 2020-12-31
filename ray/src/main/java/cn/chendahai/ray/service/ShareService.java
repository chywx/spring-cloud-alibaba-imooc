package cn.chendahai.ray.service;

import cn.chendahai.ray.dao.MidUserShareMapper;
import cn.chendahai.ray.dao.ShareMapper;
import cn.chendahai.ray.dto.PageDTO;
import cn.chendahai.ray.dto.ShareAuditDTO;
import cn.chendahai.ray.dto.ShareDTO;
import cn.chendahai.ray.dto.UserAddBonseDTO;
import cn.chendahai.ray.dto.UserAddBonusMsgDTO;
import cn.chendahai.ray.dto.UserDTO;
import cn.chendahai.ray.entity.MidUserShare;
import cn.chendahai.ray.entity.Share;
import cn.chendahai.ray.entity.User;
import cn.chendahai.ray.enums.AuditStatusEnum;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
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
    private final MidUserShareMapper midUserShareMapper;
    private final UserService userService;

    public ShareDTO findById(Integer id) {
        // 获取分享详情
        Share share = this.shareMapper.selectByPrimaryKey(id);
        log.error(">>>share {}", JSONObject.toJSONString(share));
        // 发布人id
        Integer userId = share.getUserId();

        User user = this.userService.findById(userId);

        ShareDTO shareDTO = new ShareDTO();
        // 消息的装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(user.getWxNickname());
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
        if (!Objects.equals(AuditStatusEnum.NOT_YET.name(), share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }

        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            // 发送半消息。。

            UserAddBonseDTO userAddBonseDTO = UserAddBonseDTO.builder()
                .userId(share.getUserId())
                .bonus(5)
                .build();
            userService.receive(userAddBonseDTO);
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

    public PageInfo<Share> q(String title, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNo(), pageDTO.getPageSize());
        List<Share> shares = this.shareMapper.selectByParam(title);
        return new PageInfo<>(shares);
    }

    public Share exchangeById(Integer id, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        User user = userService.findById(userId);
        // 1.根据id查询share，校验是否存在

        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("改分享不存在！");
        }
        MidUserShare midUserShare = this.midUserShareMapper.selectOne(
            MidUserShare.builder()
                .shareId(id)
                .userId(userId)
                .build()
        );
        // 如果兑换过了，直接返回
        if (midUserShare != null) {
            return share;
        }
        // 2. 根据当前登录的用户id，查询几分是否够

        if (share.getPrice() > user.getBonus()) {
            throw new IllegalArgumentException("用户的几分不够用！");
        }

        // 3. 扣减几分&插入记录
        userService.addBonus(
            UserAddBonusMsgDTO.builder()
                .userId(userId)
                .bonus(0 - share.getPrice())
                .build()
        );
        midUserShareMapper.insert(
            MidUserShare.builder()
                .userId(userId)
                .shareId(id)
                .build()
        );
        return share;
    }
}

