package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeVote;
import domain.sc.scCommittee.ScCommitteeVoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeVoteMapper {
    long countByExample(ScCommitteeVoteExample example);

    int deleteByExample(ScCommitteeVoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScCommitteeVote record);

    int insertSelective(ScCommitteeVote record);

    List<ScCommitteeVote> selectByExampleWithRowbounds(ScCommitteeVoteExample example, RowBounds rowBounds);

    List<ScCommitteeVote> selectByExample(ScCommitteeVoteExample example);

    ScCommitteeVote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScCommitteeVote record, @Param("example") ScCommitteeVoteExample example);

    int updateByExample(@Param("record") ScCommitteeVote record, @Param("example") ScCommitteeVoteExample example);

    int updateByPrimaryKeySelective(ScCommitteeVote record);

    int updateByPrimaryKey(ScCommitteeVote record);
}