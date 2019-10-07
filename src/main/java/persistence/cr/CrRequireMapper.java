package persistence.cr;

import domain.cr.CrRequire;
import domain.cr.CrRequireExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrRequireMapper {
    long countByExample(CrRequireExample example);

    int deleteByExample(CrRequireExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrRequire record);

    int insertSelective(CrRequire record);

    List<CrRequire> selectByExampleWithRowbounds(CrRequireExample example, RowBounds rowBounds);

    List<CrRequire> selectByExample(CrRequireExample example);

    CrRequire selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrRequire record, @Param("example") CrRequireExample example);

    int updateByExample(@Param("record") CrRequire record, @Param("example") CrRequireExample example);

    int updateByPrimaryKeySelective(CrRequire record);

    int updateByPrimaryKey(CrRequire record);
}