package cn.chendahai.center.content.dao.content;


import cn.chendahai.center.content.domain.entity.content.Notice;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface NoticeMapper extends Mapper<Notice> {

    @Select("select * from notice where show_flag = 1 order by create_time desc limit 1")
    Notice newest();

}