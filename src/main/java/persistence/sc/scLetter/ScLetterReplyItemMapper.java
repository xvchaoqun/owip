package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterReplyItem;
import domain.sc.scLetter.ScLetterReplyItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScLetterReplyItemMapper {
    long countByExample(ScLetterReplyItemExample example);

    int deleteByExample(ScLetterReplyItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScLetterReplyItem record);

    int insertSelective(ScLetterReplyItem record);

    List<ScLetterReplyItem> selectByExampleWithRowbounds(ScLetterReplyItemExample example, RowBounds rowBounds);

    List<ScLetterReplyItem> selectByExample(ScLetterReplyItemExample example);

    ScLetterReplyItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScLetterReplyItem record, @Param("example") ScLetterReplyItemExample example);

    int updateByExample(@Param("record") ScLetterReplyItem record, @Param("example") ScLetterReplyItemExample example);

    int updateByPrimaryKeySelective(ScLetterReplyItem record);

    int updateByPrimaryKey(ScLetterReplyItem record);
}