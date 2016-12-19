package persistence.member;

import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberApplyViewMapper {
    int countByExample(MemberApplyViewExample example);

    List<MemberApplyView> selectByExampleWithBLOBsWithRowbounds(MemberApplyViewExample example, RowBounds rowBounds);

    List<MemberApplyView> selectByExampleWithBLOBs(MemberApplyViewExample example);

    List<MemberApplyView> selectByExampleWithRowbounds(MemberApplyViewExample example, RowBounds rowBounds);

    List<MemberApplyView> selectByExample(MemberApplyViewExample example);
}