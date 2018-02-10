package persistence.pcs;

import domain.pcs.PcsVoteMember;
import domain.pcs.PcsVoteMemberExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsVoteMemberMapper {
    long countByExample(PcsVoteMemberExample example);

    int deleteByExample(PcsVoteMemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsVoteMember record);

    int insertSelective(PcsVoteMember record);

    List<PcsVoteMember> selectByExampleWithRowbounds(PcsVoteMemberExample example, RowBounds rowBounds);

    List<PcsVoteMember> selectByExample(PcsVoteMemberExample example);

    PcsVoteMember selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsVoteMember record, @Param("example") PcsVoteMemberExample example);

    int updateByExample(@Param("record") PcsVoteMember record, @Param("example") PcsVoteMemberExample example);

    int updateByPrimaryKeySelective(PcsVoteMember record);

    int updateByPrimaryKey(PcsVoteMember record);
}