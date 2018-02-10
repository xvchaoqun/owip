package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterItemView;
import domain.sc.scLetter.ScLetterItemViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScLetterItemViewMapper {
    long countByExample(ScLetterItemViewExample example);

    List<ScLetterItemView> selectByExampleWithRowbounds(ScLetterItemViewExample example, RowBounds rowBounds);

    List<ScLetterItemView> selectByExample(ScLetterItemViewExample example);
}