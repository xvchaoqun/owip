package persistence.dr;

import domain.dr.DrOnlineInspector;
import domain.dr.DrOnlineInspectorExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DrOnlineInspectorMapper {
    long countByExample(DrOnlineInspectorExample example);

    int deleteByExample(DrOnlineInspectorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlineInspector record);

    int insertSelective(DrOnlineInspector record);

    List<DrOnlineInspector> selectByExampleWithRowbounds(DrOnlineInspectorExample example, RowBounds rowBounds);

    List<DrOnlineInspector> selectByExample(DrOnlineInspectorExample example);

    DrOnlineInspector selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlineInspector record, @Param("example") DrOnlineInspectorExample example);

    int updateByExample(@Param("record") DrOnlineInspector record, @Param("example") DrOnlineInspectorExample example);

    int updateByPrimaryKeySelective(DrOnlineInspector record);

    int updateByPrimaryKey(DrOnlineInspector record);
}