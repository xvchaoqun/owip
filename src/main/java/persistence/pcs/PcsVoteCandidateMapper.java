package persistence.pcs;

import domain.pcs.PcsVoteCandidate;
import domain.pcs.PcsVoteCandidateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsVoteCandidateMapper {
    long countByExample(PcsVoteCandidateExample example);

    int deleteByExample(PcsVoteCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsVoteCandidate record);

    int insertSelective(PcsVoteCandidate record);

    List<PcsVoteCandidate> selectByExampleWithRowbounds(PcsVoteCandidateExample example, RowBounds rowBounds);

    List<PcsVoteCandidate> selectByExample(PcsVoteCandidateExample example);

    PcsVoteCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsVoteCandidate record, @Param("example") PcsVoteCandidateExample example);

    int updateByExample(@Param("record") PcsVoteCandidate record, @Param("example") PcsVoteCandidateExample example);

    int updateByPrimaryKeySelective(PcsVoteCandidate record);

    int updateByPrimaryKey(PcsVoteCandidate record);
}