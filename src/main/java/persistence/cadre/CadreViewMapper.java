package persistence.cadre;

import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadreViewMapper {
    long countByExample(CadreViewExample example);

    List<CadreView> selectByExampleWithRowbounds(CadreViewExample example, RowBounds rowBounds);

    List<CadreView> selectByExample(CadreViewExample example);

    CadreView selectByPrimaryKey(Integer id);
}