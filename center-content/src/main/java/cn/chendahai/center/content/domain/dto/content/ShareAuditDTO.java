package cn.chendahai.center.content.domain.dto.content;

import cn.chendahai.center.content.domain.enums.AuditStatusEnum;
import lombok.Data;

@Data
public class ShareAuditDTO {

    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;
    /**
     * 原因
     */
    private String reason;
}
