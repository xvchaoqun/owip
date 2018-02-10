package persistence.pcs;

import domain.pcs.PcsCandidateView;
import domain.pcs.PcsCandidateViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PcsCandidateViewMapper {
    long countByExample(PcsCandidateViewExample example);

    List<PcsCandidateView> selectByExampleWithRowbounds(PcsCandidateViewExample example, RowBounds rowBounds);

    List<PcsCandidateView> selectByExample(PcsCandidateViewExample example);
}