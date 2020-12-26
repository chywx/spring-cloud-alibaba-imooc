package cn.chendahai.center.content.controller.content;

import cn.chendahai.center.content.auth.CheckLogin;
import cn.chendahai.center.content.domain.dto.content.PageDTO;
import cn.chendahai.center.content.domain.dto.content.ShareDTO;
import cn.chendahai.center.content.domain.entity.content.Share;
import cn.chendahai.center.content.service.content.ShareService;
import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareConroller {

    private final ShareService shareService;

    @GetMapping("/{id}")
    @CheckLogin
    public ShareDTO findById(@PathVariable Integer id) {
        return this.shareService.findById(id);
    }

    @GetMapping("/q")
    public PageInfo<Share> q(String title, PageDTO pageDTO) {
        return shareService.q(title, pageDTO);
    }

    @GetMapping("/exchange/{id}")
    @CheckLogin
    public Share exchangeById(@PathVariable Integer id, HttpServletRequest request) {
        return this.shareService.exchangeById(id, request);
    }
}
