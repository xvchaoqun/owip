package persistence.base;

import domain.base.MetaType;
import domain.base.MetaTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetaTypeMapper {
    int countByExample(MetaTypeExample example);

    int deleteByExample(MetaTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MetaType record);

    int insertSelective(MetaType record);

    List<MetaType> selectByExampleWithRowbounds(MetaTypeExample example, RowBounds rowBounds);

    List<MetaType> selectByExample(MetaTypeExample example);

    MetaType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MetaType record, @Param("example") MetaTypeExample example);

    int updateByExample(@Param("record") MetaType record, @Param("example") MetaTypeExample example);

    int updateByPrimaryKeySelective(MetaType record);

    int updateByPrimaryKey(MetaType record);
}