package persistence.crs.common;

import domain.crs.CrsApplicant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICrsMapper {

    // 应聘人报名次数统计
    public List<CrsStatApplicantBean> selectStatApplicantList(@Param("applicantStatus") Byte applicantStatus,
                                                              @Param("postStatus") Byte postStatus,
                                                              RowBounds rowBounds);
    public int countStatApplicantList(@Param("applicantStatus") Byte applicantStatus,
                                      @Param("postStatus") Byte postStatus);

    // 推荐/自荐
    public int recommend(@Param("record") CrsApplicant record);

    // 获取专家列表，增加专家参与岗位数量、排序
    public List<ICrsExpert> selectExpertList(@Param("userId") Integer userId,
                                             @Param("status") Byte status,
                                             @Param("meetingTimeStart") Date meetingTimeStart,
                                             @Param("meetingTimeEnd") Date meetingTimeEnd,
                                             @Param("orderType") Byte orderType,
                                             RowBounds rowBounds);

    public int countExpertList(@Param("userId") Integer userId,
                               @Param("status") Byte status,
                               @Param("meetingTimeStart") Date meetingTimeStart,
                               @Param("meetingTimeEnd") Date meetingTimeEnd);

    // 岗位报名分类统计
    @Select("select count(*) as num, require_check_status, is_require_check_pass,is_quit " +
            "from crs_applicant_view where post_id=#{postId} and status = #{status} " +
            "group by require_check_status, is_require_check_pass, is_quit")
    public List<Map> applicantStatic(@Param("postId") Integer postId, @Param("status") Byte status);

    // 获取干部申请的岗位（crs_applicant.status=1）
    public List<ICrsPost> selectUserApplyCrsPostList(@Param("userId") Integer userId,
                                                     @Param("isQuit") Boolean isQuit,
                                                     @Param("postStatusList") List<Byte> postStatusList,
                                                     RowBounds rowBounds);

    public int countUserApplyCrsPostList(@Param("userId") Integer userId,
                                         @Param("isQuit") Boolean isQuit,
                                         @Param("postStatusList") List<Byte> postStatusList);

    // 已报名的岗位
    @Select("select post_id as postId, is_quit as isQuit from crs_applicant where user_id=#{userId} and status = #{status}")
    public List<Map> hasApplyPosts(@Param("userId") Integer userId, @Param("status") Byte status);

    // 可补报岗位
    @Select("select post_id from crs_apply_user where user_id=#{userId} and status=1 and now() between start_time and end_time")
    public List<Integer> canAfterApplyPostIds(@Param("userId") Integer userId);
}
