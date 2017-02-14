package persistence.train;

import domain.train.TrainInspector;
import domain.train.TrainInspectorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TrainInspectorMapper {
    int countByExample(TrainInspectorExample example);

    int deleteByExample(TrainInspectorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainInspector record);

    int insertSelective(TrainInspector record);

    List<TrainInspector> selectByExampleWithRowbounds(TrainInspectorExample example, RowBounds rowBounds);

    List<TrainInspector> selectByExample(TrainInspectorExample example);

    TrainInspector selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainInspector record, @Param("example") TrainInspectorExample example);

    int updateByExample(@Param("record") TrainInspector record, @Param("example") TrainInspectorExample example);

    int updateByPrimaryKeySelective(TrainInspector record);

    int updateByPrimaryKey(TrainInspector record);
}