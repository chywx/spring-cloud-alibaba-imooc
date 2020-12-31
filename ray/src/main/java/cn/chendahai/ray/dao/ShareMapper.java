package cn.chendahai.ray.dao;


import cn.chendahai.ray.entity.Share;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ShareMapper extends Mapper<Share> {

    List<Share> selectByParam(@Param("title") String title);

    List<Share> selectByIds(@Param("ids") List<Integer> ids);
}