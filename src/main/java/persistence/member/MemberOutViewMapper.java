package persistence.member;

import domain.member.MemberOutView;
import domain.member.MemberOutViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberOutViewMapper {
    long countByExample(MemberOutViewExample example);

    List<MemberOutView> selectByExampleWithRowbounds(MemberOutViewExample example, RowBounds rowBounds);

    List<MemberOutView> selectByExample(MemberOutViewExample example);
}