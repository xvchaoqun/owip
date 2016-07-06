package persistence.member;

import domain.member.MemberInflow;
import domain.member.MemberInflowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberInflowMapper {
    int countByExample(MemberInflowExample example);

    int deleteByExample(MemberInflowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberInflow record);

    int insertSelective(MemberInflow record);

    List<MemberInflow> selectByExampleWithRowbounds(MemberInflowExample example, RowBounds rowBounds);

    List<MemberInflow> selectByExample(MemberInflowExample example);

    MemberInflow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberInflow record, @Param("example") MemberInflowExample example);

    int updateByExample(@Param("record") MemberInflow record, @Param("example") MemberInflowExample example);

    int updateByPrimaryKeySelective(MemberInflow record);

    int updateByPrimaryKey(MemberInflow record);
}