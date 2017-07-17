package persistence.cadre;

import domain.cadre.CadrePostPro;
import domain.cadre.CadrePostProExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadrePostProMapper {
    int countByExample(CadrePostProExample example);

    int deleteByExample(CadrePostProExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadrePostPro record);

    int insertSelective(CadrePostPro record);

    List<CadrePostPro> selectByExampleWithRowbounds(CadrePostProExample example, RowBounds rowBounds);

    List<CadrePostPro> selectByExample(CadrePostProExample example);

    CadrePostPro selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadrePostPro record, @Param("example") CadrePostProExample example);

    int updateByExample(@Param("record") CadrePostPro record, @Param("example") CadrePostProExample example);

    int updateByPrimaryKeySelective(CadrePostPro record);

    int updateByPrimaryKey(CadrePostPro record);
}