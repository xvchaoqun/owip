package persistence.cadre;

import domain.cadre.CadreInfoCheck;
import domain.cadre.CadreInfoCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreInfoCheckMapper {
    long countByExample(CadreInfoCheckExample example);

    int deleteByExample(CadreInfoCheckExample example);

    int deleteByPrimaryKey(Integer cadreId);

    int insert(CadreInfoCheck record);

    int insertSelective(CadreInfoCheck record);

    List<CadreInfoCheck> selectByExampleWithRowbounds(CadreInfoCheckExample example, RowBounds rowBounds);

    List<CadreInfoCheck> selectByExample(CadreInfoCheckExample example);

    CadreInfoCheck selectByPrimaryKey(Integer cadreId);

    int updateByExampleSelective(@Param("record") CadreInfoCheck record, @Param("example") CadreInfoCheckExample example);

    int updateByExample(@Param("record") CadreInfoCheck record, @Param("example") CadreInfoCheckExample example);

    int updateByPrimaryKeySelective(CadreInfoCheck record);

    int updateByPrimaryKey(CadreInfoCheck record);
}