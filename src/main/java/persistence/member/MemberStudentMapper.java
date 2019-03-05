package persistence.member;

import domain.member.MemberStudent;
import domain.member.MemberStudentExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface MemberStudentMapper {
    long countByExample(MemberStudentExample example);

    List<MemberStudent> selectByExampleWithRowbounds(MemberStudentExample example, RowBounds rowBounds);

    List<MemberStudent> selectByExample(MemberStudentExample example);
}