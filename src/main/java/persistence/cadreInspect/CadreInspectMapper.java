package persistence.cadreInspect;

import domain.cadreInspect.CadreInspect;
import domain.cadreInspect.CadreInspectExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreInspectMapper {
    int countByExample(CadreInspectExample example);

    int deleteByExample(CadreInspectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreInspect record);

    int insertSelective(CadreInspect record);

    List<CadreInspect> selectByExampleWithRowbounds(CadreInspectExample example, RowBounds rowBounds);

    List<CadreInspect> selectByExample(CadreInspectExample example);

    CadreInspect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreInspect record, @Param("example") CadreInspectExample example);

    int updateByExample(@Param("record") CadreInspect record, @Param("example") CadreInspectExample example);

    int updateByPrimaryKeySelective(CadreInspect record);

    int updateByPrimaryKey(CadreInspect record);
}