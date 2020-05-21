package persistence.dr;

import domain.dr.DrOnline;
import domain.dr.DrOnlineExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DrOnlineMapper {
    long countByExample(DrOnlineExample example);

    int deleteByExample(DrOnlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnline record);

    int insertSelective(DrOnline record);

    List<DrOnline> selectByExampleWithRowbounds(DrOnlineExample example, RowBounds rowBounds);

    List<DrOnline> selectByExample(DrOnlineExample example);

    DrOnline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnline record, @Param("example") DrOnlineExample example);

    int updateByExample(@Param("record") DrOnline record, @Param("example") DrOnlineExample example);

    int updateByPrimaryKeySelective(DrOnline record);

    int updateByPrimaryKey(DrOnline record);
}