package persistence;

import domain.ApplyPost;
import domain.ApplyPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplyPostMapper {
    int countByExample(ApplyPostExample example);

    int deleteByExample(ApplyPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyPost record);

    int insertSelective(ApplyPost record);

    List<ApplyPost> selectByExampleWithRowbounds(ApplyPostExample example, RowBounds rowBounds);

    List<ApplyPost> selectByExample(ApplyPostExample example);

    ApplyPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyPost record, @Param("example") ApplyPostExample example);

    int updateByExample(@Param("record") ApplyPost record, @Param("example") ApplyPostExample example);

    int updateByPrimaryKeySelective(ApplyPost record);

    int updateByPrimaryKey(ApplyPost record);
}