package cn.chendahai.ray.dao;


import cn.chendahai.ray.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * 作业2：研究@Repository @Component @Service @Controller之间的区别与联系
 * // 面试题，加分 6
 */
public interface UserMapper extends Mapper<User> {

    @Select("SELECT count(*) FROM `bonus_event_log` where DATE_FORMAT(now(),'%Y-%m-%d') = DATE_FORMAT(create_time,'%Y-%m-%d') and user_id = #{userId} and event = 'sign'")
    int checkSign(@Param("userId") Integer userId);
}