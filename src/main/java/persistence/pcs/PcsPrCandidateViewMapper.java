package persistence.pcs;

import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsPrCandidateViewMapper {
    long countByExample(PcsPrCandidateViewExample example);

    List<PcsPrCandidateView> selectByExampleWithRowbounds(PcsPrCandidateViewExample example, RowBounds rowBounds);

    List<PcsPrCandidateView> selectByExample(PcsPrCandidateViewExample example);
}