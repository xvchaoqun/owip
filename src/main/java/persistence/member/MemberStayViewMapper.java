package persistence.member;

import domain.member.MemberStayView;
import domain.member.MemberStayViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberStayViewMapper {
    long countByExample(MemberStayViewExample example);

    List<MemberStayView> selectByExampleWithRowbounds(MemberStayViewExample example, RowBounds rowBounds);

    List<MemberStayView> selectByExample(MemberStayViewExample example);
}