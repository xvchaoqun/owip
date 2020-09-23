package persistence.party;

import domain.party.BranchMemberGroup;
import domain.party.BranchMemberGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BranchMemberGroupMapper {
    long countByExample(BranchMemberGroupExample example);

    int deleteByExample(BranchMemberGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BranchMemberGroup record);

    int insertSelective(BranchMemberGroup record);

    List<BranchMemberGroup> selectByExampleWithRowbounds(BranchMemberGroupExample example, RowBounds rowBounds);

    List<BranchMemberGroup> selectByExample(BranchMemberGroupExample example);

    BranchMemberGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BranchMemberGroup record, @Param("example") BranchMemberGroupExample example);

    int updateByExample(@Param("record") BranchMemberGroup record, @Param("example") BranchMemberGroupExample example);

    int updateByPrimaryKeySelective(BranchMemberGroup record);

    int updateByPrimaryKey(BranchMemberGroup record);
}