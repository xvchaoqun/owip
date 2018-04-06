package persistence.cet;

import domain.cet.CetProjectObjCadreView;
import domain.cet.CetProjectObjCadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectObjCadreViewMapper {
    long countByExample(CetProjectObjCadreViewExample example);

    List<CetProjectObjCadreView> selectByExampleWithRowbounds(CetProjectObjCadreViewExample example, RowBounds rowBounds);

    List<CetProjectObjCadreView> selectByExample(CetProjectObjCadreViewExample example);
}