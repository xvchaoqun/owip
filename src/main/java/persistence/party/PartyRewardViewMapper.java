package persistence.party;

import domain.party.PartyRewardView;
import domain.party.PartyRewardViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyRewardViewMapper {
    long countByExample(PartyRewardViewExample example);

    int deleteByExample(PartyRewardViewExample example);

    int insert(PartyRewardView record);

    int insertSelective(PartyRewardView record);

    List<PartyRewardView> selectByExampleWithRowbounds(PartyRewardViewExample example, RowBounds rowBounds);

    List<PartyRewardView> selectByExample(PartyRewardViewExample example);

    int updateByExampleSelective(@Param("record") PartyRewardView record, @Param("example") PartyRewardViewExample example);

    int updateByExample(@Param("record") PartyRewardView record, @Param("example") PartyRewardViewExample example);
}