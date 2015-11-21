package persistence;

import domain.MemberStudent;
import domain.MemberStudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberStudentMapper {
    int countByExample(MemberStudentExample example);

    int deleteByExample(MemberStudentExample example);

    int insert(MemberStudent record);

    int insertSelective(MemberStudent record);

    List<MemberStudent> selectByExampleWithRowbounds(MemberStudentExample example, RowBounds rowBounds);

    List<MemberStudent> selectByExample(MemberStudentExample example);

    int updateByExampleSelective(@Param("record") MemberStudent record, @Param("example") MemberStudentExample example);

    int updateByExample(@Param("record") MemberStudent record, @Param("example") MemberStudentExample example);
}