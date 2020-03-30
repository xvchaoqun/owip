package persistence.dr;

import domain.dr.DrOnlineResultView;
import domain.dr.DrOnlineResultViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOnlineResultViewMapper {
    long countByExample(DrOnlineResultViewExample example);

    int deleteByExample(DrOnlineResultViewExample example);

    int insert(DrOnlineResultView record);

    int insertSelective(DrOnlineResultView record);

    List<DrOnlineResultView> selectByExampleWithRowbounds(DrOnlineResultViewExample example, RowBounds rowBounds);

    List<DrOnlineResultView> selectByExample(DrOnlineResultViewExample example);

    int updateByExampleSelective(@Param("record") DrOnlineResultView record, @Param("example") DrOnlineResultViewExample example);

    int updateByExample(@Param("record") DrOnlineResultView record, @Param("example") DrOnlineResultViewExample example);
}