package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterReply;
import domain.sc.scLetter.ScLetterReplyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScLetterReplyMapper {
    long countByExample(ScLetterReplyExample example);

    int deleteByExample(ScLetterReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScLetterReply record);

    int insertSelective(ScLetterReply record);

    List<ScLetterReply> selectByExampleWithRowbounds(ScLetterReplyExample example, RowBounds rowBounds);

    List<ScLetterReply> selectByExample(ScLetterReplyExample example);

    ScLetterReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScLetterReply record, @Param("example") ScLetterReplyExample example);

    int updateByExample(@Param("record") ScLetterReply record, @Param("example") ScLetterReplyExample example);

    int updateByPrimaryKeySelective(ScLetterReply record);

    int updateByPrimaryKey(ScLetterReply record);
}