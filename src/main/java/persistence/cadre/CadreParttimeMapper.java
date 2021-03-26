package persistence.cadre;

import domain.cadre.CadreParttime;
import domain.cadre.CadreParttimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreParttimeMapper {
    long countByExample(CadreParttimeExample example);

    int deleteByExample(CadreParttimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreParttime record);

    int insertSelective(CadreParttime record);

    List<CadreParttime> selectByExampleWithRowbounds(CadreParttimeExample example, RowBounds rowBounds);

    List<CadreParttime> selectByExample(CadreParttimeExample example);

    CadreParttime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreParttime record, @Param("example") CadreParttimeExample example);

    int updateByExample(@Param("record") CadreParttime record, @Param("example") CadreParttimeExample example);

    int updateByPrimaryKeySelective(CadreParttime record);

    int updateByPrimaryKey(CadreParttime record);
}