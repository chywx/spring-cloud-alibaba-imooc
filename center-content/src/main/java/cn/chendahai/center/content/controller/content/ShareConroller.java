package cn.chendahai.center.content.controller.content;

import cn.chendahai.center.content.auth.CheckLogin;
import cn.chendahai.center.content.dao.content.MidUserShareMapper;
import cn.chendahai.center.content.dao.content.ShareMapper;
import cn.chendahai.center.content.domain.dto.content.PageDTO;
import cn.chendahai.center.content.domain.dto.content.ShareDTO;
import cn.chendahai.center.content.domain.dto.content.shareRequestDTO;
import cn.chendahai.center.content.domain.entity.content.MidUserShare;
import cn.chendahai.center.content.domain.entity.content.Share;
import cn.chendahai.center.content.domain.enums.AuditStatusEnum;
import cn.chendahai.center.content.service.content.ShareService;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shares")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareConroller {

    private final ShareService shareService;

    private final ShareMapper shareMapper;

    private final MidUserShareMapper midUserShareMapper;

    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }

    @GetMapping("/q")
    public PageInfo<Share> q(String title, PageDTO pageDTO) {
        return shareService.q(title, pageDTO);
    }

    /*
    积分兑换指定ID的share
    用于：首页-兑换-兑换
     */
    @GetMapping("/exchange/{id}")
    @CheckLogin
    public Share exchangeById(@PathVariable Integer id, HttpServletRequest request) {
        return this.shareService.exchangeById(id, request);
    }


    /*
    投稿
    用于：投稿
     */
    @PostMapping("/contribute")
    @CheckLogin
    public Share addContribute(@RequestBody shareRequestDTO shareRequestDTO, HttpServletRequest request) {
        Share share = new Share();
        BeanUtils.copyProperties(shareRequestDTO, share);
        Integer userId = (Integer) request.getAttribute("id");
        share.setUserId(userId);
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setCover("xxx");
        share.setBuyCount(0);
        share.setAuditStatus(AuditStatusEnum.NOT_YET.name());
        shareMapper.insertSelective(share);
        share = shareMapper.selectByPrimaryKey(share.getId());
        return share;
    }

    /*
    编辑投稿
    用于：投稿
     */
    @PutMapping("/contribute/{id}")
    @CheckLogin
    public Share editContribute(@PathVariable(name = "id") Integer shareId, @RequestBody shareRequestDTO shareRequestDTO) {
        Share share = shareMapper.selectByPrimaryKey(shareId);
        BeanUtils.copyProperties(shareRequestDTO, share);
        int i = shareMapper.updateByPrimaryKeySelective(share);
        log.info("editContribute {}", i);
        return share;
    }


    /*
    根据id预览详情
    用于：投稿-提交投稿-预览
     */
    @GetMapping("/preview/{id}")
    @CheckLogin
    public Share preview(@PathVariable(name = "id") Integer shareId, HttpServletRequest request) {
        Share share = shareMapper.selectByPrimaryKey(shareId);
        return share;
    }

    /*
        查询当前登录用户拥有的share列表
        用于：我的-我的兑换
     */
    @GetMapping("/my")
    @CheckLogin
    public List<Share> my(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        MidUserShare midUserShare = MidUserShare.builder().userId(userId).build();
        List<MidUserShare> midUserShares = midUserShareMapper.select(midUserShare);
        if (CollectionUtils.isEmpty(midUserShares)) {
            return null;
        }
        List<Integer> ids = midUserShares.stream().map(MidUserShare::getShareId).collect(Collectors.toList());
        List<Share> shares = shareMapper.selectByIds(ids);
        return shares;
    }

    /*
        我的投稿
        用于：我的-我的投稿
     */
    @GetMapping("/my/contributions")
    @CheckLogin
    public List<Share> myContributions(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("id");
        Share share = Share.builder().userId(userId).auditStatus(AuditStatusEnum.NOT_YET.name()).build();
        List<Share> shares = shareMapper.select(share);
        return shares;
    }


}
