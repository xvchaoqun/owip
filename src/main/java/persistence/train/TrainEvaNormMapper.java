package persistence.train;

import domain.train.TrainEvaNorm;
import domain.train.TrainEvaNormExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TrainEvaNormMapper {
    int countByExample(TrainEvaNormExample example);

    int deleteByExample(TrainEvaNormExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainEvaNorm record);

    int insertSelective(TrainEvaNorm record);

    List<TrainEvaNorm> selectByExampleWithRowbounds(TrainEvaNormExample example, RowBounds rowBounds);

    List<TrainEvaNorm> selectByExample(TrainEvaNormExample example);

    TrainEvaNorm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainEvaNorm record, @Param("example") TrainEvaNormExample example);

    int updateByExample(@Param("record") TrainEvaNorm record, @Param("example") TrainEvaNormExample example);

    int updateByPrimaryKeySelective(TrainEvaNorm record);

    int updateByPrimaryKey(TrainEvaNorm record);
}