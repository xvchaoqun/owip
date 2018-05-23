package persistence.crs;

import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import domain.crs.CrsApplicantViewWithBLOBs;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CrsApplicantViewMapper {
    long countByExample(CrsApplicantViewExample example);

    List<CrsApplicantViewWithBLOBs> selectByExampleWithBLOBsWithRowbounds(CrsApplicantViewExample example, RowBounds rowBounds);

    List<CrsApplicantViewWithBLOBs> selectByExampleWithBLOBs(CrsApplicantViewExample example);

    List<CrsApplicantView> selectByExampleWithRowbounds(CrsApplicantViewExample example, RowBounds rowBounds);

    List<CrsApplicantView> selectByExample(CrsApplicantViewExample example);
}