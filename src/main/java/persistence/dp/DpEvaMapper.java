package persistence.dp;

import domain.dp.DpEva;
import domain.dp.DpEvaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpEvaMapper {
    long countByExample(DpEvaExample example);

    int deleteByExample(DpEvaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpEva record);

    int insertSelective(DpEva record);

    List<DpEva> selectByExampleWithRowbounds(DpEvaExample example, RowBounds rowBounds);

    List<DpEva> selectByExample(DpEvaExample example);

    DpEva selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpEva record, @Param("example") DpEvaExample example);

    int updateByExample(@Param("record") DpEva record, @Param("example") DpEvaExample example);

    int updateByPrimaryKeySelective(DpEva record);

    int updateByPrimaryKey(DpEva record);
}