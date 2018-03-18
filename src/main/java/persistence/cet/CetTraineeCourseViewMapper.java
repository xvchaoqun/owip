package persistence.cet;

import domain.cet.CetTraineeCourseView;
import domain.cet.CetTraineeCourseViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeCourseViewMapper {
    long countByExample(CetTraineeCourseViewExample example);

    List<CetTraineeCourseView> selectByExampleWithRowbounds(CetTraineeCourseViewExample example, RowBounds rowBounds);

    List<CetTraineeCourseView> selectByExample(CetTraineeCourseViewExample example);
}