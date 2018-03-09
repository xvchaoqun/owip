package persistence.cet;

import domain.cet.CetColumnCourseView;
import domain.cet.CetColumnCourseViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetColumnCourseViewMapper {
    long countByExample(CetColumnCourseViewExample example);

    List<CetColumnCourseView> selectByExampleWithRowbounds(CetColumnCourseViewExample example, RowBounds rowBounds);

    List<CetColumnCourseView> selectByExample(CetColumnCourseViewExample example);
}