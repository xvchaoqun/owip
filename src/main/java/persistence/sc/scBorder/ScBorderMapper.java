package persistence.sc.scBorder;

import domain.sc.scBorder.ScBorder;
import domain.sc.scBorder.ScBorderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScBorderMapper {
    long countByExample(ScBorderExample example);

    int deleteByExample(ScBorderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScBorder record);

    int insertSelective(ScBorder record);

    List<ScBorder> selectByExampleWithRowbounds(ScBorderExample example, RowBounds rowBounds);

    List<ScBorder> selectByExample(ScBorderExample example);

    ScBorder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScBorder record, @Param("example") ScBorderExample example);

    int updateByExample(@Param("record") ScBorder record, @Param("example") ScBorderExample example);

    int updateByPrimaryKeySelective(ScBorder record);

    int updateByPrimaryKey(ScBorder record);
}