package persistence.cet;

import domain.cet.CetTrainInspector;
import domain.cet.CetTrainInspectorExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CetTrainInspectorMapper {
    long countByExample(CetTrainInspectorExample example);

    int deleteByExample(CetTrainInspectorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainInspector record);

    int insertSelective(CetTrainInspector record);

    List<CetTrainInspector> selectByExampleWithRowbounds(CetTrainInspectorExample example, RowBounds rowBounds);

    List<CetTrainInspector> selectByExample(CetTrainInspectorExample example);

    CetTrainInspector selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainInspector record, @Param("example") CetTrainInspectorExample example);

    int updateByExample(@Param("record") CetTrainInspector record, @Param("example") CetTrainInspectorExample example);

    int updateByPrimaryKeySelective(CetTrainInspector record);

    int updateByPrimaryKey(CetTrainInspector record);
}