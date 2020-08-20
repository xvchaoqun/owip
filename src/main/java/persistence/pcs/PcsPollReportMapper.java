package persistence.pcs;

import domain.pcs.PcsPollReport;
import domain.pcs.PcsPollReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPollReportMapper {
    long countByExample(PcsPollReportExample example);

    int deleteByExample(PcsPollReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPollReport record);

    int insertSelective(PcsPollReport record);

    List<PcsPollReport> selectByExampleWithRowbounds(PcsPollReportExample example, RowBounds rowBounds);

    List<PcsPollReport> selectByExample(PcsPollReportExample example);

    PcsPollReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPollReport record, @Param("example") PcsPollReportExample example);

    int updateByExample(@Param("record") PcsPollReport record, @Param("example") PcsPollReportExample example);

    int updateByPrimaryKeySelective(PcsPollReport record);

    int updateByPrimaryKey(PcsPollReport record);
}