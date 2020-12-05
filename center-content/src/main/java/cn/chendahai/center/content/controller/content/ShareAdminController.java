package cn.chendahai.center.content.controller.content;

import cn.chendahai.center.content.auth.CheckAuthorization;
import cn.chendahai.center.content.domain.dto.content.ShareAuditDTO;
import cn.chendahai.center.content.domain.entity.content.Share;
import cn.chendahai.center.content.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/shares")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController {
    private final ShareService shareService;
    @PutMapping("/audit/{id}")
    @CheckAuthorization("admin")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO) {
        return this.shareService.auditById(id, auditDTO);
    }

}
