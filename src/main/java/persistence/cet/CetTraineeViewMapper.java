package persistence.cet;

import domain.cet.CetTraineeView;
import domain.cet.CetTraineeViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeViewMapper {
    long countByExample(CetTraineeViewExample example);

    List<CetTraineeView> selectByExampleWithRowbounds(CetTraineeViewExample example, RowBounds rowBounds);

    List<CetTraineeView> selectByExample(CetTraineeViewExample example);
}