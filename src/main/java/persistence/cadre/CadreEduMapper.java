package persistence.cadre;

import domain.cadre.CadreEdu;
import domain.cadre.CadreEduExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreEduMapper {
    int countByExample(CadreEduExample example);

    int deleteByExample(CadreEduExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreEdu record);

    int insertSelective(CadreEdu record);

    List<CadreEdu> selectByExampleWithRowbounds(CadreEduExample example, RowBounds rowBounds);

    List<CadreEdu> selectByExample(CadreEduExample example);

    CadreEdu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreEdu record, @Param("example") CadreEduExample example);

    int updateByExample(@Param("record") CadreEdu record, @Param("example") CadreEduExample example);

    int updateByPrimaryKeySelective(CadreEdu record);

    int updateByPrimaryKey(CadreEdu record);
}