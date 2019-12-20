package persistence.dp;

import domain.dp.DpReward;
import domain.dp.DpRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpRewardMapper {
    long countByExample(DpRewardExample example);

    int deleteByExample(DpRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpReward record);

    int insertSelective(DpReward record);

    List<DpReward> selectByExampleWithRowbounds(DpRewardExample example, RowBounds rowBounds);

    List<DpReward> selectByExample(DpRewardExample example);

    DpReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpReward record, @Param("example") DpRewardExample example);

    int updateByExample(@Param("record") DpReward record, @Param("example") DpRewardExample example);

    int updateByPrimaryKeySelective(DpReward record);

    int updateByPrimaryKey(DpReward record);
}