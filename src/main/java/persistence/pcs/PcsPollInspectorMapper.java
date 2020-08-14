package persistence.pcs;

import domain.pcs.PcsPollInspector;
import domain.pcs.PcsPollInspectorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPollInspectorMapper {
    long countByExample(PcsPollInspectorExample example);

    int deleteByExample(PcsPollInspectorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPollInspector record);

    int insertSelective(PcsPollInspector record);

    List<PcsPollInspector> selectByExampleWithRowbounds(PcsPollInspectorExample example, RowBounds rowBounds);

    List<PcsPollInspector> selectByExample(PcsPollInspectorExample example);

    PcsPollInspector selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPollInspector record, @Param("example") PcsPollInspectorExample example);

    int updateByExample(@Param("record") PcsPollInspector record, @Param("example") PcsPollInspectorExample example);

    int updateByPrimaryKeySelective(PcsPollInspector record);

    int updateByPrimaryKey(PcsPollInspector record);
}