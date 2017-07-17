package persistence.dispatch;

import domain.dispatch.DispatchType;
import domain.dispatch.DispatchTypeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchTypeMapper {
    int countByExample(DispatchTypeExample example);

    int deleteByExample(DispatchTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchType record);

    int insertSelective(DispatchType record);

    List<DispatchType> selectByExampleWithRowbounds(DispatchTypeExample example, RowBounds rowBounds);

    List<DispatchType> selectByExample(DispatchTypeExample example);

    DispatchType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchType record, @Param("example") DispatchTypeExample example);

    int updateByExample(@Param("record") DispatchType record, @Param("example") DispatchTypeExample example);

    int updateByPrimaryKeySelective(DispatchType record);

    int updateByPrimaryKey(DispatchType record);
}