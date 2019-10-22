package persistence.sp;

import domain.sp.SpRetire;
import domain.sp.SpRetireExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SpRetireMapper {
    long countByExample(SpRetireExample example);

    int deleteByExample(SpRetireExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpRetire record);

    int insertSelective(SpRetire record);

    List<SpRetire> selectByExampleWithRowbounds(SpRetireExample example, RowBounds rowBounds);

    List<SpRetire> selectByExample(SpRetireExample example);

    SpRetire selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpRetire record, @Param("example") SpRetireExample example);

    int updateByExample(@Param("record") SpRetire record, @Param("example") SpRetireExample example);

    int updateByPrimaryKeySelective(SpRetire record);

    int updateByPrimaryKey(SpRetire record);
}