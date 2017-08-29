package persistence.pcs;

import domain.pcs.PcsPrCandidateView;
import domain.pcs.PcsPrCandidateViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PcsPrCandidateViewMapper {
    long countByExample(PcsPrCandidateViewExample example);

    List<PcsPrCandidateView> selectByExampleWithRowbounds(PcsPrCandidateViewExample example, RowBounds rowBounds);

    List<PcsPrCandidateView> selectByExample(PcsPrCandidateViewExample example);
}