package persistence.dispatch;

import domain.dispatch.DispatchCadreRelate;
import domain.dispatch.DispatchCadreRelateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchCadreRelateMapper {
    int countByExample(DispatchCadreRelateExample example);

    int deleteByExample(DispatchCadreRelateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchCadreRelate record);

    int insertSelective(DispatchCadreRelate record);

    List<DispatchCadreRelate> selectByExampleWithRowbounds(DispatchCadreRelateExample example, RowBounds rowBounds);

    List<DispatchCadreRelate> selectByExample(DispatchCadreRelateExample example);

    DispatchCadreRelate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchCadreRelate record, @Param("example") DispatchCadreRelateExample example);

    int updateByExample(@Param("record") DispatchCadreRelate record, @Param("example") DispatchCadreRelateExample example);

    int updateByPrimaryKeySelective(DispatchCadreRelate record);

    int updateByPrimaryKey(DispatchCadreRelate record);
}