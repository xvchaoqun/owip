package persistence.cadre;

import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreViewMapper {
    long countByExample(CadreViewExample example);

    List<CadreView> selectByExampleWithRowbounds(CadreViewExample example, RowBounds rowBounds);

    List<CadreView> selectByExample(CadreViewExample example);

    CadreView selectByPrimaryKey(Integer id);
}