package cn.chendahai.ray.entity;/**
 * @author chy
 * @date 2020/12/26 0026 下午 15:32
 * Description：
 */

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/12/26 0026
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Notice {

    private Integer id;
    private String content;
    private Boolean showFlag;
    private Date createTime;
}
