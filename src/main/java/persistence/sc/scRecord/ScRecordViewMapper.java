package persistence.sc.scRecord;

import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScRecordViewMapper {
    long countByExample(ScRecordViewExample example);

    List<ScRecordView> selectByExampleWithRowbounds(ScRecordViewExample example, RowBounds rowBounds);

    List<ScRecordView> selectByExample(ScRecordViewExample example);
}