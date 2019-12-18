package persistence.cadre;

import domain.cadre.CadrePositionReport;
import domain.cadre.CadrePositionReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadrePositionReportMapper {
    long countByExample(CadrePositionReportExample example);

    int deleteByExample(CadrePositionReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePositionReport record);

    int insertSelective(CadrePositionReport record);

    List<CadrePositionReport> selectByExampleWithRowbounds(CadrePositionReportExample example, RowBounds rowBounds);

    List<CadrePositionReport> selectByExample(CadrePositionReportExample example);

    CadrePositionReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePositionReport record, @Param("example") CadrePositionReportExample example);

    int updateByExample(@Param("record") CadrePositionReport record, @Param("example") CadrePositionReportExample example);

    int updateByPrimaryKeySelective(CadrePositionReport record);

    int updateByPrimaryKey(CadrePositionReport record);
}