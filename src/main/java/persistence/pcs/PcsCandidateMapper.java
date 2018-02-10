package persistence.pcs;

import domain.pcs.PcsCandidate;
import domain.pcs.PcsCandidateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsCandidateMapper {
    long countByExample(PcsCandidateExample example);

    int deleteByExample(PcsCandidateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsCandidate record);

    int insertSelective(PcsCandidate record);

    List<PcsCandidate> selectByExampleWithRowbounds(PcsCandidateExample example, RowBounds rowBounds);

    List<PcsCandidate> selectByExample(PcsCandidateExample example);

    PcsCandidate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsCandidate record, @Param("example") PcsCandidateExample example);

    int updateByExample(@Param("record") PcsCandidate record, @Param("example") PcsCandidateExample example);

    int updateByPrimaryKeySelective(PcsCandidate record);

    int updateByPrimaryKey(PcsCandidate record);
}