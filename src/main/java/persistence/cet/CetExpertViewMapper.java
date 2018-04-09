package persistence.cet;

import domain.cet.CetExpertView;
import domain.cet.CetExpertViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetExpertViewMapper {
    long countByExample(CetExpertViewExample example);

    List<CetExpertView> selectByExampleWithRowbounds(CetExpertViewExample example, RowBounds rowBounds);

    List<CetExpertView> selectByExample(CetExpertViewExample example);
}