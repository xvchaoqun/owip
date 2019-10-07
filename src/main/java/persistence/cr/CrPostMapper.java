package persistence.cr;

import domain.cr.CrPost;
import domain.cr.CrPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrPostMapper {
    long countByExample(CrPostExample example);

    int deleteByExample(CrPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrPost record);

    int insertSelective(CrPost record);

    List<CrPost> selectByExampleWithRowbounds(CrPostExample example, RowBounds rowBounds);

    List<CrPost> selectByExample(CrPostExample example);

    CrPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrPost record, @Param("example") CrPostExample example);

    int updateByExample(@Param("record") CrPost record, @Param("example") CrPostExample example);

    int updateByPrimaryKeySelective(CrPost record);

    int updateByPrimaryKey(CrPost record);
}