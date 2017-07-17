package persistence.cadre;

import domain.cadre.CadreReport;
import domain.cadre.CadreReportExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreReportMapper {
    int countByExample(CadreReportExample example);

    int deleteByExample(CadreReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreReport record);

    int insertSelective(CadreReport record);

    List<CadreReport> selectByExampleWithRowbounds(CadreReportExample example, RowBounds rowBounds);

    List<CadreReport> selectByExample(CadreReportExample example);

    CadreReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreReport record, @Param("example") CadreReportExample example);

    int updateByExample(@Param("record") CadreReport record, @Param("example") CadreReportExample example);

    int updateByPrimaryKeySelective(CadreReport record);

    int updateByPrimaryKey(CadreReport record);
}