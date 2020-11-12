package persistence.pm;

import domain.pm.Pm3Meeting;
import domain.pm.Pm3MeetingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface Pm3MeetingMapper {
    long countByExample(Pm3MeetingExample example);

    int deleteByExample(Pm3MeetingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Pm3Meeting record);

    int insertSelective(Pm3Meeting record);

    List<Pm3Meeting> selectByExampleWithRowbounds(Pm3MeetingExample example, RowBounds rowBounds);

    List<Pm3Meeting> selectByExample(Pm3MeetingExample example);

    Pm3Meeting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Pm3Meeting record, @Param("example") Pm3MeetingExample example);

    int updateByExample(@Param("record") Pm3Meeting record, @Param("example") Pm3MeetingExample example);

    int updateByPrimaryKeySelective(Pm3Meeting record);

    int updateByPrimaryKey(Pm3Meeting record);
}