package persistence.sc.scAd;

import domain.sc.scAd.ScAdArchiveView;
import domain.sc.scAd.ScAdArchiveViewExample;
import domain.sc.scAd.ScAdArchiveViewWithBLOBs;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScAdArchiveViewMapper {
    long countByExample(ScAdArchiveViewExample example);

    List<ScAdArchiveViewWithBLOBs> selectByExampleWithBLOBsWithRowbounds(ScAdArchiveViewExample example, RowBounds rowBounds);

    List<ScAdArchiveViewWithBLOBs> selectByExampleWithBLOBs(ScAdArchiveViewExample example);

    List<ScAdArchiveView> selectByExampleWithRowbounds(ScAdArchiveViewExample example, RowBounds rowBounds);

    List<ScAdArchiveView> selectByExample(ScAdArchiveViewExample example);
}