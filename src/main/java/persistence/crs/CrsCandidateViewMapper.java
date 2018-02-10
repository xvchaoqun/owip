package persistence.crs;

import domain.crs.CrsCandidateView;
import domain.crs.CrsCandidateViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsCandidateViewMapper {
    long countByExample(CrsCandidateViewExample example);

    List<CrsCandidateView> selectByExampleWithRowbounds(CrsCandidateViewExample example, RowBounds rowBounds);

    List<CrsCandidateView> selectByExample(CrsCandidateViewExample example);
}