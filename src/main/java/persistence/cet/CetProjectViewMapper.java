package persistence.cet;

import domain.cet.CetProjectView;
import domain.cet.CetProjectViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectViewMapper {
    long countByExample(CetProjectViewExample example);

    List<CetProjectView> selectByExampleWithRowbounds(CetProjectViewExample example, RowBounds rowBounds);

    List<CetProjectView> selectByExample(CetProjectViewExample example);
}