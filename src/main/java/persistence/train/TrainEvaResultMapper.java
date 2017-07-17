package persistence.train;

import domain.train.TrainEvaResult;
import domain.train.TrainEvaResultExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TrainEvaResultMapper {
    int countByExample(TrainEvaResultExample example);

    int deleteByExample(TrainEvaResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainEvaResult record);

    int insertSelective(TrainEvaResult record);

    List<TrainEvaResult> selectByExampleWithRowbounds(TrainEvaResultExample example, RowBounds rowBounds);

    List<TrainEvaResult> selectByExample(TrainEvaResultExample example);

    TrainEvaResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainEvaResult record, @Param("example") TrainEvaResultExample example);

    int updateByExample(@Param("record") TrainEvaResult record, @Param("example") TrainEvaResultExample example);

    int updateByPrimaryKeySelective(TrainEvaResult record);

    int updateByPrimaryKey(TrainEvaResult record);
}