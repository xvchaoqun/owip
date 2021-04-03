package persistence.pcs;

import domain.pcs.PcsPrCandidate;
import domain.pcs.PcsPrCandidateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPrCandidateMapper {
    long countByExample(PcsPrCandidateExample example);

    int deleteByExample(PcsPrCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsPrCandidate record);

    int insertSelective(PcsPrCandidate record);

    List<PcsPrCandidate> selectByExampleWithRowbounds(PcsPrCandidateExample example, RowBounds rowBounds);

    List<PcsPrCandidate> selectByExample(PcsPrCandidateExample example);

    PcsPrCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsPrCandidate record, @Param("example") PcsPrCandidateExample example);

    int updateByExample(@Param("record") PcsPrCandidate record, @Param("example") PcsPrCandidateExample example);

    int updateByPrimaryKeySelective(PcsPrCandidate record);

    int updateByPrimaryKey(PcsPrCandidate record);
}