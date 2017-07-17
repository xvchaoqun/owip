package persistence.cis;

import domain.cis.CisObjInspector;
import domain.cis.CisObjInspectorExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CisObjInspectorMapper {
    int countByExample(CisObjInspectorExample example);

    int deleteByExample(CisObjInspectorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CisObjInspector record);

    int insertSelective(CisObjInspector record);

    List<CisObjInspector> selectByExampleWithRowbounds(CisObjInspectorExample example, RowBounds rowBounds);

    List<CisObjInspector> selectByExample(CisObjInspectorExample example);

    CisObjInspector selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CisObjInspector record, @Param("example") CisObjInspectorExample example);

    int updateByExample(@Param("record") CisObjInspector record, @Param("example") CisObjInspectorExample example);

    int updateByPrimaryKeySelective(CisObjInspector record);

    int updateByPrimaryKey(CisObjInspector record);
}