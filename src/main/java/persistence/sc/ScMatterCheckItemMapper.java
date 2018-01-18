package persistence.sc;

import domain.sc.ScMatterCheckItem;
import domain.sc.ScMatterCheckItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterCheckItemMapper {
    long countByExample(ScMatterCheckItemExample example);

    int deleteByExample(ScMatterCheckItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterCheckItem record);

    int insertSelective(ScMatterCheckItem record);

    List<ScMatterCheckItem> selectByExampleWithRowbounds(ScMatterCheckItemExample example, RowBounds rowBounds);

    List<ScMatterCheckItem> selectByExample(ScMatterCheckItemExample example);

    ScMatterCheckItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterCheckItem record, @Param("example") ScMatterCheckItemExample example);

    int updateByExample(@Param("record") ScMatterCheckItem record, @Param("example") ScMatterCheckItemExample example);

    int updateByPrimaryKeySelective(ScMatterCheckItem record);

    int updateByPrimaryKey(ScMatterCheckItem record);
}