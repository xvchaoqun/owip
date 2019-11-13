package persistence.op;

import domain.op.OpReport;
import domain.op.OpReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OpReportMapper {
    long countByExample(OpReportExample example);

    int deleteByExample(OpReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpReport record);

    int insertSelective(OpReport record);

    List<OpReport> selectByExampleWithRowbounds(OpReportExample example, RowBounds rowBounds);

    List<OpReport> selectByExample(OpReportExample example);

    OpReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpReport record, @Param("example") OpReportExample example);

    int updateByExample(@Param("record") OpReport record, @Param("example") OpReportExample example);

    int updateByPrimaryKeySelective(OpReport record);

    int updateByPrimaryKey(OpReport record);
}