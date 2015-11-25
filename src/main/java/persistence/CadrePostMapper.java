package persistence;

import domain.CadrePost;
import domain.CadrePostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadrePostMapper {
    int countByExample(CadrePostExample example);

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