package persistence.cadreTemp;

import domain.cadreTemp.CadreTempView;
import domain.cadreTemp.CadreTempViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadreTempViewMapper {
    int countByExample(CadreTempViewExample example);

    List<CadreTempView> selectByExampleWithRowbounds(CadreTempViewExample example, RowBounds rowBounds);

    List<CadreTempView> selectByExample(CadreTempViewExample example);
}