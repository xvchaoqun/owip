package persistence.member;

import domain.member.MemberOutView;
import domain.member.MemberOutViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberOutViewMapper {
    long countByExample(MemberOutViewExample example);

    int deleteByExample(MemberOutViewExample example);

    int insert(MemberOutView record);

    int insertSelective(MemberOutView record);

    List<MemberOutView> selectByExampleWithRowbounds(MemberOutViewExample example, RowBounds rowBounds);

    List<MemberOutView> selectByExample(MemberOutViewExample example);

    int updateByExampleSelective(@Param("record") MemberOutView record, @Param("example") MemberOutViewExample example);

    int updateByExample(@Param("record") MemberOutView record, @Param("example") MemberOutViewExample example);
}