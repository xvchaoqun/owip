package persistence.cla;

import domain.cla.ClaAdditionalPostView;
import domain.cla.ClaAdditionalPostViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ClaAdditionalPostViewMapper {
    long countByExample(ClaAdditionalPostViewExample example);

    List<ClaAdditionalPostView> selectByExampleWithRowbounds(ClaAdditionalPostViewExample example, RowBounds rowBounds);

    List<ClaAdditionalPostView> selectByExample(ClaAdditionalPostViewExample example);
}