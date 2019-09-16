package persistence.base;

import domain.base.LayerType;
import domain.base.LayerTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LayerTypeMapper {
    long countByExample(LayerTypeExample example);

    int deleteByExample(LayerTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LayerType record);

    int insertSelective(LayerType record);

    List<LayerType> selectByExampleWithRowbounds(LayerTypeExample example, RowBounds rowBounds);

    List<LayerType> selectByExample(LayerTypeExample example);

    LayerType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LayerType record, @Param("example") LayerTypeExample example);

    int updateByExample(@Param("record") LayerType record, @Param("example") LayerTypeExample example);

    int updateByPrimaryKeySelective(LayerType record);

    int updateByPrimaryKey(LayerType record);
}