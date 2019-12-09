package persistence.dp;

import domain.dp.DpPartyMember;
import domain.dp.DpPartyMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DpPartyMemberMapper {
    long countByExample(DpPartyMemberExample example);

    int deleteByExample(DpPartyMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DpPartyMember record);

    int insertSelective(DpPartyMember record);

    List<DpPartyMember> selectByExampleWithRowbounds(DpPartyMemberExample example, RowBounds rowBounds);

    List<DpPartyMember> selectByExample(DpPartyMemberExample example);

    DpPartyMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DpPartyMember record, @Param("example") DpPartyMemberExample example);

    int updateByExample(@Param("record") DpPartyMember record, @Param("example") DpPartyMemberExample example);

    int updateByPrimaryKeySelective(DpPartyMember record);

    int updateByPrimaryKey(DpPartyMember record);
}