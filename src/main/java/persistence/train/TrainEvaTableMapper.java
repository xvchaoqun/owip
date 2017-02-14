package persistence.train;

import domain.train.TrainEvaTable;
import domain.train.TrainEvaTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TrainEvaTableMapper {
    int countByExample(TrainEvaTableExample example);

    int deleteByExample(TrainEvaTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainEvaTable record);

    int insertSelective(TrainEvaTable record);

    List<TrainEvaTable> selectByExampleWithRowbounds(TrainEvaTableExample example, RowBounds rowBounds);

    List<TrainEvaTable> selectByExample(TrainEvaTableExample example);

    TrainEvaTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainEvaTable record, @Param("example") TrainEvaTableExample example);

    int updateByExample(@Param("record") TrainEvaTable record, @Param("example") TrainEvaTableExample example);

    int updateByPrimaryKeySelective(TrainEvaTable record);

    int updateByPrimaryKey(TrainEvaTable record);
}