package persistence.dp;

import domain.dp.DpNpm;
import domain.dp.DpNpmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpNpmMapper {
    long countByExample(DpNpmExample example);

    int deleteByExample(DpNpmExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpNpm record);

    int insertSelective(DpNpm record);

    List<DpNpm> selectByExampleWithRowbounds(DpNpmExample example, RowBounds rowBounds);

    List<DpNpm> selectByExample(DpNpmExample example);

    DpNpm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpNpm record, @Param("example") DpNpmExample example);

    int updateByExample(@Param("record") DpNpm record, @Param("example") DpNpmExample example);

    int updateByPrimaryKeySelective(DpNpm record);

    int updateByPrimaryKey(DpNpm record);
}