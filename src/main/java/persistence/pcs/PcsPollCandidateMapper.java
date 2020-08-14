package persistence.pcs;

import domain.pcs.PcsPollCandidate;
import domain.pcs.PcsPollCandidateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPollCandidateMapper {
    long countByExample(PcsPollCandidateExample example);

    int deleteByExample(PcsPollCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPollCandidate record);

    int insertSelective(PcsPollCandidate record);

    List<PcsPollCandidate> selectByExampleWithRowbounds(PcsPollCandidateExample example, RowBounds rowBounds);

    List<PcsPollCandidate> selectByExample(PcsPollCandidateExample example);

    PcsPollCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPollCandidate record, @Param("example") PcsPollCandidateExample example);

    int updateByExample(@Param("record") PcsPollCandidate record, @Param("example") PcsPollCandidateExample example);

    int updateByPrimaryKeySelective(PcsPollCandidate record);

    int updateByPrimaryKey(PcsPollCandidate record);
}