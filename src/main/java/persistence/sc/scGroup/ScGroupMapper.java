package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroup;
import domain.sc.scGroup.ScGroupExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupMapper {
    long countByExample(ScGroupExample example);

    int deleteByExample(ScGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroup record);

    int insertSelective(ScGroup record);

    List<ScGroup> selectByExampleWithRowbounds(ScGroupExample example, RowBounds rowBounds);

    List<ScGroup> selectByExample(ScGroupExample example);

    ScGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroup record, @Param("example") ScGroupExample example);

    int updateByExample(@Param("record") ScGroup record, @Param("example") ScGroupExample example);

    int updateByPrimaryKeySelective(ScGroup record);

    int updateByPrimaryKey(ScGroup record);
}