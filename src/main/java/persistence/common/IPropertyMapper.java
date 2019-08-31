package persistence.common;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lm on 2017/11/15.
 */
public interface IPropertyMapper {

    // 年级
    @Select("select distinct grade from ow_member_view where type=2 order by grade asc")
    List<String> studentGrades();

    // 学生类别
    @Select("select distinct student_type from ow_member_view where type=2 and student_type is not null order by student_type asc")
    List<String> studentTypes();

    // 民族
    @Select("select distinct nation from ow_member_view where nation is not null and nation!='' order by nation")
    List<String> nations();

    // 籍贯
    @Select("select distinct native_place from ow_member_view where native_place is not null and native_place!='' order by native_place")
    List<String> nativePlaces();

    // 学生民族
    @Select("select distinct nation from ow_member_view where type=2 and nation is not null and nation!='' order by nation")
    List<String> studentNations();

    // 学生籍贯
    @Select("select distinct native_place from ow_member_view where type=2 and native_place is not null and native_place!='' order by native_place")
    List<String> studentNativePlaces();

    // 教师民族
    @Select("select distinct nation from ow_member_view where type=1 and nation is not null and nation!='' order by nation")
    List<String> teacherNations();

    // 教师籍贯
    @Select("select distinct native_place from ow_member_view where type=1 and native_place is not null and native_place!='' order by native_place")
    List<String> teacherNativePlaces();

    // 最高学历
    @Select("select distinct education from ow_member_view where type=1 and education is not null and education!='' order by education asc")
    List<String> teacherEducationTypes();

    // 干部岗位类别
    @Select("select distinct post_class from cadre_view where post_class is not null and post_class!='' order by post_class asc")
    List<String> teacherPostClasses();

    // 干部专业技术职务
    @Select("select distinct pro_post from cadre_view where pro_post is not null and pro_post!='' order by pro_post asc")
    List<String> teacherProPosts();

    // 是否在职（人员状态）
    @Select("select distinct staff_status from cadre_view where staff_status is not null and staff_status!='' order by staff_status asc")
    List<String> staffStatuses();

    // 是否临时人员
    @Select("select distinct is_temp from cadre_view where is_temp is not null and is_temp!='' order by is_temp asc")
    List<String> isTemps();

    // 干部职称级别
    @Select("select distinct pro_post_level from cadre_view where pro_post_level is not null and pro_post_level!='' order by pro_post_level asc")
    List<String> teacherProPostLevels();
}
