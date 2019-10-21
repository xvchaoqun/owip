package persistence.qy;

import domain.qy.QyRewardRecord;
import domain.qy.QyRewardRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QyRewardRecordMapper {
    long countByExample(QyRewardRecordExample example);

    int deleteByExample(QyRewardRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QyRewardRecord record);

    int insertSelective(QyRewardRecord record);

    List<QyRewardRecord> selectByExampleWithRowbounds(QyRewardRecordExample example, RowBounds rowBounds);

    List<QyRewardRecord> selectByExample(QyRewardRecordExample example);

    QyRewardRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QyRewardRecord record, @Param("example") QyRewardRecordExample example);

    int updateByExample(@Param("record") QyRewardRecord record, @Param("example") QyRewardRecordExample example);

    int updateByPrimaryKeySelective(QyRewardRecord record);

    int updateByPrimaryKey(QyRewardRecord record);
}