package cn.chendahai.center.content.domain.dto.content;/**
 * @author chy
 * @date 2020/12/28 0028 下午 16:21
 * Description：
 */

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *功能描述 
 * @author chy
 * @date 2020/12/28 0028
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class shareRequestDTO implements Serializable {

    /**
     * 标题
     */
    private String title;


    /**
     * 是否原创 0:否 1:是
     */
    private Boolean isOriginal;

    /**
     * 作者
     */
    private String author;

    /**
     * 概要信息
     */
    private String summary;

    /**
     * 价格（需要的积分）
     */
    private Integer price;

    /**
     * 下载地址
     */
    private String downloadUrl;

}
