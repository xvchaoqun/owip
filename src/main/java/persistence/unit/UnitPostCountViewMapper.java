package persistence.unit;

import domain.unit.UnitPostCountView;
import domain.unit.UnitPostCountViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface UnitPostCountViewMapper {
    long countByExample(UnitPostCountViewExample example);

    List<UnitPostCountView> selectByExampleWithRowbounds(UnitPostCountViewExample example, RowBounds rowBounds);

    List<UnitPostCountView> selectByExample(UnitPostCountViewExample example);
}