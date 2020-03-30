package persistence.dr;

import domain.dr.DrOnlineResult;
import domain.dr.DrOnlineResultExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DrOnlineResultMapper {
    long countByExample(DrOnlineResultExample example);

    int deleteByExample(DrOnlineResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlineResult record);

    int insertSelective(DrOnlineResult record);

    List<DrOnlineResult> selectByExampleWithRowbounds(DrOnlineResultExample example, RowBounds rowBounds);

    List<DrOnlineResult> selectByExample(DrOnlineResultExample example);

    DrOnlineResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlineResult record, @Param("example") DrOnlineResultExample example);

    int updateByExample(@Param("record") DrOnlineResult record, @Param("example") DrOnlineResultExample example);

    int updateByPrimaryKeySelective(DrOnlineResult record);

    int updateByPrimaryKey(DrOnlineResult record);
}