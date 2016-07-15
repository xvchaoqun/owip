package persistence.party;

import domain.party.BranchMember;
import domain.party.BranchMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BranchMemberMapper {
    int countByExample(BranchMemberExample example);

    int deleteByExample(BranchMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BranchMember record);

    int insertSelective(BranchMember record);

    List<BranchMember> selectByExampleWithRowbounds(BranchMemberExample example, RowBounds rowBounds);

    List<BranchMember> selectByExample(BranchMemberExample example);

    BranchMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BranchMember record, @Param("example") BranchMemberExample example);

    int updateByExample(@Param("record") BranchMember record, @Param("example") BranchMemberExample example);

    int updateByPrimaryKeySelective(BranchMember record);

    int updateByPrimaryKey(BranchMember record);
}