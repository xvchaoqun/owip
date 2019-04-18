package persistence.member;

import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberApplyViewMapper {
    long countByExample(MemberApplyViewExample example);

    List<MemberApplyView> selectByExampleWithRowbounds(MemberApplyViewExample example, RowBounds rowBounds);

    List<MemberApplyView> selectByExample(MemberApplyViewExample example);
}