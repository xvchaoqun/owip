package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterCheck;
import domain.sc.scMatter.ScMatterCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterCheckMapper {
    long countByExample(ScMatterCheckExample example);

    int deleteByExample(ScMatterCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterCheck record);

    int insertSelective(ScMatterCheck record);

    List<ScMatterCheck> selectByExampleWithRowbounds(ScMatterCheckExample example, RowBounds rowBounds);

    List<ScMatterCheck> selectByExample(ScMatterCheckExample example);

    ScMatterCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterCheck record, @Param("example") ScMatterCheckExample example);

    int updateByExample(@Param("record") ScMatterCheck record, @Param("example") ScMatterCheckExample example);

    int updateByPrimaryKeySelective(ScMatterCheck record);

    int updateByPrimaryKey(ScMatterCheck record);
}