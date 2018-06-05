package persistence.abroad;

import domain.abroad.AbroadAdditionalPost;
import domain.abroad.AbroadAdditionalPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AbroadAdditionalPostMapper {
    long countByExample(AbroadAdditionalPostExample example);

    int deleteByExample(AbroadAdditionalPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AbroadAdditionalPost record);

    int insertSelective(AbroadAdditionalPost record);

    List<AbroadAdditionalPost> selectByExampleWithRowbounds(AbroadAdditionalPostExample example, RowBounds rowBounds);

    List<AbroadAdditionalPost> selectByExample(AbroadAdditionalPostExample example);

    AbroadAdditionalPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AbroadAdditionalPost record, @Param("example") AbroadAdditionalPostExample example);

    int updateByExample(@Param("record") AbroadAdditionalPost record, @Param("example") AbroadAdditionalPostExample example);

    int updateByPrimaryKeySelective(AbroadAdditionalPost record);

    int updateByPrimaryKey(AbroadAdditionalPost record);
}