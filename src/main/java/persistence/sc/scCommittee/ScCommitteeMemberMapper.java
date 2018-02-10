package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeMember;
import domain.sc.scCommittee.ScCommitteeMemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScCommitteeMemberMapper {
    long countByExample(ScCommitteeMemberExample example);

    int deleteByExample(ScCommitteeMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScCommitteeMember record);

    int insertSelective(ScCommitteeMember record);

    List<ScCommitteeMember> selectByExampleWithRowbounds(ScCommitteeMemberExample example, RowBounds rowBounds);

    List<ScCommitteeMember> selectByExample(ScCommitteeMemberExample example);

    ScCommitteeMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScCommitteeMember record, @Param("example") ScCommitteeMemberExample example);

    int updateByExample(@Param("record") ScCommitteeMember record, @Param("example") ScCommitteeMemberExample example);

    int updateByPrimaryKeySelective(ScCommitteeMember record);

    int updateByPrimaryKey(ScCommitteeMember record);
}