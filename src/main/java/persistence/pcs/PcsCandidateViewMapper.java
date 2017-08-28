package persistence.pcs;

import domain.pcs.PcsCandidateView;
import domain.pcs.PcsCandidateViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PcsCandidateViewMapper {
    long countByExample(PcsCandidateViewExample example);

    List<PcsCandidateView> selectByExampleWithRowbounds(PcsCandidateViewExample example, RowBounds rowBounds);

    List<PcsCandidateView> selectByExample(PcsCandidateViewExample example);
}