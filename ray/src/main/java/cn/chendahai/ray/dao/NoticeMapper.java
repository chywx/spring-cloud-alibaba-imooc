package cn.chendahai.ray.dao;


import cn.chendahai.ray.entity.Notice;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface NoticeMapper extends Mapper<Notice> {

    @Select("select * from notice where show_flag = 1 order by create_time desc limit 1")
    Notice newest();

}