package persistence.base;

import domain.base.MetaClass;
import domain.base.MetaClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetaClassMapper {
    long countByExample(MetaClassExample example);

    int deleteByExample(MetaClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MetaClass record);

    int insertSelective(MetaClass record);

    List<MetaClass> selectByExampleWithRowbounds(MetaClassExample example, RowBounds rowBounds);

    List<MetaClass> selectByExample(MetaClassExample example);

    MetaClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MetaClass record, @Param("example") MetaClassExample example);

    int updateByExample(@Param("record") MetaClass record, @Param("example") MetaClassExample example);

    int updateByPrimaryKeySelective(MetaClass record);

    int updateByPrimaryKey(MetaClass record);
}