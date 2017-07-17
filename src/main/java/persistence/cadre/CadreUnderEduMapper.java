package persistence.cadre;

import domain.cadre.CadreUnderEdu;
import domain.cadre.CadreUnderEduExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreUnderEduMapper {
    int countByExample(CadreUnderEduExample example);

    int deleteByExample(CadreUnderEduExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreUnderEdu record);

    int insertSelective(CadreUnderEdu record);

    List<CadreUnderEdu> selectByExampleWithRowbounds(CadreUnderEduExample example, RowBounds rowBounds);

    List<CadreUnderEdu> selectByExample(CadreUnderEduExample example);

    CadreUnderEdu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreUnderEdu record, @Param("example") CadreUnderEduExample example);

    int updateByExample(@Param("record") CadreUnderEdu record, @Param("example") CadreUnderEduExample example);

    int updateByPrimaryKeySelective(CadreUnderEdu record);

    int updateByPrimaryKey(CadreUnderEdu record);
}