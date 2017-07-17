package persistence.member;

import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberApplyViewMapper {
    int countByExample(MemberApplyViewExample example);

    List<MemberApplyView> selectByExampleWithBLOBsWithRowbounds(MemberApplyViewExample example, RowBounds rowBounds);

    List<MemberApplyView> selectByExampleWithBLOBs(MemberApplyViewExample example);

    List<MemberApplyView> selectByExampleWithRowbounds(MemberApplyViewExample example, RowBounds rowBounds);

    List<MemberApplyView> selectByExample(MemberApplyViewExample example);
}