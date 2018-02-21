package persistence.sc.scAd;

import domain.sc.scAd.ScAdUse;
import domain.sc.scAd.ScAdUseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScAdUseMapper {
    long countByExample(ScAdUseExample example);

    int deleteByExample(ScAdUseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScAdUse record);

    int insertSelective(ScAdUse record);

    List<ScAdUse> selectByExampleWithBLOBsWithRowbounds(ScAdUseExample example, RowBounds rowBounds);

    List<ScAdUse> selectByExampleWithBLOBs(ScAdUseExample example);

    List<ScAdUse> selectByExampleWithRowbounds(ScAdUseExample example, RowBounds rowBounds);

    List<ScAdUse> selectByExample(ScAdUseExample example);

    ScAdUse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScAdUse record, @Param("example") ScAdUseExample example);

    int updateByExampleWithBLOBs(@Param("record") ScAdUse record, @Param("example") ScAdUseExample example);

    int updateByExample(@Param("record") ScAdUse record, @Param("example") ScAdUseExample example);

    int updateByPrimaryKeySelective(ScAdUse record);

    int updateByPrimaryKeyWithBLOBs(ScAdUse record);

    int updateByPrimaryKey(ScAdUse record);
}