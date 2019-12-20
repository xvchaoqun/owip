package persistence.dp.common;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lm on 2019/7/29.
 */
public interface IDpPropertyMapper {

    //prcm民族
    @Select("select distinct nation from dp_pr_cm_view where nation is not null and nation!='' order by nation")
    List<String> prCmNations();
    //npm民族
    @Select("select distinct nation from dp_npm_view where nation is not null and nation!='' order by nation")
    List<String> npmNations();
    //om民族
    @Select("select distinct nation from dp_om_view where nation is not null and nation!='' order by nation")
    List<String> omNations();
    //npr民族
    @Select("select distinct nation from dp_npr_view where nation is not null and nation!='' order by nation")
    List<String> nprNations();

    // 籍贯
    @Select("select distinct native_place from dp_npm_view where native_place is not null and native_place!='' order by native_place")
    List<String> npmNativePlaces();

    // 所有民族
    @Select("select distinct nation from dp_member_view where nation is not null and nation!='' order by nation")
    List<String> nations();

    // 籍贯
    @Select("select distinct native_place from dp_member_view where native_place is not null and native_place!='' order by native_place")
    List<String> nativePlaces();

    // 学生民族
    @Select("select distinct nation from dp_member_view where type=2 and nation is not null and nation!='' order by nation")
    List<String> studentNations();

    // 学生籍贯
    @Select("select distinct native_place from dp_member_view where type=2 and native_place is not null and native_place!='' order by native_place")
    List<String> studentNativePlaces();

    // 教师民族
    @Select("select distinct nation from dp_member_view where type=1 and nation is not null and nation!='' order by nation")
    List<String> teacherNations();

    // 教师籍贯
    @Select("select distinct native_place from dp_member_view where type=1 and native_place is not null and native_place!='' order by native_place")
    List<String> teacherNativePlaces();

    // 教师行政级别
    @Select("select distinct admin_level from dp_member_view where type=1 and admin_level is not null and admin_level!='' order by admin_level")
    List<Integer> teacherAdminLevels();

    // 教师行政职务
    @Select("select distinct post from dp_member_view where type=1 and post is not null and post!='' order by post")
    List<String> teacherPosts();

    // 最高学历
    @Select("select distinct education from dp_member_view where type=1 and education is not null and education!='' order by education asc")
    List<String> teacherEducationTypes();

    // 最高学历
    @Select("select distinct type from dp_pr_cm_view where type is not null and type!='' order by type asc")
    List<String> prCmTypes();


    // 干部岗位类别
    @Select("select distinct post_class from cadre_view where post_class is not null and post_class!='' order by post_class asc")
    List<String> teacherPostClasses();

    // 干部专业技术职务
    @Select("select distinct pro_post from cadre_view where pro_post is not null and pro_post!='' order by pro_post asc")
    List<String> teacherProPosts();

    // 干部专技岗位等级
    @Select("select distinct pro_post_level from cadre_view where pro_post_level is not null and pro_post_level!='' order by pro_post_level asc")
    List<String> teacherProPostLevels();


    // 查询民主党派中党派成员中是干部的人的userId
    @Select("select distinct dp_member.user_id from dp_member,cadre where dp_member.user_id=cadre.user_id")
    List<Integer> findCadreFromdpMember();

    // 查询无党派人士中是干部的人的userId
    @Select("select distinct dp_npm.user_id from dp_npm,cadre where dp_npm.user_id=cadre.user_id")
    List<Integer> findCadreFromdpNpm();

    // 查询其他统战人员中是干部的人的userId
    @Select("select distinct dp_om.user_id from dp_om,cadre where dp_om.user_id=cadre.user_id")
    List<Integer> findCadreFromdpOm();

    // 查询党外代表人士中是干部的人的userId
    @Select("select distinct dp_npr.user_id from dp_npr,cadre where dp_npr.user_id=cadre.user_id")
    List<Integer> findCadreFromdpNpr();

    // 查询人大代表、政协委员中是干部的人的userId
    @Select("select distinct dp_pr_cm.user_id from dp_pr_cm,cadre where dp_pr_cm.user_id=cadre.user_id")
    List<Integer> findCadreFromdpPrCm();
}
