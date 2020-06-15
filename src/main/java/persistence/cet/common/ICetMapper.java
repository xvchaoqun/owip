package persistence.cet.common;

import domain.cet.*;
import domain.unit.Unit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICetMapper {

    // 根据账号、姓名、学工号查找学员
    List<CetProjectObj> selectObjList(@Param("projectId") int projectId,
                                      @Param("search") String search, RowBounds rowBounds);
    int countObjList(@Param("projectId") int projectId, @Param("search") String search);


    // 获取个人的进入年度学习档案的所有年度
    @Select("select trainee_type_id, count(*) as num from cet_project_obj " +
            "where project_id=#{projectId} and is_quit=#{isQuit} group by trainee_type_id ")
    public List<Map> projectObj_typeCount(@Param("projectId") int projectId, @Param("isQuit") boolean isQuit);

    // 获取个人的进入年度学习档案的所有年度
    @Select("select year from cet_annual_obj where user_id=#{userId} order by year desc")
    public List<Integer> getAnnualYears(@Param("userId") Integer userId);

    // 获取个人的年度学习档案
    @ResultMap("persistence.cet.CetAnnualObjMapper.BaseResultMap")
    @Select("select * from cet_annual_obj where user_id=#{userId} and year = #{year}")
    public CetAnnualObj getCetAnnualObj(@Param("userId") Integer userId, @Param("year") Integer year);

    // 设定年度学习任务
    @Update("update cet_annual_obj set period_offline=#{periodOffline}, period_online=#{periodOnline} where id in(${ids})")
    public void batchRequire(@Param("periodOffline") BigDecimal periodOffline,
                             @Param("periodOnline") BigDecimal periodOnline,
                             @Param("ids") String ids);

    // 按类型读取完成学时数
    public BigDecimal totalFinishPeriod(@Param("year") int year,
                                        @Param("userId") int userId,
                                        @Param("traineeTypeId") Integer traineeTypeId,
                                        @Param("type") Byte type);

    // 按类型读取完成学时数（网络）
    public BigDecimal totalOnlinePeriod(@Param("year") int year,
                                        @Param("userId") int userId,
                                        @Param("traineeTypeId") Integer traineeTypeId,
                                        @Param("type") Byte type);

    // 上级培训单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from cet_upper_train_admin cuta, " +
            "unit u where cuta.unit_id=u.id")
    public List<Unit> findUpperUnits();

    // 培训计划的参训人类型
    @ResultMap("persistence.cet.CetTraineeTypeMapper.BaseResultMap")
    @Select("select  ctt.* from cet_project_trainee_type cptt, cet_trainee_type ctt where cptt.project_id=#{projectId}" +
            " and cptt.trainee_type_id = ctt.id order by ctt.sort_order asc")
    public List<CetTraineeType> getCetTraineeTypes(@Param("projectId") Integer projectId);

    // 学员列表
    @Select("select id from cet_project_obj where project_id=#{projectId}")
    public List<Integer> getCetProjectObjIds(@Param("projectId") Integer projectId);

    // 专题培训 - 已选课学员列表
    @Select("select distinct user_id from cet_trainee_course_view where project_id=#{projectId}")
    public List<Integer> getCetProjectHasApplyUserIds(@Param("projectId") Integer projectId);

    // 学员的培训列表
    public List<CetProject> selectUserCetProjectList(@Param("userId") Integer userId,
                                                     @Param("projectType") byte projectType,
                                                     @Param("year") Integer year,
                                                     @Param("name") String name, RowBounds rowBounds);

    public int countUserCetProjectList(@Param("userId") Integer userId,
                                       @Param("projectType") byte projectType,
                                       @Param("year") Integer year,
                                       @Param("name") String name);


    // 学员的培训班列表
    public List<ICetTrain> selectUserCetTrainList(@Param("userId") Integer userId,
                                                  @Param("hasSelected") Boolean hasSelected,
                                                  @Param("isFinished") Boolean isFinished,
                                                  RowBounds rowBounds);

    public int countUserCetTrainList(@Param("userId") Integer userId,
                                     @Param("hasSelected") Boolean hasSelected,
                                     @Param("isFinished") Boolean isFinished);

    // 学员已选课程
    @ResultMap("persistence.cet.common.ICetMapper.ICetTrainCourseBaseResultMap")
    @Select("select ctc.*, cteec.can_quit, cteec.is_finished from cet_trainee_course cteec, cet_train_course ctc " +
            "where cteec.trainee_id=#{traineeId} and cteec.train_course_id=ctc.id order by ctc.sort_order asc")
    public List<ICetTrainCourse> selectedCetTrainCourses(@Param("traineeId") Integer traineeId);

    // 学员未选课程
    @ResultMap("persistence.cet.CetTrainCourseViewMapper.BaseResultMap")
    @Select("select * from cet_train_course_view ctc where ctc.train_id=#{trainId} and " +
            " not exists(select 1 from cet_trainee_course where train_course_id=ctc.id and trainee_id=#{traineeId}) order by ctc.sort_order asc")
    public List<CetTrainCourseView> unSelectedCetTrainCourses(@Param("trainId") Integer trainId,
                                                              @Param("traineeId") Integer traineeId);

    // 已选课学员
    @Select("select user_id from cet_trainee_course_view where train_course_id=#{trainCourseId} order by choose_time asc")
    public List<Integer> applyUserIds(@Param("trainCourseId") Integer trainCourseId);

    // 培训班已选课学员数量
    @Select("select count(distinct cteec.trainee_id) from cet_trainee_course cteec, cet_trainee ctee, cet_train_course ctc \n" +
            "where cteec.trainee_id=ctee.id and cteec.train_course_id=ctc.id and ctc.train_id=#{trainId}")
    public int traineeCount(@Param("trainId") Integer trainId);

    // 已分组学员
    public List<Integer> groupUserIds(@Param("discussGroupId") int discussGroupId,
                                      @Param("isFinished") Boolean isFinished);

    // 已完成学员（自主学习、上级网上专题班）
    public List<Integer> finishUserIds(@Param("planCourseId") Integer planCourseId,
                                       @Param("isFinished") Boolean isFinished);

    // 未选课学员
    public List<Integer> notApplyUserIds(@Param("projectId") Integer projectId,
                                         @Param("trainCourseIds") Integer[] trainCourseIds);

    // 未上传心得体会学员
    public List<Integer> notUploadWriteUserIds(@Param("projectId") Integer projectId,
                                               @Param("objIds") Integer[] objIds);

    // 培训对象参与的讨论组
    @ResultMap("persistence.cet.common.ICetMapper.ICetDiscussGroupBaseResultMap")
    @Select("select cdg.*, cd.period, cd.plan_id, cpo.user_id, cdgo.is_finished from cet_discuss_group_obj cdgo " +
            "left join cet_project_obj cpo on cpo.id=cdgo.obj_id " +
            "left join cet_discuss cd on cd.id=cdgo.discuss_id " +
            "left join cet_discuss_group cdg on cdgo.discuss_group_id=cdg.id " +
            "where cd.plan_id=#{planId} and cpo.user_id=#{userId} order by cd.sort_order asc, cdg.sort_order asc")
    public List<ICetDiscussGroup> userDiscussGroup(@Param("planId") Integer planId, @Param("userId") Integer userId);

    // 培训班 选择 课程
    List<CetCourse> selectCetTrainCourseList(@Param("trainId") int trainId,
                                             @Param("expertId") Integer expertId,
                                             @Param("name") String name,
                                             @Param("courseTypes") Byte[] courseTypes,
                                             RowBounds rowBounds);

    int countCetTrainCourseList(@Param("trainId") int trainId,
                                @Param("expertId") Integer expertId,
                                @Param("name") String name,
                                @Param("courseTypes") Byte[] courseTypes);

    // 培训方案 选择 课程
    List<CetCourse> selectCetPlanCourseList(@Param("planId") int planId,
                                            @Param("name") String name,
                                            @Param("courseTypes") Byte[] courseTypes,
                                            RowBounds rowBounds);

    int countCetPlanCourseList(@Param("planId") int planId,
                               @Param("name") String name,
                               @Param("courseTypes") Byte[] courseTypes);

    // 获取参训人员第二天的第一堂课
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("select ctc.* from cet_train_course ctc, cet_trainee_course cteec " +
            "where ctc.id = cteec.train_course_id and cteec.trainee_id=#{traineeId} and " +
            "left(ctc.start_time, 10) = date_add(curdate(),interval 1 day) " +
            "order by ctc.start_time asc limit 1")
    public CetTrainCourse getTomorrowFirstCourse(@Param("traineeId") int traineeId);

    // 获取参训人员当天还未开课的第一堂课
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("select ctc.* from cet_train_course ctc, cet_trainee_course cteec " +
            "where ctc.id = cteec.train_course_id and cteec.trainee_id=#{traineeId} and " +
            "left(ctc.start_time, 10) = curdate() and ctc.start_time > now() " +
            "order by ctc.start_time asc limit 1")
    public CetTrainCourse getTodayFirstCourse(@Param("traineeId") int traineeId);

    // 获取当天还未开课课程
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("<script>" + "select * from cet_train_course " +
            "where <if test='trainId!=null'> train_id=#{trainId} and</if> left(start_time, 10) = curdate() and start_time > now() " +
            "order by start_time asc" + "</script>")
    public List<CetTrainCourse> getTodayTrainCourseList(@Param("trainId") Integer trainId);

    // 获取培训所包含的培训班（在培训方案下面的培训班，针对线下培训、实践教学）
    @ResultMap("persistence.cet.CetTrainMapper.BaseResultMap")
    @Select("select ct.* from cet_train ct, cet_project_plan cpp where  cpp.project_id=#{projectId} and ct.plan_id=cpp.id ")
    public List<CetTrain> getCetTrain(@Param("projectId") int projectId);

    // 获取培训班所在的培训计划
    @ResultMap("persistence.cet.CetProjectMapper.BaseResultMap")
    @Select("select cp.* from cet_project cp, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cp.id ")
    public CetProject getCetProject(@Param("trainId") int trainId);

    // 获取分组讨论所在的培训计划
    @ResultMap("persistence.cet.CetProjectMapper.BaseResultMap")
    @Select("select cp.* from cet_project cp, cet_project_plan cpp, cet_discuss cd , cet_discuss_group cdg " +
            "where cdg.id=#{discussGroupId} and cdg.discuss_id=cd.id and cd.plan_id=cpp.id and cpp.project_id=cp.id ")
    public CetProject getCetProjectOfDiscussGroup(@Param("discussGroupId") int discussGroupId);

    // 获取培训对象
    @ResultMap("persistence.cet.CetProjectObjMapper.BaseResultMap")
    @Select("select cpo.* from cet_project_obj cpo, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cpo.project_id " +
            " and cpo.user_id=#{userId}")
    public CetProjectObj getCetProjectObj(@Param("userId") int userId, @Param("trainId") int trainId);

    // 获取培训对象在一个培训方案中的已完成学时（针对线下培训、线上培训和实践教学）
    @Select("select sum(ctv.finish_period) from cet_trainee_view ctv " +
            "LEFT JOIN cet_project cp ON ctv.project_id=cp.id " +
            "where cp.is_valid=1 and ctv.plan_id=#{planId} and ctv.obj_id=#{objId}")
    public BigDecimal getProjectPlanFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    // 获取培训对象在每年或某个培训项目中的培训方案中的已完成学时（针对线下培训、线上培训和实践教学）
    public BigDecimal getPlanFinishPeriod(@Param("type") byte planType,
                                          @Param("userId") int userId,
                                          @Param("year") Integer year,
                                          @Param("projectId") Integer projectId);

    @Select("select obj_id as objId, sum(finish_period) as period from cet_trainee_view where plan_id=#{planId} group by obj_id")
    public List<FinishPeriodBean> getPlanFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对自主学习）
    @Select("select sum(cc.period) from cet_plan_course_obj cpco " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "left join cet_course cc on cc.id=cpc.course_id " +
            "where cpc.plan_id=#{planId} and is_finished=1 and obj_id=#{objId}")
    public BigDecimal getSelfFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    @Select("select obj_id as objId, sum(cc.period) as period from cet_plan_course_obj cpco " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "left join cet_course cc on cc.id=cpc.course_id " +
            "where cpc.plan_id=#{planId} and is_finished=1 group by obj_id")
    public List<FinishPeriodBean> getSelfFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对分组研讨）
    @Select("select sum(cd.period) from cet_discuss_group_obj cdgo " +
            "left join cet_discuss cd on cd.id=cdgo.discuss_id " +
            "where cd.plan_id=#{planId} and cdgo.obj_id=#{objId} and cdgo.is_finished=1")
    public BigDecimal getGroupFinishPeriod(@Param("planId") int planId,
                                           @Param("objId") int objId);

    @Select("select cdgo.obj_id as objId, sum(cd.period) as period from cet_discuss_group_obj cdgo " +
            "left join cet_discuss cd on cd.id=cdgo.discuss_id " +
            "where cd.plan_id=#{planId} and cdgo.is_finished=1 group by cdgo.obj_id")
    public List<FinishPeriodBean> getGroupFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对上级网上专题）
    @Select("select sum(cci.period) from cet_plan_course_obj_result cpcor " +
            "left join cet_course_item cci on cci.id=cpcor.course_item_id " +
            "left join cet_plan_course_obj cpco on cpco.id = cpcor.plan_course_obj_id " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "where cpc.plan_id=#{planId} and cpco.obj_id=#{objId} and cpco.is_finished=1")
    public BigDecimal getSpecialPlanFinishPeriod(@Param("planId") int planId,
                                             @Param("objId") int objId);

    // 获取培训对象在每年或某培训项目中的培训方案中的已完成学时（针对上级网上专题）
    public BigDecimal getSpecialFinishPeriod(@Param("type") byte planType,
                                             @Param("userId") int userId,
                                             @Param("year") Integer year,
                                             @Param("projectId") Integer projectId);

    @Select("select cpco.obj_id as objId, sum(cci.period) as period from cet_plan_course_obj_result cpcor " +
            "left join cet_course_item cci on cci.id=cpcor.course_item_id " +
            "left join cet_plan_course_obj cpco on cpco.id = cpcor.plan_course_obj_id " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "where cpc.plan_id=#{planId} and cpco.is_finished=1 group by cpco.obj_id")
    public List<FinishPeriodBean> getSpecialFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对撰写心得体会）
    @Select("select cpp.period from cet_project_plan cpp " +
            "left join cet_project_obj cpo on cpo.project_id=cpp.project_id " +
            "where cpp.id=#{planId} and cpo.id=#{objId} and cpo.write_file_path is not null")
    public BigDecimal getWriteFinishPeriod(@Param("planId") int planId,
                                           @Param("objId") int objId);

    @Select("select cpo.id as objId, cpp.period from cet_project_plan cpp " +
            "left join cet_project_obj cpo on cpo.project_id=cpp.project_id " +
            "where cpp.id=#{planId} and cpo.write_file_path is not null group by cpo.id")
    public List<FinishPeriodBean> getWriteFinishPeriods(@Param("planId") int planId);

    /**
     * select user_id, sum(period) as yearPeriod from cet_trainee_course_view cteecv
     * where is_finished=1 and year=(select cp.year from cet_project cp, cet_project_plan cpp, cet_train ct
     * where ct.id=25 and ct.plan_id=cpp.id and cpp.project_id=cp.id)
     * and user_id in (select cpo.user_id from  cet_trainee ctee, cet_project_obj cpo
     * where ctee.train_id=25 and ctee.obj_id=cpo.id)
     * group by user_id;
     */
    // 获取某个培训班下面，每个参训人员的年度参加培训情况（年度参加培训的总学时数）
    @Select("select user_id as userId, sum(period) as yearPeriod from cet_trainee_course_view cteecv  " +
            "where is_finished=1 and year=(select cp.year from cet_project cp, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cp.id) " +
            "and user_id in (select cpo.user_id from  cet_trainee ctee, cet_project_obj cpo " +
            "where ctee.train_id=#{trainId} and ctee.obj_id=cpo.id) group by user_id")
    public List<Map> listTraineeYearPeriod(@Param("trainId") int trainId);

    // 一个培训班内，每个参训人对每个课程的评价情况
    @Select("select result.train_course_id as trainCourseId, result.inspector_id as inspectorId, sum(rank.score) as totalScore, ic.feedback " +
            "from cet_train_eva_result result, cet_train_eva_rank rank, cet_train_inspector_course ic " +
            "where result.train_id=#{trainId} and rank.id=result.rank_id and " +
            "ic.train_course_id=result.train_course_id and ic.inspector_id=result.inspector_id " +
            "group by result.inspector_id, result.train_course_id")
    public List<StatTrainBean> stat(@Param("trainId") int trainId);

    // 二级党委培训数量统计
    List<Map> unitProjectGroupByStatus(@Param("addPermits") Boolean addPermits,
                                 @Param("adminPartyIdList") List<Integer> adminPartyIdList);

    // 二级党委参训人员数量统计
    List<Map> unitTrainGroupByStatus(@Param("addPermits") Boolean addPermits,
                                       @Param("adminPartyIdList") List<Integer> adminPartyIdList);
    // 二级党委参训人员补录数量统计
    Integer unitTrainReRecord(@Param("addPermits") Boolean addPermits,
                                     @Param("adminPartyIdList") List<Integer> adminPartyIdList);

    // 获取用户管理的二级党委列表
    @ResultMap("persistence.cet.CetPartyMapper.BaseResultMap")
    @Select("select p.* from cet_party p, cet_party_admin a where a.user_id=#{userId} and a.cet_party_id=p.id and p.is_deleted=0")
    List<CetParty> getAdminParties(int userId);
    // 获取用户管理的二级党委ID列表
    @Select("select p.id from cet_party p, cet_party_admin a where a.user_id=#{userId} and a.cet_party_id=p.id and p.is_deleted=0")
    List<Integer> getAdminPartyIds(int userId);
}
