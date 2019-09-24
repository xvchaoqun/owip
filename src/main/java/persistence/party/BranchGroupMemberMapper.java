package persistence.party;

import domain.party.BranchGroupMember;
import domain.party.BranchGroupMemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface BranchGroupMemberMapper {
    long countByExample(BranchGroupMemberExample example);

    int deleteByExample(BranchGroupMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BranchGroupMember record);

    int insertSelective(BranchGroupMember record);

    List<BranchGroupMember> selectByExampleWithRowbounds(BranchGroupMemberExample example, RowBounds rowBounds);

    List<BranchGroupMember> selectByExample(BranchGroupMemberExample example);

    BranchGroupMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BranchGroupMember record, @Param("example") BranchGroupMemberExample example);

    int updateByExample(@Param("record") BranchGroupMember record, @Param("example") BranchGroupMemberExample example);

    int updateByPrimaryKeySelective(BranchGroupMember record);

    int updateByPrimaryKey(BranchGroupMember record);
}