package persistence.cet;

import domain.cet.CetTrainEvaNorm;
import domain.cet.CetTrainEvaNormExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainEvaNormMapper {
    long countByExample(CetTrainEvaNormExample example);

    int deleteByExample(CetTrainEvaNormExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainEvaNorm record);

    int insertSelective(CetTrainEvaNorm record);

    List<CetTrainEvaNorm> selectByExampleWithRowbounds(CetTrainEvaNormExample example, RowBounds rowBounds);

    List<CetTrainEvaNorm> selectByExample(CetTrainEvaNormExample example);

    CetTrainEvaNorm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainEvaNorm record, @Param("example") CetTrainEvaNormExample example);

    int updateByExample(@Param("record") CetTrainEvaNorm record, @Param("example") CetTrainEvaNormExample example);

    int updateByPrimaryKeySelective(CetTrainEvaNorm record);

    int updateByPrimaryKey(CetTrainEvaNorm record);
}