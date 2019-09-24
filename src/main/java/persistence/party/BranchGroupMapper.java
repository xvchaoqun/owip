package persistence.party;

import domain.party.BranchGroup;
import domain.party.BranchGroupExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface BranchGroupMapper {
    long countByExample(BranchGroupExample example);

    int deleteByExample(BranchGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BranchGroup record);

    int insertSelective(BranchGroup record);

    List<BranchGroup> selectByExampleWithRowbounds(BranchGroupExample example, RowBounds rowBounds);

    List<BranchGroup> selectByExample(BranchGroupExample example);

    BranchGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BranchGroup record, @Param("example") BranchGroupExample example);

    int updateByExample(@Param("record") BranchGroup record, @Param("example") BranchGroupExample example);

    int updateByPrimaryKeySelective(BranchGroup record);

    int updateByPrimaryKey(BranchGroup record);
}