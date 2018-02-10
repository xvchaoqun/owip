package persistence.crs;

import domain.crs.CrsApplicantAdjustView;
import domain.crs.CrsApplicantAdjustViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsApplicantAdjustViewMapper {
    long countByExample(CrsApplicantAdjustViewExample example);

    List<CrsApplicantAdjustView> selectByExampleWithRowbounds(CrsApplicantAdjustViewExample example, RowBounds rowBounds);

    List<CrsApplicantAdjustView> selectByExample(CrsApplicantAdjustViewExample example);
}