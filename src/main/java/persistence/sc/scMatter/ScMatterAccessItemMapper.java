package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterAccessItem;
import domain.sc.scMatter.ScMatterAccessItemExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScMatterAccessItemMapper {
    long countByExample(ScMatterAccessItemExample example);

    int deleteByExample(ScMatterAccessItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterAccessItem record);

    int insertSelective(ScMatterAccessItem record);

    List<ScMatterAccessItem> selectByExampleWithRowbounds(ScMatterAccessItemExample example, RowBounds rowBounds);

    List<ScMatterAccessItem> selectByExample(ScMatterAccessItemExample example);

    ScMatterAccessItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterAccessItem record, @Param("example") ScMatterAccessItemExample example);

    int updateByExample(@Param("record") ScMatterAccessItem record, @Param("example") ScMatterAccessItemExample example);

    int updateByPrimaryKeySelective(ScMatterAccessItem record);

    int updateByPrimaryKey(ScMatterAccessItem record);
}