package persistence.cet;

import domain.cet.CetProjectObjView;
import domain.cet.CetProjectObjViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectObjViewMapper {
    long countByExample(CetProjectObjViewExample example);

    List<CetProjectObjView> selectByExampleWithRowbounds(CetProjectObjViewExample example, RowBounds rowBounds);

    List<CetProjectObjView> selectByExample(CetProjectObjViewExample example);
}