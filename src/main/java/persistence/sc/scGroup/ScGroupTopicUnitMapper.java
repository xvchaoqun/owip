package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupTopicUnit;
import domain.sc.scGroup.ScGroupTopicUnitExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupTopicUnitMapper {
    long countByExample(ScGroupTopicUnitExample example);

    int deleteByExample(ScGroupTopicUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroupTopicUnit record);

    int insertSelective(ScGroupTopicUnit record);

    List<ScGroupTopicUnit> selectByExampleWithRowbounds(ScGroupTopicUnitExample example, RowBounds rowBounds);

    List<ScGroupTopicUnit> selectByExample(ScGroupTopicUnitExample example);

    ScGroupTopicUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroupTopicUnit record, @Param("example") ScGroupTopicUnitExample example);

    int updateByExample(@Param("record") ScGroupTopicUnit record, @Param("example") ScGroupTopicUnitExample example);

    int updateByPrimaryKeySelective(ScGroupTopicUnit record);

    int updateByPrimaryKey(ScGroupTopicUnit record);
}