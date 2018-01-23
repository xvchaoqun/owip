package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterItemView;
import domain.sc.scMatter.ScMatterItemViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterItemViewMapper {
    long countByExample(ScMatterItemViewExample example);

    List<ScMatterItemView> selectByExampleWithRowbounds(ScMatterItemViewExample example, RowBounds rowBounds);

    List<ScMatterItemView> selectByExample(ScMatterItemViewExample example);
}