package persistence.cadre;

import domain.cadre.CadrePost;
import domain.cadre.CadrePostExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadrePostMapper {
    long countByExample(CadrePostExample example);

    int deleteByExample(CadrePostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePost record);

    int insertSelective(CadrePost record);

    List<CadrePost> selectByExampleWithRowbounds(CadrePostExample example, RowBounds rowBounds);

    List<CadrePost> selectByExample(CadrePostExample example);

    CadrePost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePost record, @Param("example") CadrePostExample example);

    int updateByExample(@Param("record") CadrePost record, @Param("example") CadrePostExample example);

    int updateByPrimaryKeySelective(CadrePost record);

    int updateByPrimaryKey(CadrePost record);
}