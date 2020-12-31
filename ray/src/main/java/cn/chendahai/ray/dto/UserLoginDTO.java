package cn.chendahai.ray.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserLoginDTO {
    /**
     * code
     */
    private String code;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 微信昵称
     */
    private String wxNickname;
}
