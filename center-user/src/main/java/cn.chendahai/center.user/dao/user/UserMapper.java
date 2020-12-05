package cn.chendahai.center.user.dao.user;


import cn.chendahai.center.user.domain.entity.user.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * 作业2：研究@Repository @Component @Service @Controller之间的区别与联系
 * // 面试题，加分 6
 */
public interface UserMapper extends Mapper<User> {
}