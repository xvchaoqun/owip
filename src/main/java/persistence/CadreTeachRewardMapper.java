package persistence;

import domain.CadreTeachReward;
import domain.CadreTeachRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreTeachRewardMapper {
    int countByExample(CadreTeachRewardExample example);

    int deleteByExample(CadreTeachRewardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreTeachReward record);

    int insertSelective(CadreTeachReward record);

    List<CadreTeachReward> selectByExampleWithRowbounds(CadreTeachRewardExample example, RowBounds rowBounds);

    List<CadreTeachReward> selectByExample(CadreTeachRewardExample example);

    CadreTeachReward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreTeachReward record, @Param("example") CadreTeachRewardExample example);

    int updateByExample(@Param("record") CadreTeachReward record, @Param("example") CadreTeachRewardExample example);

    int updateByPrimaryKeySelective(CadreTeachReward record);

    int updateByPrimaryKey(CadreTeachReward record);
}