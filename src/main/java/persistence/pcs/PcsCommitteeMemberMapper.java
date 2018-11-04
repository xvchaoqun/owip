package persistence.pcs;

import domain.pcs.PcsCommitteeMember;
import domain.pcs.PcsCommitteeMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsCommitteeMemberMapper {
    long countByExample(PcsCommitteeMemberExample example);

    int deleteByExample(PcsCommitteeMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsCommitteeMember record);

    int insertSelective(PcsCommitteeMember record);

    List<PcsCommitteeMember> selectByExampleWithRowbounds(PcsCommitteeMemberExample example, RowBounds rowBounds);

    List<PcsCommitteeMember> selectByExample(PcsCommitteeMemberExample example);

    PcsCommitteeMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsCommitteeMember record, @Param("example") PcsCommitteeMemberExample example);

    int updateByExample(@Param("record") PcsCommitteeMember record, @Param("example") PcsCommitteeMemberExample example);

    int updateByPrimaryKeySelective(PcsCommitteeMember record);

    int updateByPrimaryKey(PcsCommitteeMember record);
}