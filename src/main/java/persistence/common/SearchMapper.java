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

    @Select("select distinct nation from ow_member_student where nation is not null and nation!='' order by nation")
    List<String> studentNations();
    @Select("select distinct native_place from ow_member_student where native_place is not null and native_place!='' order by native_place")
    List<String> studentNativePlaces();

    @Select("select distinct nation from ow_member_teacher where nation is not null and nation!='' order by nation")
    List<String> teacherNations();
    @Select("select distinct native_place from ow_member_teacher where native_place is not null and native_place!='' order by native_place")
    List<String> teacherNativePlaces();

    @Select("select distinct education from ow_member_teacher where education is not null order by education asc")
    List<String> teacherEducationTypes();

    @Select("select distinct post_class from ow_member_teacher where post_class is not null order by post_class asc")
    List<String> teacherPostClasses();
}
