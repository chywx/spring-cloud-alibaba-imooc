package cn.chendahai.center.content.dao.content;


import cn.chendahai.center.content.domain.entity.content.Share;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ShareMapper extends Mapper<Share> {

    List<Share> selectByParam(@Param("title") String title);

    List<Share> selectByIds(@Param("ids") List<Integer> ids);
}