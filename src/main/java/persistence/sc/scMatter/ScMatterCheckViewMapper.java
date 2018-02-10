package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterCheckView;
import domain.sc.scMatter.ScMatterCheckViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScMatterCheckViewMapper {
    long countByExample(ScMatterCheckViewExample example);

    List<ScMatterCheckView> selectByExampleWithRowbounds(ScMatterCheckViewExample example, RowBounds rowBounds);

    List<ScMatterCheckView> selectByExample(ScMatterCheckViewExample example);
}