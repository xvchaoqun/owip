package persistence.sc.ScShift;

import domain.sc.scShift.ScShift;
import domain.sc.scShift.ScShiftExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScShiftMapper {
    long countByExample(ScShiftExample example);

    int deleteByExample(ScShiftExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScShift record);

    int insertSelective(ScShift record);

    List<ScShift> selectByExampleWithRowbounds(ScShiftExample example, RowBounds rowBounds);

    List<ScShift> selectByExample(ScShiftExample example);

    ScShift selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScShift record, @Param("example") ScShiftExample example);

    int updateByExample(@Param("record") ScShift record, @Param("example") ScShiftExample example);

    int updateByPrimaryKeySelective(ScShift record);

    int updateByPrimaryKey(ScShift record);
}