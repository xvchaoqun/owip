package persistence.base;

import domain.base.ContentTpl;
import domain.base.ContentTplExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ContentTplMapper {
    long countByExample(ContentTplExample example);

    int deleteByExample(ContentTplExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContentTpl record);

    int insertSelective(ContentTpl record);

    List<ContentTpl> selectByExampleWithRowbounds(ContentTplExample example, RowBounds rowBounds);

    List<ContentTpl> selectByExample(ContentTplExample example);

    ContentTpl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContentTpl record, @Param("example") ContentTplExample example);

    int updateByExample(@Param("record") ContentTpl record, @Param("example") ContentTplExample example);

    int updateByPrimaryKeySelective(ContentTpl record);

    int updateByPrimaryKey(ContentTpl record);
}