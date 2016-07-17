package persistence.cadre;

import domain.cadre.CadreBook;
import domain.cadre.CadreBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreBookMapper {
    int countByExample(CadreBookExample example);

    int deleteByExample(CadreBookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreBook record);

    int insertSelective(CadreBook record);

    List<CadreBook> selectByExampleWithRowbounds(CadreBookExample example, RowBounds rowBounds);

    List<CadreBook> selectByExample(CadreBookExample example);

    CadreBook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreBook record, @Param("example") CadreBookExample example);

    int updateByExample(@Param("record") CadreBook record, @Param("example") CadreBookExample example);

    int updateByPrimaryKeySelective(CadreBook record);

    int updateByPrimaryKey(CadreBook record);
}