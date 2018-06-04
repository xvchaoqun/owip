package persistence.crs;

import domain.crs.CrsCandidateView;
import domain.crs.CrsCandidateViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CrsCandidateViewMapper {
    long countByExample(CrsCandidateViewExample example);

    List<CrsCandidateView> selectByExampleWithRowbounds(CrsCandidateViewExample example, RowBounds rowBounds);

    List<CrsCandidateView> selectByExample(CrsCandidateViewExample example);
}