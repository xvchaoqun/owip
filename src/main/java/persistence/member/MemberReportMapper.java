package persistence.member;

import domain.member.MemberReport;
import domain.member.MemberReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberReportMapper {
    long countByExample(MemberReportExample example);

    int deleteByExample(MemberReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberReport record);

    int insertSelective(MemberReport record);

    List<MemberReport> selectByExampleWithRowbounds(MemberReportExample example, RowBounds rowBounds);

    List<MemberReport> selectByExample(MemberReportExample example);

    MemberReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberReport record, @Param("example") MemberReportExample example);

    int updateByExample(@Param("record") MemberReport record, @Param("example") MemberReportExample example);

    int updateByPrimaryKeySelective(MemberReport record);

    int updateByPrimaryKey(MemberReport record);
}