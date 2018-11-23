package persistence.sc.scBorder;

import domain.sc.scBorder.ScBorderItem;
import domain.sc.scBorder.ScBorderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScBorderItemMapper {
    long countByExample(ScBorderItemExample example);

    int deleteByExample(ScBorderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScBorderItem record);

    int insertSelective(ScBorderItem record);

    List<ScBorderItem> selectByExampleWithRowbounds(ScBorderItemExample example, RowBounds rowBounds);

    List<ScBorderItem> selectByExample(ScBorderItemExample example);

    ScBorderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScBorderItem record, @Param("example") ScBorderItemExample example);

    int updateByExample(@Param("record") ScBorderItem record, @Param("example") ScBorderItemExample example);

    int updateByPrimaryKeySelective(ScBorderItem record);

    int updateByPrimaryKey(ScBorderItem record);
}