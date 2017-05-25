package persistence.cpc;

import domain.cpc.CpcAllocationView;
import domain.cpc.CpcAllocationViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CpcAllocationViewMapper {
    long countByExample(CpcAllocationViewExample example);

    List<CpcAllocationView> selectByExampleWithRowbounds(CpcAllocationViewExample example, RowBounds rowBounds);

    List<CpcAllocationView> selectByExample(CpcAllocationViewExample example);
}