package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterReplyItemView;
import domain.sc.scLetter.ScLetterReplyItemViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScLetterReplyItemViewMapper {
    long countByExample(ScLetterReplyItemViewExample example);

    List<ScLetterReplyItemView> selectByExampleWithRowbounds(ScLetterReplyItemViewExample example, RowBounds rowBounds);

    List<ScLetterReplyItemView> selectByExample(ScLetterReplyItemViewExample example);
}