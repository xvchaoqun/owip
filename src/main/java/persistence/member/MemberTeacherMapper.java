package persistence.member;

import domain.member.MemberTeacher;
import domain.member.MemberTeacherExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MemberTeacherMapper {
    long countByExample(MemberTeacherExample example);

    List<MemberTeacher> selectByExampleWithRowbounds(MemberTeacherExample example, RowBounds rowBounds);

    List<MemberTeacher> selectByExample(MemberTeacherExample example);
}