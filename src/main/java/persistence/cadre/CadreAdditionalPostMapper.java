package persistence.cadre;

import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdditionalPostExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreAdditionalPostMapper {
    int countByExample(CadreAdditionalPostExample example);

    int deleteByExample(CadreAdditionalPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreAdditionalPost record);

    int insertSelective(CadreAdditionalPost record);

    List<CadreAdditionalPost> selectByExampleWithRowbounds(CadreAdditionalPostExample example, RowBounds rowBounds);

    List<CadreAdditionalPost> selectByExample(CadreAdditionalPostExample example);

    CadreAdditionalPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreAdditionalPost record, @Param("example") CadreAdditionalPostExample example);

    int updateByExample(@Param("record") CadreAdditionalPost record, @Param("example") CadreAdditionalPostExample example);

    int updateByPrimaryKeySelective(CadreAdditionalPost record);

    int updateByPrimaryKey(CadreAdditionalPost record);
}