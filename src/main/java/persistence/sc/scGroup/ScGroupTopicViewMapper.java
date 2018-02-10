package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupTopicView;
import domain.sc.scGroup.ScGroupTopicViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupTopicViewMapper {
    long countByExample(ScGroupTopicViewExample example);

    List<ScGroupTopicView> selectByExampleWithRowbounds(ScGroupTopicViewExample example, RowBounds rowBounds);

    List<ScGroupTopicView> selectByExample(ScGroupTopicViewExample example);
}