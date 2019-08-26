package persistence.sc.scPublic;

import domain.sc.scPublic.ScPublicUserView;
import domain.sc.scPublic.ScPublicUserViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScPublicUserViewMapper {
    long countByExample(ScPublicUserViewExample example);

    List<ScPublicUserView> selectByExampleWithRowbounds(ScPublicUserViewExample example, RowBounds rowBounds);

    List<ScPublicUserView> selectByExample(ScPublicUserViewExample example);
}