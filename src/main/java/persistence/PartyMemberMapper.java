package persistence;

import domain.PartyMember;
import domain.PartyMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyMemberMapper {
    int countByExample(PartyMemberExample example);

    int deleteByExample(PartyMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyMember record);

    int insertSelective(PartyMember record);

    List<PartyMember> selectByExampleWithRowbounds(PartyMemberExample example, RowBounds rowBounds);

    List<PartyMember> selectByExample(PartyMemberExample example);

    PartyMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyMember record, @Param("example") PartyMemberExample example);

    int updateByExample(@Param("record") PartyMember record, @Param("example") PartyMemberExample example);

    int updateByPrimaryKeySelective(PartyMember record);

    int updateByPrimaryKey(PartyMember record);
}