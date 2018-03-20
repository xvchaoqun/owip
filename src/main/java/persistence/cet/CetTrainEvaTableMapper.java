package persistence.cet;

import domain.cet.CetTrainEvaTable;
import domain.cet.CetTrainEvaTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainEvaTableMapper {
    long countByExample(CetTrainEvaTableExample example);

    int deleteByExample(CetTrainEvaTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainEvaTable record);

    int insertSelective(CetTrainEvaTable record);

    List<CetTrainEvaTable> selectByExampleWithRowbounds(CetTrainEvaTableExample example, RowBounds rowBounds);

    List<CetTrainEvaTable> selectByExample(CetTrainEvaTableExample example);

    CetTrainEvaTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainEvaTable record, @Param("example") CetTrainEvaTableExample example);

    int updateByExample(@Param("record") CetTrainEvaTable record, @Param("example") CetTrainEvaTableExample example);

    int updateByPrimaryKeySelective(CetTrainEvaTable record);

    int updateByPrimaryKey(CetTrainEvaTable record);
}