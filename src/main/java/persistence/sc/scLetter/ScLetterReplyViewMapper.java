package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterReplyView;
import domain.sc.scLetter.ScLetterReplyViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScLetterReplyViewMapper {
    long countByExample(ScLetterReplyViewExample example);

    List<ScLetterReplyView> selectByExampleWithRowbounds(ScLetterReplyViewExample example, RowBounds rowBounds);

    List<ScLetterReplyView> selectByExample(ScLetterReplyViewExample example);
}