package persistence.cet;

import domain.cet.CetSpecialObjCadreView;
import domain.cet.CetSpecialObjCadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetSpecialObjCadreViewMapper {
    long countByExample(CetSpecialObjCadreViewExample example);

    List<CetSpecialObjCadreView> selectByExampleWithRowbounds(CetSpecialObjCadreViewExample example, RowBounds rowBounds);

    List<CetSpecialObjCadreView> selectByExample(CetSpecialObjCadreViewExample example);
}