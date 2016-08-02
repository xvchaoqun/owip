package persistence.member;

import domain.member.Teacher;
import domain.member.TeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TeacherMapper {
    int countByExample(TeacherExample example);

    int deleteByExample(TeacherExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    List<Teacher> selectByExampleWithRowbounds(TeacherExample example, RowBounds rowBounds);

    List<Teacher> selectByExample(TeacherExample example);

    Teacher selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") Teacher record, @Param("example") TeacherExample example);

    int updateByExample(@Param("record") Teacher record, @Param("example") TeacherExample example);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
}