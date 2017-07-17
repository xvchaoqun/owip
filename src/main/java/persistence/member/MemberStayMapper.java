package persistence.member;

import domain.member.MemberStay;
import domain.member.MemberStayExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberStayMapper {
    long countByExample(MemberStayExample example);

    int deleteByExample(MemberStayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MemberStay record);

    int insertSelective(MemberStay record);

    List<MemberStay> selectByExampleWithRowbounds(MemberStayExample example, RowBounds rowBounds);

    List<MemberStay> selectByExample(MemberStayExample example);

    MemberStay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberStay record, @Param("example") MemberStayExample example);

    int updateByExample(@Param("record") MemberStay record, @Param("example") MemberStayExample example);

    int updateByPrimaryKeySelective(MemberStay record);

    int updateByPrimaryKey(MemberStay record);
}