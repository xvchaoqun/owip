package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterAccess;
import domain.sc.scMatter.ScMatterAccessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterAccessMapper {
    long countByExample(ScMatterAccessExample example);

    int deleteByExample(ScMatterAccessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMatterAccess record);

    int insertSelective(ScMatterAccess record);

    List<ScMatterAccess> selectByExampleWithRowbounds(ScMatterAccessExample example, RowBounds rowBounds);

    List<ScMatterAccess> selectByExample(ScMatterAccessExample example);

    ScMatterAccess selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMatterAccess record, @Param("example") ScMatterAccessExample example);

    int updateByExample(@Param("record") ScMatterAccess record, @Param("example") ScMatterAccessExample example);

    int updateByPrimaryKeySelective(ScMatterAccess record);

    int updateByPrimaryKey(ScMatterAccess record);
}