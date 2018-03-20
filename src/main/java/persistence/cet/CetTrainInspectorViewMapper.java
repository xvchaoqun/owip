package persistence.cet;

import domain.cet.CetTrainInspectorView;
import domain.cet.CetTrainInspectorViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainInspectorViewMapper {
    long countByExample(CetTrainInspectorViewExample example);

    List<CetTrainInspectorView> selectByExampleWithRowbounds(CetTrainInspectorViewExample example, RowBounds rowBounds);

    List<CetTrainInspectorView> selectByExample(CetTrainInspectorViewExample example);
}