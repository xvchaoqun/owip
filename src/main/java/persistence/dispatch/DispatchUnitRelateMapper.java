package persistence.dispatch;

import domain.dispatch.DispatchUnitRelate;
import domain.dispatch.DispatchUnitRelateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchUnitRelateMapper {
    int countByExample(DispatchUnitRelateExample example);

    int deleteByExample(DispatchUnitRelateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchUnitRelate record);

    int insertSelective(DispatchUnitRelate record);

    List<DispatchUnitRelate> selectByExampleWithRowbounds(DispatchUnitRelateExample example, RowBounds rowBounds);

    List<DispatchUnitRelate> selectByExample(DispatchUnitRelateExample example);

    DispatchUnitRelate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchUnitRelate record, @Param("example") DispatchUnitRelateExample example);

    int updateByExample(@Param("record") DispatchUnitRelate record, @Param("example") DispatchUnitRelateExample example);

    int updateByPrimaryKeySelective(DispatchUnitRelate record);

    int updateByPrimaryKey(DispatchUnitRelate record);
}