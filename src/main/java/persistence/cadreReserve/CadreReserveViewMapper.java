package persistence.cadreReserve;

import domain.cadreReserve.CadreReserveView;
import domain.cadreReserve.CadreReserveViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadreReserveViewMapper {
    long countByExample(CadreReserveViewExample example);

    List<CadreReserveView> selectByExampleWithRowbounds(CadreReserveViewExample example, RowBounds rowBounds);

    List<CadreReserveView> selectByExample(CadreReserveViewExample example);
}