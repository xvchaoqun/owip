package persistence.dr;

import domain.dr.DrOnlinePost;
import domain.dr.DrOnlinePostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOnlinePostMapper {
    long countByExample(DrOnlinePostExample example);

    int deleteByExample(DrOnlinePostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlinePost record);

    int insertSelective(DrOnlinePost record);

    List<DrOnlinePost> selectByExampleWithRowbounds(DrOnlinePostExample example, RowBounds rowBounds);

    List<DrOnlinePost> selectByExample(DrOnlinePostExample example);

    DrOnlinePost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlinePost record, @Param("example") DrOnlinePostExample example);

    int updateByExample(@Param("record") DrOnlinePost record, @Param("example") DrOnlinePostExample example);

    int updateByPrimaryKeySelective(DrOnlinePost record);

    int updateByPrimaryKey(DrOnlinePost record);
}