package persistence.sc;

import domain.sc.ScMatterItem;
import domain.sc.ScMatterItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterItemMapper {
    long countByExample(ScMatterItemExample example);

    int deleteByExample(ScMatterItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterItem record);

    int insertSelective(ScMatterItem record);

    List<ScMatterItem> selectByExampleWithRowbounds(ScMatterItemExample example, RowBounds rowBounds);

    List<ScMatterItem> selectByExample(ScMatterItemExample example);

    ScMatterItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterItem record, @Param("example") ScMatterItemExample example);

    int updateByExample(@Param("record") ScMatterItem record, @Param("example") ScMatterItemExample example);

    int updateByPrimaryKeySelective(ScMatterItem record);

    int updateByPrimaryKey(ScMatterItem record);
}