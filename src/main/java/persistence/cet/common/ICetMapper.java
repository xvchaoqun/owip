package persistence.cet.common;

import bean.analysis.StatTrainBean;
import domain.cet.CetCourse;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTraineeType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICetMapper {

    // 培训计划的参训人类型
    @ResultMap("persistence.cet.CetTraineeTypeMapper.BaseResultMap")
    @Select("select  ctt.* from cet_project_trainee_type cptt, cet_trainee_type ctt where cptt.project_id=#{projectId}" +
            " and cptt.trainee_type_id = ctt.id order by ctt.sort_order asc")
    public List<CetTraineeType> getCetTraineeTypes(@Param("projectId") Integer projectId);

    // 学员的培训列表
    public List<CetProject> selectUserCetProjectList( @Param("userId") Integer userId,
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
                                                  @Param("isFinished") Byte isFinished,
                                                  RowBounds rowBounds);
    public int countUserCetTrainList(@Param("userId") Integer userId,
                                     @Param("hasSelected") Boolean hasSelected,
                                     @Param("isFinished") Byte isFinished);

    // 学员已选课程
    @ResultMap("persistence.cet.common.ICetMapper.ICetTrainCourseBaseResultMap")
    @Select("select ctc.*, cteec.can_quit from cet_trainee_course cteec, cet_train_course ctc " +
            "where cteec.trainee_id=#{traineeId} and cteec.train_course_id=ctc.id order by ctc.sort_order asc")
    public List<ICetTrainCourse> selectedCetTrainCourses(@Param("traineeId") Integer traineeId);

    // 学员未选课程
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("select * from cet_train_course where train_id=#{trainId} and " +
            "id not in(select train_course_id from cet_trainee_course where  trainee_id=#{traineeId}) order by sort_order asc")
    public List<CetTrainCourse> unSelectedCetTrainCourses(@Param("trainId") Integer trainId,
                                                             @Param("traineeId") Integer traineeId);

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
            "order by start_time asc"+ "</script>")
    public List<CetTrainCourse> getTodayTrainCourseList(@Param("trainId") Integer trainId);

    // 获取培训所包含的培训班（在培训方案下面的培训班，针对线下培训、实践教学）
    @ResultMap("persistence.cet.CetTrainMapper.BaseResultMap")
    @Select("select ct.* from cet_train ct, cet_project_plan cpp where  cpp.project_id=#{projectId} and ct.plan_id=cpp.id ")
    public List<CetTrain> getCetTrain( @Param("projectId") int projectId);

    // 获取培训班所在的培训计划
    @ResultMap("persistence.cet.CetProjectMapper.BaseResultMap")
    @Select("select cp.* from cet_project cp, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cp.id ")
    public CetProject getCetProject( @Param("trainId") int trainId);

    // 获取培训对象
    @ResultMap("persistence.cet.CetProjectObjMapper.BaseResultMap")
    @Select("select cpo.* from cet_project_obj cpo, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cpo.project_id " +
            " and cpo.user_id=#{userId}")
    public CetProjectObj getCetProjectObj(@Param("userId") int userId, @Param("trainId") int trainId);

    // 获取培训对象在一个培训方案中的已完成学时（针对线下培训和实践教学）
    @Select("select sum(finish_period) from cet_trainee_view where plan_id=#{planId} and obj_id=#{objId}")
    public BigDecimal getPlanFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    // 获取培训对象在一个培训方案中的已完成学时（针对自主学习）
    @Select("select sum(cc.period) from cet_plan_course_obj cpco " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "left join cet_course cc on cc.id=cpc.course_id " +
            "where cpc.plan_id=#{planId} and is_finished=1 and obj_id=#{objId}")
    public BigDecimal getSelfFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    // 获取培训对象在一个培训方案中的已完成学时（针对上级网上专题）
    @Select("select sum(cci.period) from cet_plan_course_obj_result cpcor " +
            "left join cet_course_item cci on cci.id=cpcor.course_item_id " +
            "left join cet_plan_course_obj cpco on cpco.id = cpcor.plan_course_obj_id " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "where cpc.plan_id=#{planId} and cpco.obj_id=#{objId} and cpco.is_finished=1")
    public BigDecimal getSpecialFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    // 获取培训对象在一个培训方案中的已完成学时（针对撰写心得体会）
    @Select("select cpp.period from cet_project_plan cpp " +
            "left join cet_project_obj cpo on cpo.project_id=cpp.project_id " +
            "where cpp.id=#{planId} and cpo.id=#{objId} and cpo.pdf_write is not null")
    public BigDecimal getWriteFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    /**
     select user_id, sum(period) as yearPeriod from cet_trainee_course_view cteecv
     where is_finished=1 and year=(select cp.year from cet_project cp, cet_project_plan cpp, cet_train ct
     where ct.id=25 and ct.plan_id=cpp.id and cpp.project_id=cp.id)
     and user_id in (select cpo.user_id from  cet_trainee ctee, cet_project_obj cpo
     where ctee.train_id=25 and ctee.obj_id=cpo.id)
     group by user_id;
     */
    // 获取某个培训班下面，每个参训人员的年度参加培训情况（年度参加培训的总学时数）
    @ResultType(java.util.HashMap.class)
    @Select("select user_id as userId, sum(period) as yearPeriod from cet_trainee_course_view cteecv  " +
            "where is_finished=1 and year=(select cp.year from cet_project cp, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cp.id) " +
            "and user_id in (select cpo.user_id from  cet_trainee ctee, cet_project_obj cpo " +
            "where ctee.train_id=#{trainId} and ctee.obj_id=cpo.id) group by user_id")
    public List<Map> listTraineeYearPeriod(@Param("trainId") int trainId);

    // 一个培训班内，每个参训人对每个课程的评价情况
    @ResultType(bean.analysis.StatTrainBean.class)
    @Select("select result.train_course_id as trainCourseId, result.inspector_id as inspectorId, sum(rank.score) as totalScore, ic.feedback " +
            "from cet_train_eva_result result, cet_train_eva_rank rank, cet_train_inspector_course ic " +
            "where result.train_id=#{trainId} and rank.id=result.rank_id and " +
            "ic.train_course_id=result.train_course_id and ic.inspector_id=result.inspector_id " +
            "group by result.inspector_id, result.train_course_id")
    public List<StatTrainBean> stat(@Param("trainId") int trainId);

}
