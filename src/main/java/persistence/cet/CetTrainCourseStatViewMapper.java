package persistence.cet;

import domain.cet.CetTrainCourseStatView;
import domain.cet.CetTrainCourseStatViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainCourseStatViewMapper {
    long countByExample(CetTrainCourseStatViewExample example);

    List<CetTrainCourseStatView> selectByExampleWithRowbounds(CetTrainCourseStatViewExample example, RowBounds rowBounds);

    List<CetTrainCourseStatView> selectByExample(CetTrainCourseStatViewExample example);
}