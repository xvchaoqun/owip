package persistence.cadre;

import domain.cadre.CadreAdLog;
import domain.cadre.CadreAdLogExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreAdLogMapper {
    long countByExample(CadreAdLogExample example);

    int deleteByExample(CadreAdLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreAdLog record);

    int insertSelective(CadreAdLog record);

    List<CadreAdLog> selectByExampleWithRowbounds(CadreAdLogExample example, RowBounds rowBounds);

    List<CadreAdLog> selectByExample(CadreAdLogExample example);

    CadreAdLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreAdLog record, @Param("example") CadreAdLogExample example);

    int updateByExample(@Param("record") CadreAdLog record, @Param("example") CadreAdLogExample example);

    int updateByPrimaryKeySelective(CadreAdLog record);

    int updateByPrimaryKey(CadreAdLog record);
}