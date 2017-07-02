package persistence.member;

import domain.member.MemberOutView;
import domain.member.MemberOutViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberOutViewMapper {
    long countByExample(MemberOutViewExample example);

    List<MemberOutView> selectByExampleWithRowbounds(MemberOutViewExample example, RowBounds rowBounds);

    List<MemberOutView> selectByExample(MemberOutViewExample example);
}