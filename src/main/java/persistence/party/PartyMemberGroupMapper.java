package persistence.party;

import domain.party.PartyMemberGroup;
import domain.party.PartyMemberGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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

    //<!--<if test="dispatchUnitId != null" >-->
    //  <!--<if test="fid != null" >-->
    int updateByPrimaryKeySelective(PartyMemberGroup record);

    int updateByPrimaryKey(PartyMemberGroup record);
}