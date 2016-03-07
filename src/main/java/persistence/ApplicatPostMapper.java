package persistence;

import domain.ApplicatPost;
import domain.ApplicatPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplicatPostMapper {
    int countByExample(ApplicatPostExample example);

    int deleteByExample(ApplicatPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplicatPost record);

    int insertSelective(ApplicatPost record);

    List<ApplicatPost> selectByExampleWithRowbounds(ApplicatPostExample example, RowBounds rowBounds);

    List<ApplicatPost> selectByExample(ApplicatPostExample example);

    ApplicatPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplicatPost record, @Param("example") ApplicatPostExample example);

    int updateByExample(@Param("record") ApplicatPost record, @Param("example") ApplicatPostExample example);

    int updateByPrimaryKeySelective(ApplicatPost record);

    int updateByPrimaryKey(ApplicatPost record);
}