package persistence.cadre;

import domain.cadre.CadreTrain;
import domain.cadre.CadreTrainExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreTrainMapper {
    int countByExample(CadreTrainExample example);

    int deleteByExample(CadreTrainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreTrain record);

    int insertSelective(CadreTrain record);

    List<CadreTrain> selectByExampleWithRowbounds(CadreTrainExample example, RowBounds rowBounds);

    List<CadreTrain> selectByExample(CadreTrainExample example);

    CadreTrain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreTrain record, @Param("example") CadreTrainExample example);

    int updateByExample(@Param("record") CadreTrain record, @Param("example") CadreTrainExample example);

    int updateByPrimaryKeySelective(CadreTrain record);

    int updateByPrimaryKey(CadreTrain record);
}