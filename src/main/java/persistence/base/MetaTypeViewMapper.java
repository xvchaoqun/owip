package persistence.base;

import domain.base.MetaTypeView;
import domain.base.MetaTypeViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MetaTypeViewMapper {
    long countByExample(MetaTypeViewExample example);

    int deleteByExample(MetaTypeViewExample example);

    int insert(MetaTypeView record);

    int insertSelective(MetaTypeView record);

    List<MetaTypeView> selectByExampleWithRowbounds(MetaTypeViewExample example, RowBounds rowBounds);

    List<MetaTypeView> selectByExample(MetaTypeViewExample example);

    int updateByExampleSelective(@Param("record") MetaTypeView record, @Param("example") MetaTypeViewExample example);

    int updateByExample(@Param("record") MetaTypeView record, @Param("example") MetaTypeViewExample example);
}