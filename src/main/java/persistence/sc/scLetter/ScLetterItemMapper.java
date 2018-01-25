package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterItem;
import domain.sc.scLetter.ScLetterItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScLetterItemMapper {
    long countByExample(ScLetterItemExample example);

    int deleteByExample(ScLetterItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScLetterItem record);

    int insertSelective(ScLetterItem record);

    List<ScLetterItem> selectByExampleWithRowbounds(ScLetterItemExample example, RowBounds rowBounds);

    List<ScLetterItem> selectByExample(ScLetterItemExample example);

    ScLetterItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScLetterItem record, @Param("example") ScLetterItemExample example);

    int updateByExample(@Param("record") ScLetterItem record, @Param("example") ScLetterItemExample example);

    int updateByPrimaryKeySelective(ScLetterItem record);

    int updateByPrimaryKey(ScLetterItem record);
}