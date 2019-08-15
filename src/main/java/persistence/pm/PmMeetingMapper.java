package persistence.pm;

import domain.pm.PmMeeting;
import domain.pm.PmMeetingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmMeetingMapper {
    long countByExample(PmMeetingExample example);

    int deleteByExample(PmMeetingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmMeeting record);

    int insertSelective(PmMeeting record);

    List<PmMeeting> selectByExampleWithBLOBsWithRowbounds(PmMeetingExample example, RowBounds rowBounds);

    List<PmMeeting> selectByExampleWithBLOBs(PmMeetingExample example);

    List<PmMeeting> selectByExampleWithRowbounds(PmMeetingExample example, RowBounds rowBounds);

    List<PmMeeting> selectByExample(PmMeetingExample example);

    PmMeeting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmMeeting record, @Param("example") PmMeetingExample example);

    int updateByExampleWithBLOBs(@Param("record") PmMeeting record, @Param("example") PmMeetingExample example);

    int updateByExample(@Param("record") PmMeeting record, @Param("example") PmMeetingExample example);

    int updateByPrimaryKeySelective(PmMeeting record);

    int updateByPrimaryKeyWithBLOBs(PmMeeting record);

    int updateByPrimaryKey(PmMeeting record);
}