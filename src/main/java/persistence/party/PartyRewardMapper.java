package persistence.party;

import domain.party.PartyReward;
import domain.party.PartyRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyRewardMapper {
    long countByExample(PartyRewardExample example);

    int deleteByExample(PartyRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyReward record);

    int insertSelective(PartyReward record);

    List<PartyReward> selectByExampleWithRowbounds(PartyRewardExample example, RowBounds rowBounds);

    List<PartyReward> selectByExample(PartyRewardExample example);

    PartyReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyReward record, @Param("example") PartyRewardExample example);

    int updateByExample(@Param("record") PartyReward record, @Param("example") PartyRewardExample example);

    int updateByPrimaryKeySelective(PartyReward record);

    int updateByPrimaryKey(PartyReward record);
}