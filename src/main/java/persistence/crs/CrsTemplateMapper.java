package persistence.crs;

import domain.crs.CrsTemplate;
import domain.crs.CrsTemplateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsTemplateMapper {
    long countByExample(CrsTemplateExample example);

    int deleteByExample(CrsTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsTemplate record);

    int insertSelective(CrsTemplate record);

    List<CrsTemplate> selectByExampleWithRowbounds(CrsTemplateExample example, RowBounds rowBounds);

    List<CrsTemplate> selectByExample(CrsTemplateExample example);

    CrsTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsTemplate record, @Param("example") CrsTemplateExample example);

    int updateByExample(@Param("record") CrsTemplate record, @Param("example") CrsTemplateExample example);

    int updateByPrimaryKeySelective(CrsTemplate record);

    int updateByPrimaryKey(CrsTemplate record);
}