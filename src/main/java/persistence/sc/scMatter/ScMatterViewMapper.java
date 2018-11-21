package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterView;
import domain.sc.scMatter.ScMatterViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterViewMapper {
    long countByExample(ScMatterViewExample example);

    List<ScMatterView> selectByExampleWithRowbounds(ScMatterViewExample example, RowBounds rowBounds);

    List<ScMatterView> selectByExample(ScMatterViewExample example);
}