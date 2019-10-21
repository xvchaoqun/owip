package persistence.qy;

import domain.qy.QyRewardRecordView;
import domain.qy.QyRewardRecordViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface QyRewardRecordViewMapper {
    long countByExample(QyRewardRecordViewExample example);

    List<QyRewardRecordView> selectByExampleWithRowbounds(QyRewardRecordViewExample example, RowBounds rowBounds);

    List<QyRewardRecordView> selectByExample(QyRewardRecordViewExample example);
}