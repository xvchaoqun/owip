package persistence.cet;

import domain.cet.CetTrainObjView;
import domain.cet.CetTrainObjViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainObjViewMapper {
    long countByExample(CetTrainObjViewExample example);

    List<CetTrainObjView> selectByExampleWithRowbounds(CetTrainObjViewExample example, RowBounds rowBounds);

    List<CetTrainObjView> selectByExample(CetTrainObjViewExample example);
}