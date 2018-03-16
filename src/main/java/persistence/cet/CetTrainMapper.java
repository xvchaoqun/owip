package persistence.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainMapper {
    long countByExample(CetTrainExample example);

    int deleteByExample(CetTrainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrain record);

    int insertSelective(CetTrain record);

    List<CetTrain> selectByExampleWithBLOBsWithRowbounds(CetTrainExample example, RowBounds rowBounds);

    List<CetTrain> selectByExampleWithBLOBs(CetTrainExample example);

    List<CetTrain> selectByExampleWithRowbounds(CetTrainExample example, RowBounds rowBounds);

    List<CetTrain> selectByExample(CetTrainExample example);

    CetTrain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrain record, @Param("example") CetTrainExample example);

    int updateByExampleWithBLOBs(@Param("record") CetTrain record, @Param("example") CetTrainExample example);

    int updateByExample(@Param("record") CetTrain record, @Param("example") CetTrainExample example);

    int updateByPrimaryKeySelective(CetTrain record);

    int updateByPrimaryKeyWithBLOBs(CetTrain record);

    int updateByPrimaryKey(CetTrain record);
}