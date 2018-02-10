package persistence.sc.scPublic;

import domain.sc.scPublic.ScPublicView;
import domain.sc.scPublic.ScPublicViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScPublicViewMapper {
    long countByExample(ScPublicViewExample example);

    List<ScPublicView> selectByExampleWithRowbounds(ScPublicViewExample example, RowBounds rowBounds);

    List<ScPublicView> selectByExample(ScPublicViewExample example);
}