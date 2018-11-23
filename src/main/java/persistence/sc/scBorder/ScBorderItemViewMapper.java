package persistence.sc.scBorder;

import domain.sc.scBorder.ScBorderItemView;
import domain.sc.scBorder.ScBorderItemViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScBorderItemViewMapper {
    long countByExample(ScBorderItemViewExample example);

    List<ScBorderItemView> selectByExampleWithRowbounds(ScBorderItemViewExample example, RowBounds rowBounds);

    List<ScBorderItemView> selectByExample(ScBorderItemViewExample example);
}