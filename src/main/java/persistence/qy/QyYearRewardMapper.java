package persistence.qy;

import domain.qy.QyYearReward;
import domain.qy.QyYearRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QyYearRewardMapper {
    long countByExample(QyYearRewardExample example);

    int deleteByExample(QyYearRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QyYearReward record);

    int insertSelective(QyYearReward record);

    List<QyYearReward> selectByExampleWithRowbounds(QyYearRewardExample example, RowBounds rowBounds);

    List<QyYearReward> selectByExample(QyYearRewardExample example);

    QyYearReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QyYearReward record, @Param("example") QyYearRewardExample example);

    int updateByExample(@Param("record") QyYearReward record, @Param("example") QyYearRewardExample example);

    int updateByPrimaryKeySelective(QyYearReward record);

    int updateByPrimaryKey(QyYearReward record);
}