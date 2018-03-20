package persistence.cet;

import domain.cet.CetTrainEvaResult;
import domain.cet.CetTrainEvaResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainEvaResultMapper {
    long countByExample(CetTrainEvaResultExample example);

    int deleteByExample(CetTrainEvaResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainEvaResult record);

    int insertSelective(CetTrainEvaResult record);

    List<CetTrainEvaResult> selectByExampleWithRowbounds(CetTrainEvaResultExample example, RowBounds rowBounds);

    List<CetTrainEvaResult> selectByExample(CetTrainEvaResultExample example);

    CetTrainEvaResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainEvaResult record, @Param("example") CetTrainEvaResultExample example);

    int updateByExample(@Param("record") CetTrainEvaResult record, @Param("example") CetTrainEvaResultExample example);

    int updateByPrimaryKeySelective(CetTrainEvaResult record);

    int updateByPrimaryKey(CetTrainEvaResult record);
}