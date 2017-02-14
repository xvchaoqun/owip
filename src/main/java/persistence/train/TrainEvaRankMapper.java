package persistence.train;

import domain.train.TrainEvaRank;
import domain.train.TrainEvaRankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TrainEvaRankMapper {
    int countByExample(TrainEvaRankExample example);

    int deleteByExample(TrainEvaRankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainEvaRank record);

    int insertSelective(TrainEvaRank record);

    List<TrainEvaRank> selectByExampleWithRowbounds(TrainEvaRankExample example, RowBounds rowBounds);

    List<TrainEvaRank> selectByExample(TrainEvaRankExample example);

    TrainEvaRank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainEvaRank record, @Param("example") TrainEvaRankExample example);

    int updateByExample(@Param("record") TrainEvaRank record, @Param("example") TrainEvaRankExample example);

    int updateByPrimaryKeySelective(TrainEvaRank record);

    int updateByPrimaryKey(TrainEvaRank record);
}