package persistence.sc;

import domain.sc.ScMatter;
import domain.sc.ScMatterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterMapper {
    long countByExample(ScMatterExample example);

    int deleteByExample(ScMatterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatter record);

    int insertSelective(ScMatter record);

    List<ScMatter> selectByExampleWithRowbounds(ScMatterExample example, RowBounds rowBounds);

    List<ScMatter> selectByExample(ScMatterExample example);

    ScMatter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatter record, @Param("example") ScMatterExample example);

    int updateByExample(@Param("record") ScMatter record, @Param("example") ScMatterExample example);

    int updateByPrimaryKeySelective(ScMatter record);

    int updateByPrimaryKey(ScMatter record);
}