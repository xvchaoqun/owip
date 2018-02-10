package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterItemView;
import domain.sc.scMatter.ScMatterItemViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScMatterItemViewMapper {
    long countByExample(ScMatterItemViewExample example);

    List<ScMatterItemView> selectByExampleWithRowbounds(ScMatterItemViewExample example, RowBounds rowBounds);

    List<ScMatterItemView> selectByExample(ScMatterItemViewExample example);
}