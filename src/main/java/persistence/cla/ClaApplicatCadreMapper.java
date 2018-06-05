package persistence.cla;

import domain.cla.ClaApplicatCadre;
import domain.cla.ClaApplicatCadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApplicatCadreMapper {
    long countByExample(ClaApplicatCadreExample example);

    int deleteByExample(ClaApplicatCadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApplicatCadre record);

    int insertSelective(ClaApplicatCadre record);

    List<ClaApplicatCadre> selectByExampleWithRowbounds(ClaApplicatCadreExample example, RowBounds rowBounds);

    List<ClaApplicatCadre> selectByExample(ClaApplicatCadreExample example);

    ClaApplicatCadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApplicatCadre record, @Param("example") ClaApplicatCadreExample example);

    int updateByExample(@Param("record") ClaApplicatCadre record, @Param("example") ClaApplicatCadreExample example);

    int updateByPrimaryKeySelective(ClaApplicatCadre record);

    int updateByPrimaryKey(ClaApplicatCadre record);
}