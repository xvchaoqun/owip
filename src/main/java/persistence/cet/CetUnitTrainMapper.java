package persistence.cet;

import domain.cet.CetUnitTrain;
import domain.cet.CetUnitTrainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetUnitTrainMapper {
    long countByExample(CetUnitTrainExample example);

    int deleteByExample(CetUnitTrainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetUnitTrain record);

    int insertSelective(CetUnitTrain record);

    List<CetUnitTrain> selectByExampleWithRowbounds(CetUnitTrainExample example, RowBounds rowBounds);

    List<CetUnitTrain> selectByExample(CetUnitTrainExample example);

    CetUnitTrain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetUnitTrain record, @Param("example") CetUnitTrainExample example);

    int updateByExample(@Param("record") CetUnitTrain record, @Param("example") CetUnitTrainExample example);

    int updateByPrimaryKeySelective(CetUnitTrain record);

    int updateByPrimaryKey(CetUnitTrain record);
}