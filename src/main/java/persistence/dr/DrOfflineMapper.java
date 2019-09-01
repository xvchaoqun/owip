package persistence.dr;

import domain.dr.DrOffline;
import domain.dr.DrOfflineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOfflineMapper {
    long countByExample(DrOfflineExample example);

    int deleteByExample(DrOfflineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOffline record);

    int insertSelective(DrOffline record);

    List<DrOffline> selectByExampleWithRowbounds(DrOfflineExample example, RowBounds rowBounds);

    List<DrOffline> selectByExample(DrOfflineExample example);

    DrOffline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOffline record, @Param("example") DrOfflineExample example);

    int updateByExample(@Param("record") DrOffline record, @Param("example") DrOfflineExample example);

    int updateByPrimaryKeySelective(DrOffline record);

    int updateByPrimaryKey(DrOffline record);
}