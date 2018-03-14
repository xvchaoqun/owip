package persistence.cet;

import domain.cet.CetTrainView;
import domain.cet.CetTrainViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainViewMapper {
    long countByExample(CetTrainViewExample example);

    List<CetTrainView> selectByExampleWithBLOBsWithRowbounds(CetTrainViewExample example, RowBounds rowBounds);

    List<CetTrainView> selectByExampleWithBLOBs(CetTrainViewExample example);

    List<CetTrainView> selectByExampleWithRowbounds(CetTrainViewExample example, RowBounds rowBounds);

    List<CetTrainView> selectByExample(CetTrainViewExample example);
}