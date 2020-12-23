package cn.chendahai.center.content.dao.content;


import cn.chendahai.center.content.domain.entity.content.Share;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface ShareMapper extends Mapper<Share> {

    @Select("select * from share")
    List<Share> selectByParam(@Param("title") String title);
}