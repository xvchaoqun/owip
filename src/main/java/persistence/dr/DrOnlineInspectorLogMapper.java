package persistence.dr;

import domain.dr.DrOnlineInspectorLog;
import domain.dr.DrOnlineInspectorLogExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DrOnlineInspectorLogMapper {
    long countByExample(DrOnlineInspectorLogExample example);

    int deleteByExample(DrOnlineInspectorLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlineInspectorLog record);

    int insertSelective(DrOnlineInspectorLog record);

    List<DrOnlineInspectorLog> selectByExampleWithRowbounds(DrOnlineInspectorLogExample example, RowBounds rowBounds);

    List<DrOnlineInspectorLog> selectByExample(DrOnlineInspectorLogExample example);

    DrOnlineInspectorLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlineInspectorLog record, @Param("example") DrOnlineInspectorLogExample example);

    int updateByExample(@Param("record") DrOnlineInspectorLog record, @Param("example") DrOnlineInspectorLogExample example);

    int updateByPrimaryKeySelective(DrOnlineInspectorLog record);

    int updateByPrimaryKey(DrOnlineInspectorLog record);
}