package persistence.cet;

import domain.cet.CetColumnView;
import domain.cet.CetColumnViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetColumnViewMapper {
    long countByExample(CetColumnViewExample example);

    List<CetColumnView> selectByExampleWithRowbounds(CetColumnViewExample example, RowBounds rowBounds);

    List<CetColumnView> selectByExample(CetColumnViewExample example);
}