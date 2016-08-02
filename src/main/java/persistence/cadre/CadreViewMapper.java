package persistence.cadre;

import domain.cadre.CadreView;
import domain.cadre.CadreViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreViewMapper {
    int countByExample(CadreViewExample example);

    int deleteByExample(CadreViewExample example);

    int insert(CadreView record);

    int insertSelective(CadreView record);

    List<CadreView> selectByExampleWithRowbounds(CadreViewExample example, RowBounds rowBounds);

    List<CadreView> selectByExample(CadreViewExample example);

    int updateByExampleSelective(@Param("record") CadreView record, @Param("example") CadreViewExample example);

    int updateByExample(@Param("record") CadreView record, @Param("example") CadreViewExample example);
}