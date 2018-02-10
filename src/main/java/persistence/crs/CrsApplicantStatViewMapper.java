package persistence.crs;

import domain.crs.CrsApplicantStatView;
import domain.crs.CrsApplicantStatViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsApplicantStatViewMapper {
    long countByExample(CrsApplicantStatViewExample example);

    List<CrsApplicantStatView> selectByExampleWithRowbounds(CrsApplicantStatViewExample example, RowBounds rowBounds);

    List<CrsApplicantStatView> selectByExample(CrsApplicantStatViewExample example);
}