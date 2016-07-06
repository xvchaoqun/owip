package persistence.member;

import domain.member.MemberOutflowView;
import domain.member.MemberOutflowViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberOutflowViewMapper {
    int countByExample(MemberOutflowViewExample example);

    int deleteByExample(MemberOutflowViewExample example);

    int insert(MemberOutflowView record);

    int insertSelective(MemberOutflowView record);

    List<MemberOutflowView> selectByExampleWithRowbounds(MemberOutflowViewExample example, RowBounds rowBounds);

    List<MemberOutflowView> selectByExample(MemberOutflowViewExample example);

    int updateByExampleSelective(@Param("record") MemberOutflowView record, @Param("example") MemberOutflowViewExample example);

    int updateByExample(@Param("record") MemberOutflowView record, @Param("example") MemberOutflowViewExample example);
}