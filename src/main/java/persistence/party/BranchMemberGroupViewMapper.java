package persistence.party;

import domain.party.BranchMemberGroupView;
import domain.party.BranchMemberGroupViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BranchMemberGroupViewMapper {
    int countByExample(BranchMemberGroupViewExample example);

    int deleteByExample(BranchMemberGroupViewExample example);

    int insert(BranchMemberGroupView record);

    int insertSelective(BranchMemberGroupView record);

    List<BranchMemberGroupView> selectByExampleWithRowbounds(BranchMemberGroupViewExample example, RowBounds rowBounds);

    List<BranchMemberGroupView> selectByExample(BranchMemberGroupViewExample example);

    int updateByExampleSelective(@Param("record") BranchMemberGroupView record, @Param("example") BranchMemberGroupViewExample example);

    int updateByExample(@Param("record") BranchMemberGroupView record, @Param("example") BranchMemberGroupViewExample example);
}