package persistence.cet;

import domain.cet.CetTrainEvaRank;
import domain.cet.CetTrainEvaRankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainEvaRankMapper {
    long countByExample(CetTrainEvaRankExample example);

    int deleteByExample(CetTrainEvaRankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainEvaRank record);

    int insertSelective(CetTrainEvaRank record);

    List<CetTrainEvaRank> selectByExampleWithRowbounds(CetTrainEvaRankExample example, RowBounds rowBounds);

    List<CetTrainEvaRank> selectByExample(CetTrainEvaRankExample example);

    CetTrainEvaRank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainEvaRank record, @Param("example") CetTrainEvaRankExample example);

    int updateByExample(@Param("record") CetTrainEvaRank record, @Param("example") CetTrainEvaRankExample example);

    int updateByPrimaryKeySelective(CetTrainEvaRank record);

    int updateByPrimaryKey(CetTrainEvaRank record);
}