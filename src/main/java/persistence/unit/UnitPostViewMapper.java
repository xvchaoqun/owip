package persistence.unit;

import domain.unit.UnitPostView;
import domain.unit.UnitPostViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface UnitPostViewMapper {
    long countByExample(UnitPostViewExample example);

    List<UnitPostView> selectByExampleWithRowbounds(UnitPostViewExample example, RowBounds rowBounds);

    List<UnitPostView> selectByExample(UnitPostViewExample example);
}