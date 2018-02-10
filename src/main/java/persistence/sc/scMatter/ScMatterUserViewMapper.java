package persistence.sc.scMatter;

import domain.sc.scMatter.ScMatterUserView;
import domain.sc.scMatter.ScMatterUserViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScMatterUserViewMapper {
    long countByExample(ScMatterUserViewExample example);

    List<ScMatterUserView> selectByExampleWithRowbounds(ScMatterUserViewExample example, RowBounds rowBounds);

    List<ScMatterUserView> selectByExample(ScMatterUserViewExample example);
}