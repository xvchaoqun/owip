package persistence.dr;

import domain.dr.DrOnlinePostView;
import domain.dr.DrOnlinePostViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOnlinePostViewMapper {
    long countByExample(DrOnlinePostViewExample example);

    int deleteByExample(DrOnlinePostViewExample example);

    int insert(DrOnlinePostView record);

    int insertSelective(DrOnlinePostView record);

    List<DrOnlinePostView> selectByExampleWithRowbounds(DrOnlinePostViewExample example, RowBounds rowBounds);

    List<DrOnlinePostView> selectByExample(DrOnlinePostViewExample example);

    int updateByExampleSelective(@Param("record") DrOnlinePostView record, @Param("example") DrOnlinePostViewExample example);

    int updateByExample(@Param("record") DrOnlinePostView record, @Param("example") DrOnlinePostViewExample example);
}