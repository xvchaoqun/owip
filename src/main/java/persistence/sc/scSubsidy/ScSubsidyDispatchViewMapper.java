package persistence.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyDispatchView;
import domain.sc.scSubsidy.ScSubsidyDispatchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScSubsidyDispatchViewMapper {
    long countByExample(ScSubsidyDispatchViewExample example);

    List<ScSubsidyDispatchView> selectByExampleWithRowbounds(ScSubsidyDispatchViewExample example, RowBounds rowBounds);

    List<ScSubsidyDispatchView> selectByExample(ScSubsidyDispatchViewExample example);
}