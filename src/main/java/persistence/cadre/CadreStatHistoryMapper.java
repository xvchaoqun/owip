package persistence.cadre;

import domain.cadre.CadreStatHistory;
import domain.cadre.CadreStatHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreStatHistoryMapper {
    int countByExample(CadreStatHistoryExample example);

    int deleteByExample(CadreStatHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreStatHistory record);

    int insertSelective(CadreStatHistory record);

    List<CadreStatHistory> selectByExampleWithRowbounds(CadreStatHistoryExample example, RowBounds rowBounds);

    List<CadreStatHistory> selectByExample(CadreStatHistoryExample example);

    CadreStatHistory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreStatHistory record, @Param("example") CadreStatHistoryExample example);

    int updateByExample(@Param("record") CadreStatHistory record, @Param("example") CadreStatHistoryExample example);

    int updateByPrimaryKeySelective(CadreStatHistory record);

    int updateByPrimaryKey(CadreStatHistory record);
}