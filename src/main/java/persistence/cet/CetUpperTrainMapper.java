package persistence.cet;

import domain.cet.CetUpperTrain;
import domain.cet.CetUpperTrainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetUpperTrainMapper {
    long countByExample(CetUpperTrainExample example);

    int deleteByExample(CetUpperTrainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetUpperTrain record);

    int insertSelective(CetUpperTrain record);

    List<CetUpperTrain> selectByExampleWithRowbounds(CetUpperTrainExample example, RowBounds rowBounds);

    List<CetUpperTrain> selectByExample(CetUpperTrainExample example);

    CetUpperTrain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetUpperTrain record, @Param("example") CetUpperTrainExample example);

    int updateByExample(@Param("record") CetUpperTrain record, @Param("example") CetUpperTrainExample example);

    int updateByPrimaryKeySelective(CetUpperTrain record);

    int updateByPrimaryKey(CetUpperTrain record);
}