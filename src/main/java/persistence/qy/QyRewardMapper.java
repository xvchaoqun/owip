package persistence.qy;

import domain.qy.QyReward;
import domain.qy.QyRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QyRewardMapper {
    long countByExample(QyRewardExample example);

    int deleteByExample(QyRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QyReward record);

    int insertSelective(QyReward record);

    List<QyReward> selectByExampleWithRowbounds(QyRewardExample example, RowBounds rowBounds);

    List<QyReward> selectByExample(QyRewardExample example);

    QyReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QyReward record, @Param("example") QyRewardExample example);

    int updateByExample(@Param("record") QyReward record, @Param("example") QyRewardExample example);

    int updateByPrimaryKeySelective(QyReward record);

    int updateByPrimaryKey(QyReward record);
}