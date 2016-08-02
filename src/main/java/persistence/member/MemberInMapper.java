package persistence.member;

import domain.member.MemberIn;
import domain.member.MemberInExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberInMapper {
    int countByExample(MemberInExample example);

    int deleteByExample(MemberInExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberIn record);

    int insertSelective(MemberIn record);

    List<MemberIn> selectByExampleWithRowbounds(MemberInExample example, RowBounds rowBounds);

    List<MemberIn> selectByExample(MemberInExample example);

    MemberIn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberIn record, @Param("example") MemberInExample example);

    int updateByExample(@Param("record") MemberIn record, @Param("example") MemberInExample example);

    int updateByPrimaryKeySelective(MemberIn record);

    int updateByPrimaryKey(MemberIn record);
}