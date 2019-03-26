package persistence.unit;

import domain.unit.UnitFunction;
import domain.unit.UnitFunctionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitFunctionMapper {
    long countByExample(UnitFunctionExample example);

    int deleteByExample(UnitFunctionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitFunction record);

    int insertSelective(UnitFunction record);

    List<UnitFunction> selectByExampleWithRowbounds(UnitFunctionExample example, RowBounds rowBounds);

    List<UnitFunction> selectByExample(UnitFunctionExample example);

    UnitFunction selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitFunction record, @Param("example") UnitFunctionExample example);

    int updateByExample(@Param("record") UnitFunction record, @Param("example") UnitFunctionExample example);

    int updateByPrimaryKeySelective(UnitFunction record);

    int updateByPrimaryKey(UnitFunction record);
}