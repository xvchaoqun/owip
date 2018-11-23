package persistence.sc.scBorder;

import domain.sc.scBorder.ScBorderView;
import domain.sc.scBorder.ScBorderViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScBorderViewMapper {
    long countByExample(ScBorderViewExample example);

    List<ScBorderView> selectByExampleWithRowbounds(ScBorderViewExample example, RowBounds rowBounds);

    List<ScBorderView> selectByExample(ScBorderViewExample example);
}