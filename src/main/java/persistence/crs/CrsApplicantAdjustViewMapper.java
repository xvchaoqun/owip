package persistence.crs;

import domain.crs.CrsApplicantAdjustView;
import domain.crs.CrsApplicantAdjustViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantAdjustViewMapper {
    long countByExample(CrsApplicantAdjustViewExample example);

    List<CrsApplicantAdjustView> selectByExampleWithRowbounds(CrsApplicantAdjustViewExample example, RowBounds rowBounds);

    List<CrsApplicantAdjustView> selectByExample(CrsApplicantAdjustViewExample example);
}