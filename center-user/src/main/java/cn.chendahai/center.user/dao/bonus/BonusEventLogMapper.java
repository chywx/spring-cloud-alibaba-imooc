package cn.chendahai.center.user.dao.bonus;

import cn.chendahai.center.user.domain.entity.bonus.BonusEventLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface BonusEventLogMapper extends Mapper<BonusEventLog> {

    @Select("select * from bonus_event_log where user_id = #{userId} order by id desc")
    List<BonusEventLog> selectByUserId(@Param("userId") Integer userId);
}