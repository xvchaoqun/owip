package persistence.sc.scMotion;

import domain.sc.scMotion.ScMotionView;
import domain.sc.scMotion.ScMotionViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScMotionViewMapper {
    long countByExample(ScMotionViewExample example);

    List<ScMotionView> selectByExampleWithRowbounds(ScMotionViewExample example, RowBounds rowBounds);

    List<ScMotionView> selectByExample(ScMotionViewExample example);
}