package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterCheckView;
import domain.sc.scMatter.ScMatterCheckViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterCheckViewMapper {
    long countByExample(ScMatterCheckViewExample example);

    List<ScMatterCheckView> selectByExampleWithRowbounds(ScMatterCheckViewExample example, RowBounds rowBounds);

    List<ScMatterCheckView> selectByExample(ScMatterCheckViewExample example);
}