package persistence.cet;

import domain.cet.CetTrainCourseView;
import domain.cet.CetTrainCourseViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainCourseViewMapper {
    long countByExample(CetTrainCourseViewExample example);

    List<CetTrainCourseView> selectByExampleWithRowbounds(CetTrainCourseViewExample example, RowBounds rowBounds);

    List<CetTrainCourseView> selectByExample(CetTrainCourseViewExample example);
}