package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterCheckItemView;
import domain.sc.scMatter.ScMatterCheckItemViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScMatterCheckItemViewMapper {
    long countByExample(ScMatterCheckItemViewExample example);

    List<ScMatterCheckItemView> selectByExampleWithRowbounds(ScMatterCheckItemViewExample example, RowBounds rowBounds);

    List<ScMatterCheckItemView> selectByExample(ScMatterCheckItemViewExample example);
}