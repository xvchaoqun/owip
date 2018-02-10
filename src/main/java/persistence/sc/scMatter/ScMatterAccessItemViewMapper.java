package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterAccessItemView;
import domain.sc.scMatter.ScMatterAccessItemViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScMatterAccessItemViewMapper {
    long countByExample(ScMatterAccessItemViewExample example);

    List<ScMatterAccessItemView> selectByExampleWithRowbounds(ScMatterAccessItemViewExample example, RowBounds rowBounds);

    List<ScMatterAccessItemView> selectByExample(ScMatterAccessItemViewExample example);
}