package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fafa on 2016/5/3.
 */
public interface SearchMapper {

    @Select("select distinct grade from ow_member_student order by grade asc")
    List<String> studentGrades();

    @Select("select distinct type from ow_member_student where type is not null order by type asc")
    List<String> studentTypes();

    @Select("select distinct education from ow_member_teacher where education is not null order by education asc")
    List<String> teacherEducationTypes();

    @Select("select distinct post_class from ow_member_teacher where post_class is not null order by post_class asc")
    List<String> teacherPostClasses();
}
