package persistence.pcs;

import domain.pcs.PcsAdminReport;
import domain.pcs.PcsAdminReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsAdminReportMapper {
    long countByExample(PcsAdminReportExample example);

    int deleteByExample(PcsAdminReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsAdminReport record);

    int insertSelective(PcsAdminReport record);

    List<PcsAdminReport> selectByExampleWithRowbounds(PcsAdminReportExample example, RowBounds rowBounds);

    List<PcsAdminReport> selectByExample(PcsAdminReportExample example);

    PcsAdminReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsAdminReport record, @Param("example") PcsAdminReportExample example);

    int updateByExample(@Param("record") PcsAdminReport record, @Param("example") PcsAdminReportExample example);

    int updateByPrimaryKeySelective(PcsAdminReport record);

    int updateByPrimaryKey(PcsAdminReport record);
}