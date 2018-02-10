package persistence.crs;

import domain.crs.CrsApplicantView;
import domain.crs.CrsApplicantViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsApplicantViewMapper {
    long countByExample(CrsApplicantViewExample example);

    List<CrsApplicantView> selectByExampleWithRowbounds(CrsApplicantViewExample example, RowBounds rowBounds);

    List<CrsApplicantView> selectByExample(CrsApplicantViewExample example);
}