package persistence.member;

import domain.member.MemberTeacher;
import domain.member.MemberTeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MemberTeacherMapper {
    int countByExample(MemberTeacherExample example);

    int deleteByExample(MemberTeacherExample example);

    int insert(MemberTeacher record);

    int insertSelective(MemberTeacher record);

    List<MemberTeacher> selectByExampleWithRowbounds(MemberTeacherExample example, RowBounds rowBounds);

    List<MemberTeacher> selectByExample(MemberTeacherExample example);

    int updateByExampleSelective(@Param("record") MemberTeacher record, @Param("example") MemberTeacherExample example);

    int updateByExample(@Param("record") MemberTeacher record, @Param("example") MemberTeacherExample example);
}