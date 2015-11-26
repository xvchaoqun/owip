package persistence;

import domain.CadreResearch;
import domain.CadreResearchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreResearchMapper {
    int countByExample(CadreResearchExample example);

    int deleteByExample(CadreResearchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreResearch record);

    int insertSelective(CadreResearch record);

    List<CadreResearch> selectByExampleWithRowbounds(CadreResearchExample example, RowBounds rowBounds);

    List<CadreResearch> selectByExample(CadreResearchExample example);

    CadreResearch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreResearch record, @Param("example") CadreResearchExample example);

    int updateByExample(@Param("record") CadreResearch record, @Param("example") CadreResearchExample example);

    int updateByPrimaryKeySelective(CadreResearch record);

    int updateByPrimaryKey(CadreResearch record);
}