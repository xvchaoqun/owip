package persistence.crs;

import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantViewMapper {
    long countByExample(CrsApplicantViewExample example);

    List<CrsApplicantView> selectByExampleWithRowbounds(CrsApplicantViewExample example, RowBounds rowBounds);

    List<CrsApplicantView> selectByExample(CrsApplicantViewExample example);
}