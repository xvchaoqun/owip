package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeOtherVote;
import domain.sc.scCommittee.ScCommitteeOtherVoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeOtherVoteMapper {
    long countByExample(ScCommitteeOtherVoteExample example);

    int deleteByExample(ScCommitteeOtherVoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScCommitteeOtherVote record);

    int insertSelective(ScCommitteeOtherVote record);

    List<ScCommitteeOtherVote> selectByExampleWithRowbounds(ScCommitteeOtherVoteExample example, RowBounds rowBounds);

    List<ScCommitteeOtherVote> selectByExample(ScCommitteeOtherVoteExample example);

    ScCommitteeOtherVote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScCommitteeOtherVote record, @Param("example") ScCommitteeOtherVoteExample example);

    int updateByExample(@Param("record") ScCommitteeOtherVote record, @Param("example") ScCommitteeOtherVoteExample example);

    int updateByPrimaryKeySelective(ScCommitteeOtherVote record);

    int updateByPrimaryKey(ScCommitteeOtherVote record);
}