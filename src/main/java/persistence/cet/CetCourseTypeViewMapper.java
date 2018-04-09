package persistence.cet;

import domain.cet.CetCourseTypeView;
import domain.cet.CetCourseTypeViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetCourseTypeViewMapper {
    long countByExample(CetCourseTypeViewExample example);

    List<CetCourseTypeView> selectByExampleWithRowbounds(CetCourseTypeViewExample example, RowBounds rowBounds);

    List<CetCourseTypeView> selectByExample(CetCourseTypeViewExample example);
}