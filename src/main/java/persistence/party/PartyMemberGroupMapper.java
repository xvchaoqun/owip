package persistence.party;

import domain.party.PartyMemberGroup;
import domain.party.PartyMemberGroupExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyMemberGroupMapper {
    int countByExample(PartyMemberGroupExample example);

    int deleteByExample(PartyMemberGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyMemberGroup record);

    int insertSelective(PartyMemberGroup record);

    List<PartyMemberGroup> selectByExampleWithRowbounds(PartyMemberGroupExample example, RowBounds rowBounds);

    List<PartyMemberGroup> selectByExample(PartyMemberGroupExample example);

    PartyMemberGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyMemberGroup record, @Param("example") PartyMemberGroupExample example);

    int updateByExample(@Param("record") PartyMemberGroup record, @Param("example") PartyMemberGroupExample example);

    int updateByPrimaryKeySelective(PartyMemberGroup record);

    int updateByPrimaryKey(PartyMemberGroup record);
}