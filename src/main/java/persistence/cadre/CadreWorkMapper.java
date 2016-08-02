package persistence.cadre;

import domain.cadre.CadreWork;
import domain.cadre.CadreWorkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreWorkMapper {
    int countByExample(CadreWorkExample example);

    int deleteByExample(CadreWorkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreWork record);

    int insertSelective(CadreWork record);

    List<CadreWork> selectByExampleWithRowbounds(CadreWorkExample example, RowBounds rowBounds);

    List<CadreWork> selectByExample(CadreWorkExample example);

    CadreWork selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreWork record, @Param("example") CadreWorkExample example);

    int updateByExample(@Param("record") CadreWork record, @Param("example") CadreWorkExample example);

    int updateByPrimaryKeySelective(CadreWork record);

    int updateByPrimaryKey(CadreWork record);
}