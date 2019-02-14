package persistence.dr;

import domain.dr.DrOfflineView;
import domain.dr.DrOfflineViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface DrOfflineViewMapper {
    long countByExample(DrOfflineViewExample example);

    List<DrOfflineView> selectByExampleWithRowbounds(DrOfflineViewExample example, RowBounds rowBounds);

    List<DrOfflineView> selectByExample(DrOfflineViewExample example);
}