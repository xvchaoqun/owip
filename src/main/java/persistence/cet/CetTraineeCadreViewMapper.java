package persistence.cet;

import domain.cet.CetTraineeCadreView;
import domain.cet.CetTraineeCadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeCadreViewMapper {
    long countByExample(CetTraineeCadreViewExample example);

    List<CetTraineeCadreView> selectByExampleWithRowbounds(CetTraineeCadreViewExample example, RowBounds rowBounds);

    List<CetTraineeCadreView> selectByExample(CetTraineeCadreViewExample example);
}