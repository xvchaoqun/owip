package persistence.abroad;

import domain.abroad.AbroadAdditionalPostView;
import domain.abroad.AbroadAdditionalPostViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface AbroadAdditionalPostViewMapper {
    long countByExample(AbroadAdditionalPostViewExample example);

    List<AbroadAdditionalPostView> selectByExampleWithRowbounds(AbroadAdditionalPostViewExample example, RowBounds rowBounds);

    List<AbroadAdditionalPostView> selectByExample(AbroadAdditionalPostViewExample example);
}