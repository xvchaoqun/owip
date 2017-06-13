package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by fafa on 2015/11/16.
 */
public interface CommonMapper {

    @Select("select max(sort_order) from ${table} where ${whereSql}")
    Integer getMaxSortOrder(@Param("table") String table, @Param("whereSql") String whereSql);

    void downOrder(@Param("table") String table, @Param("whereSql") String whereSql,
                   @Param("baseSortOrder") int baseSortOrder,
                   @Param("targetSortOrder") int targetSortOrder);

    void upOrder(@Param("table") String table, @Param("whereSql") String whereSql,
                 @Param("baseSortOrder") int baseSortOrder,
                 @Param("targetSortOrder") int targetSortOrder);

    @Update("${sql}")
    void excuteSql(@Param("sql") String sql);

    // 年级
    @Select("select distinct grade from ow_member_student order by grade asc")
    List<String> studentGrades();
    // 学生类别
    @Select("select distinct type from ow_member_student where type is not null order by type asc")
    List<String> studentTypes();
    // 学生民族
    @Select("select distinct nation from ow_member_student where nation is not null and nation!='' order by nation")
    List<String> studentNations();
    // 学生籍贯
    @Select("select distinct native_place from ow_member_student where native_place is not null and native_place!='' order by native_place")
    List<String> studentNativePlaces();

    // 教师民族
    @Select("select distinct nation from ow_member_teacher where nation is not null and nation!='' order by nation")
    List<String> teacherNations();
    // 教师籍贯
    @Select("select distinct native_place from ow_member_teacher where native_place is not null and native_place!='' order by native_place")
    List<String> teacherNativePlaces();

    // 最高学历
    @Select("select distinct education from ow_member_teacher where education is not null and education!='' order by education asc")
    List<String> teacherEducationTypes();

    // 干部岗位类别
    @Select("select distinct post_class from cadre_view where post_class is not null and post_class!='' order by post_class asc")
    List<String> teacherPostClasses();
    // 干部专业技术职务
    @Select("select distinct pro_post from cadre_view where pro_post is not null and pro_post!='' order by pro_post asc")
    List<String> teacherProPosts();
    // 干部专技岗位等级
    @Select("select distinct pro_post_level from cadre_view where pro_post_level is not null and pro_post_level!='' order by pro_post_level asc")
    List<String> teacherProPostLevels();
}
