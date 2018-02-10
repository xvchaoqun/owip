package persistence.member;

import domain.member.MemberApplyView;
import domain.member.MemberApplyViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberApplyViewMapper {
    long countByExample(MemberApplyViewExample example);

    List<MemberApplyView> selectByExampleWithRowbounds(MemberApplyViewExample example, RowBounds rowBounds);

    List<MemberApplyView> selectByExample(MemberApplyViewExample example);
}