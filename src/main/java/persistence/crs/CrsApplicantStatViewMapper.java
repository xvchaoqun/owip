package persistence.crs;

import domain.crs.CrsApplicantStatView;
import domain.crs.CrsApplicantStatViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantStatViewMapper {
    long countByExample(CrsApplicantStatViewExample example);

    List<CrsApplicantStatView> selectByExampleWithRowbounds(CrsApplicantStatViewExample example, RowBounds rowBounds);

    List<CrsApplicantStatView> selectByExample(CrsApplicantStatViewExample example);
}