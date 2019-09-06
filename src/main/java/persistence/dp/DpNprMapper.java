package persistence.dp;

import domain.dp.DpNpr;
import domain.dp.DpNprExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpNprMapper {
    long countByExample(DpNprExample example);

    int deleteByExample(DpNprExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpNpr record);

    int insertSelective(DpNpr record);

    List<DpNpr> selectByExampleWithRowbounds(DpNprExample example, RowBounds rowBounds);

    List<DpNpr> selectByExample(DpNprExample example);

    DpNpr selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpNpr record, @Param("example") DpNprExample example);

    int updateByExample(@Param("record") DpNpr record, @Param("example") DpNprExample example);

    int updateByPrimaryKeySelective(DpNpr record);

    int updateByPrimaryKey(DpNpr record);
}