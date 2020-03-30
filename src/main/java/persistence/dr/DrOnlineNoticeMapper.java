package persistence.dr;

import domain.dr.DrOnlineNotice;
import domain.dr.DrOnlineNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DrOnlineNoticeMapper {
    long countByExample(DrOnlineNoticeExample example);

    int deleteByExample(DrOnlineNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrOnlineNotice record);

    int insertSelective(DrOnlineNotice record);

    List<DrOnlineNotice> selectByExampleWithBLOBsWithRowbounds(DrOnlineNoticeExample example, RowBounds rowBounds);

    List<DrOnlineNotice> selectByExampleWithBLOBs(DrOnlineNoticeExample example);

    List<DrOnlineNotice> selectByExampleWithRowbounds(DrOnlineNoticeExample example, RowBounds rowBounds);

    List<DrOnlineNotice> selectByExample(DrOnlineNoticeExample example);

    DrOnlineNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrOnlineNotice record, @Param("example") DrOnlineNoticeExample example);

    int updateByExampleWithBLOBs(@Param("record") DrOnlineNotice record, @Param("example") DrOnlineNoticeExample example);

    int updateByExample(@Param("record") DrOnlineNotice record, @Param("example") DrOnlineNoticeExample example);

    int updateByPrimaryKeySelective(DrOnlineNotice record);

    int updateByPrimaryKeyWithBLOBs(DrOnlineNotice record);

    int updateByPrimaryKey(DrOnlineNotice record);
}