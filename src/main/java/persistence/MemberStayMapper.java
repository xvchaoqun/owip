package persistence;

import domain.MemberStay;
import domain.MemberStayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberStayMapper {
    int countByExample(MemberStayExample example);

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