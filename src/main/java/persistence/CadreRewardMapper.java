package persistence;

import domain.CadreReward;
import domain.CadreRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreRewardMapper {
    int countByExample(CadreRewardExample example);

    int deleteByExample(CadreRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreReward record);

    int insertSelective(CadreReward record);

    List<CadreReward> selectByExampleWithRowbounds(CadreRewardExample example, RowBounds rowBounds);

    List<CadreReward> selectByExample(CadreRewardExample example);

    CadreReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreReward record, @Param("example") CadreRewardExample example);

    int updateByExample(@Param("record") CadreReward record, @Param("example") CadreRewardExample example);

    int updateByPrimaryKeySelective(CadreReward record);

    int updateByPrimaryKey(CadreReward record);
}