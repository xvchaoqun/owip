package persistence.member;

import domain.member.MemberView;
import domain.member.MemberViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberViewMapper {
    long countByExample(MemberViewExample example);

    List<MemberView> selectByExampleWithBLOBsWithRowbounds(MemberViewExample example, RowBounds rowBounds);

    List<MemberView> selectByExampleWithBLOBs(MemberViewExample example);

    List<MemberView> selectByExampleWithRowbounds(MemberViewExample example, RowBounds rowBounds);

    List<MemberView> selectByExample(MemberViewExample example);
}