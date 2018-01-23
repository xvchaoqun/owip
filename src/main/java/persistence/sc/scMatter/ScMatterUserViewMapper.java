package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterUserView;
import domain.sc.scMatter.ScMatterUserViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScMatterUserViewMapper {
    long countByExample(ScMatterUserViewExample example);

    List<ScMatterUserView> selectByExampleWithRowbounds(ScMatterUserViewExample example, RowBounds rowBounds);

    List<ScMatterUserView> selectByExample(ScMatterUserViewExample example);
}