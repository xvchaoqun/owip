package persistence.qy;

import domain.qy.QyRewardObj;
import domain.qy.QyRewardObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QyRewardObjMapper {
    long countByExample(QyRewardObjExample example);

    int deleteByExample(QyRewardObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QyRewardObj record);

    int insertSelective(QyRewardObj record);

    List<QyRewardObj> selectByExampleWithRowbounds(QyRewardObjExample example, RowBounds rowBounds);

    List<QyRewardObj> selectByExample(QyRewardObjExample example);

    QyRewardObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QyRewardObj record, @Param("example") QyRewardObjExample example);

    int updateByExample(@Param("record") QyRewardObj record, @Param("example") QyRewardObjExample example);

    int updateByPrimaryKeySelective(QyRewardObj record);

    int updateByPrimaryKey(QyRewardObj record);
}