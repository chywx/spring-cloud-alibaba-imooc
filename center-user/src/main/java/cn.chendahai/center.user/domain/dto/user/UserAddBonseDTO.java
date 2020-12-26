package cn.chendahai.center.user.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddBonseDTO {
    private Integer userId;
    /**
     * 积分
     */
    private Integer bonus;

    private String description;

    private String event;
}
