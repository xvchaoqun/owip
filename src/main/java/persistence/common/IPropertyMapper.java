package persistence.common;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lm on 2017/11/15.
 *
 * 此类中的方法不能直接调用，否则性能太差。请调用方法 CmTag.getPropertyCaches("方法名")
 */
public interface IPropertyMapper {

    // 年级
    @Select("select distinct s.grade from ow_member m, sys_student_info s where m.user_id=s.user_id and s.grade is not null and s.grade!='' order by s.grade asc")
    List<String> studentGrades();

    // 学生类别
    @Select("select distinct s.type from ow_member m, sys_student_info s where m.user_id=s.user_id and s.type is not null and s.type!='' order by s.type asc")
    List<String> studentTypes();

    // 籍贯
    @Select("select distinct native_place from ow_member_view where native_place is not null and native_place!='' order by native_place")
    List<String> nativePlaces();

    // 学生籍贯
    @Select("select distinct u.native_place from ow_member m, sys_user_info u, sys_student_info s where m.user_id=u.user_id and u.user_id=s.user_id and u.native_place is not null and u.native_place!='' order by u.native_place asc")
    List<String> studentNativePlaces();

    // 教师籍贯
    @Select("select distinct native_place from ow_member_view where user_type in(1,5) and native_place is not null and native_place!='' order by native_place")
    List<String> teacherNativePlaces();

    // 最高学历
    @Select("select distinct education from ow_member_view where user_type in(1,5) and education is not null and education!='' order by education asc")
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

    // 个人身份
    @Select("select distinct staff_type from cadre_view where staff_type is not null and staff_type!='' order by staff_type asc")
    List<String> staffTypes();

    // 编制类别
    @Select("select distinct authorized_type from cadre_view where authorized_type is not null and authorized_type!='' order by authorized_type asc")
    List<String> authorizedTypes();
}
