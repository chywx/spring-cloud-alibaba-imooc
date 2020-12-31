package cn.chendahai.ray.dto;

import cn.chendahai.ray.enums.AuditStatusEnum;
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
