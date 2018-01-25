package persistence.sc.scLetter;

import domain.sc.scLetter.ScLetterView;
import domain.sc.scLetter.ScLetterViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScLetterViewMapper {
    long countByExample(ScLetterViewExample example);

    List<ScLetterView> selectByExampleWithRowbounds(ScLetterViewExample example, RowBounds rowBounds);

    List<ScLetterView> selectByExample(ScLetterViewExample example);
}