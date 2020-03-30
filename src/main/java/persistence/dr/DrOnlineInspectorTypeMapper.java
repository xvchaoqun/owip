package persistence.dr;

import domain.dr.DrOnlineInspectorType;
import domain.dr.DrOnlineInspectorTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOnlineInspectorTypeMapper {
    long countByExample(DrOnlineInspectorTypeExample example);

    int deleteByExample(DrOnlineInspectorTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlineInspectorType record);

    int insertSelective(DrOnlineInspectorType record);

    List<DrOnlineInspectorType> selectByExampleWithRowbounds(DrOnlineInspectorTypeExample example, RowBounds rowBounds);

    List<DrOnlineInspectorType> selectByExample(DrOnlineInspectorTypeExample example);

    DrOnlineInspectorType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlineInspectorType record, @Param("example") DrOnlineInspectorTypeExample example);

    int updateByExample(@Param("record") DrOnlineInspectorType record, @Param("example") DrOnlineInspectorTypeExample example);

    int updateByPrimaryKeySelective(DrOnlineInspectorType record);

    int updateByPrimaryKey(DrOnlineInspectorType record);
}