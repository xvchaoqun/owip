package persistence.cis;

import domain.cis.CisInspector;
import domain.cis.CisInspectorExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CisInspectorMapper {
    int countByExample(CisInspectorExample example);

    int deleteByExample(CisInspectorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CisInspector record);

    int insertSelective(CisInspector record);

    List<CisInspector> selectByExampleWithRowbounds(CisInspectorExample example, RowBounds rowBounds);

    List<CisInspector> selectByExample(CisInspectorExample example);

    CisInspector selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CisInspector record, @Param("example") CisInspectorExample example);

    int updateByExample(@Param("record") CisInspector record, @Param("example") CisInspectorExample example);

    int updateByPrimaryKeySelective(CisInspector record);

    int updateByPrimaryKey(CisInspector record);
}