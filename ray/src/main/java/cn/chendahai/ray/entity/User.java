package cn.chendahai.ray.entity;

import com.github.binarywang.java.emoji.EmojiConverter;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    /**
     * Id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 微信id
     */
    @Column(name = "wx_id")
    private String wxId;

    /**
     * 微信昵称
     */
    @Column(name = "wx_nickname")
    private String wxNickname;

    /**
     * 角色
     */
    private String roles;

    /**
     * 头像地址
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 积分
     */
    private Integer bonus;

    public void buildUnicodeWxNickname() {
        if (this.wxNickname != null) {
            EmojiConverter emojiConverter = EmojiConverter.getInstance();
            setWxNickname(emojiConverter.toUnicode(this.wxNickname));
        }
    }

    public void buildAliaseWxNickname() {
        if (this.wxNickname != null) {
            EmojiConverter emojiConverter = EmojiConverter.getInstance();
            setWxNickname(emojiConverter.toAlias(this.wxNickname));
        }
    }
}