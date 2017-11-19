package persistence.pcs;

import domain.pcs.PcsVoteCandidateView;
import domain.pcs.PcsVoteCandidateViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PcsVoteCandidateViewMapper {
    long countByExample(PcsVoteCandidateViewExample example);

    List<PcsVoteCandidateView> selectByExampleWithRowbounds(PcsVoteCandidateViewExample example, RowBounds rowBounds);

    List<PcsVoteCandidateView> selectByExample(PcsVoteCandidateViewExample example);
}