package persistence.member;

import domain.member.MemberTeacher;
import domain.member.MemberTeacherExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberTeacherMapper {
    long countByExample(MemberTeacherExample example);

    List<MemberTeacher> selectByExampleWithRowbounds(MemberTeacherExample example, RowBounds rowBounds);

    List<MemberTeacher> selectByExample(MemberTeacherExample example);
}