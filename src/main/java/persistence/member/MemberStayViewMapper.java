package persistence.member;

import domain.member.MemberStayView;
import domain.member.MemberStayViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberStayViewMapper {
    long countByExample(MemberStayViewExample example);

    List<MemberStayView> selectByExampleWithRowbounds(MemberStayViewExample example, RowBounds rowBounds);

    List<MemberStayView> selectByExample(MemberStayViewExample example);
}