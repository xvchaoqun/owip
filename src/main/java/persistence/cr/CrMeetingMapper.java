package persistence.cr;

import domain.cr.CrMeeting;
import domain.cr.CrMeetingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrMeetingMapper {
    long countByExample(CrMeetingExample example);

    int deleteByExample(CrMeetingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrMeeting record);

    int insertSelective(CrMeeting record);

    List<CrMeeting> selectByExampleWithRowbounds(CrMeetingExample example, RowBounds rowBounds);

    List<CrMeeting> selectByExample(CrMeetingExample example);

    CrMeeting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrMeeting record, @Param("example") CrMeetingExample example);

    int updateByExample(@Param("record") CrMeeting record, @Param("example") CrMeetingExample example);

    int updateByPrimaryKeySelective(CrMeeting record);

    int updateByPrimaryKey(CrMeeting record);
}