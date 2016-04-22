package persistence;

import domain.MemberStayView;
import domain.MemberStayViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberStayViewMapper {
    int countByExample(MemberStayViewExample example);

    int deleteByExample(MemberStayViewExample example);

    int insert(MemberStayView record);

    int insertSelective(MemberStayView record);

    List<MemberStayView> selectByExampleWithRowbounds(MemberStayViewExample example, RowBounds rowBounds);

    List<MemberStayView> selectByExample(MemberStayViewExample example);

    int updateByExampleSelective(@Param("record") MemberStayView record, @Param("example") MemberStayViewExample example);

    int updateByExample(@Param("record") MemberStayView record, @Param("example") MemberStayViewExample example);
}