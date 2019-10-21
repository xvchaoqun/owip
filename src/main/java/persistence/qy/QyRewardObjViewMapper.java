package persistence.qy;

import domain.qy.QyRewardObjView;
import domain.qy.QyRewardObjViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface QyRewardObjViewMapper {
    long countByExample(QyRewardObjViewExample example);

    List<QyRewardObjView> selectByExampleWithRowbounds(QyRewardObjViewExample example, RowBounds rowBounds);

    List<QyRewardObjView> selectByExample(QyRewardObjViewExample example);
}