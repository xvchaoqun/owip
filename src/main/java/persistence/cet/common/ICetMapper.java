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

    // 刷新参训人员数量
    @Update("update cet_project p left join" +
            "(select project_id, count(*) as obj_count from cet_project_obj group by project_id) tmp on p.id=tmp.project_id " +
            "set p.obj_count=tmp.obj_count where p.id=#{projectId}")
    int refreshObjCount(@Param("projectId") Integer projectId);

    // 刷新已退出参训人员数量
    @Update("update cet_project p " +
            "left join (select project_id, count(*) as quit_count from cet_project_obj where is_quit=1 group by project_id) tmp on p.id=tmp.project_id " +
            "set p.quit_count=tmp.quit_count where p.id=#{projectId}")
    int refreshQuitCount(@Param("projectId") Integer projectId);

    // 按类别读取参训人数量
    @Select("select trainee_type_id, count(*) as num from cet_project_obj " +
            "where project_id=#{projectId} and is_quit=#{isQuit} group by trainee_type_id ")
    List<Map> projectObj_typeCount(@Param("projectId") int projectId, @Param("isQuit") boolean isQuit);

    // 刷新课件数量
    @Update("update cet_project p left join " +
            "(select project_id, count(*) as file_count from cet_project_file where project_id=#{projectId} group by project_id) tmp " +
            "on p.id=tmp.project_id set p.file_count=tmp.file_count where p.id=#{projectId}")
    int refreshFileCount(@Param("projectId") Integer projectId);

    // 获取个人的进入年度学习档案的所有年度
    @Select("select distinct cao.year, cao.trainee_type_id as traineeTypeId " +
            "from cet_annual_obj cao, cet_trainee_type ctt where cao.trainee_type_id=ctt.id " +
            "and cao.user_id=#{userId} order by cao.year desc, ctt.sort_order asc")
    List<Map> getYearTraineeTypeIds(@Param("userId") Integer userId);

    // 获取个人的年度学习档案
    @ResultMap("persistence.cet.CetAnnualObjMapper.BaseResultMap")
    @Select("select * from cet_annual_obj where user_id=#{userId} and year = #{year} and trainee_type_id=#{traineeTypeId}")
    CetAnnualObj getCetAnnualObj(@Param("userId") int userId, @Param("year") int year, @Param("traineeTypeId") int traineeTypeId);

    // 设定年度学习任务
    @Update("update cet_annual_obj set period_offline=#{periodOffline}, period_online=#{periodOnline} where id in(${ids})")
    void batchRequire(@Param("periodOffline") BigDecimal periodOffline,
                             @Param("periodOnline") BigDecimal periodOnline,
                             @Param("ids") String ids);

    // 批量删除无效的培训记录（假删除，无效指已删除或未审批或退出学习的记录）
    int removeDeletedCetRecords();

    // 没有删除的、审批通过的、还未归档的上级调训记录ID
    @Select("select ut.id from cet_upper_train ut " +
            "left join cet_record r on ut.id=r.source_id and r.source_type =1 " +
            "where ut.is_deleted=0 and ut.status=1 and (ut.update_time > r.archive_time or r.id is null)")
    List<Integer> getUnArchiveUpperTrainIds();

    // 已报送的、审批通过的、还未归档的二级党委培训记录
    @Select("select ut.id from cet_unit_train ut " +
            "left join cet_unit_project up on up.id=ut.project_id " +
            "left join cet_record r on ut.id=r.source_id and r.source_type =3 " +
            "where up.status=2 and ut.status=0 and (ut.update_time > r.archive_time or r.id is null)")
    List<Integer> getUnArchiveUnitTrainIds();

    // 未退出的、还未归档的记录党校过程培训记录
    List<Integer> getUnArchiveProjectObjIds(@Param("projectId") Integer projectId);

    @Update("update  cet_project_obj set update_time = now() where project_id=#{projectId}")
    void refreshProjectObjs(@Param("projectId") int projectId);

    // 获取最大编码（为了获取不重复编码，此处包含已删除的记录）
    @Select("select max(cert_no)+1 from cet_record " +
            "where year=#{year} and user_type=#{userType} " +
            "and special_type=#{specialType} and project_type=#{projectType}")
    Short getNextCertNo(@Param("year") int year,
                         @Param("userType") byte userType,
                         @Param("specialType") byte specialType,
                         @Param("projectType") int projectType);

    @Update("update cet_record set cert_no=#{certNo} where id=#{recordId} and not exists" +
            "(select * from (select cert_no from cet_record where year=#{year} and user_type=#{userType} " +
            "and special_type=#{specialType} and project_type=#{projectType} and cert_no=#{certNo}) tmp)")
    int updateCertNo(@Param("recordId") int recordId, @Param("certNo") int certNo,
                         @Param("year") int year,
                         @Param("userType") byte userType,
                         @Param("specialType") byte specialType,
                         @Param("projectType") int projectType);

    // 按类型读取完成学时数
    BigDecimal totalFinishPeriod(@Param("year") int year,
                                        @Param("userId") int userId,
                                        @Param("traineeTypeId") Integer traineeTypeId,
                                        @Param("type") Byte type);

    // 按类型读取完成学时数（网络）
    BigDecimal totalOnlinePeriod(@Param("year") int year,
                                        @Param("userId") int userId,
                                        @Param("traineeTypeId") Integer traineeTypeId,
                                        @Param("type") Byte type);

    // 上级培训单位
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from cet_upper_train_admin cuta, " +
            "unit u where cuta.unit_id=u.id")
    List<Unit> findUpperUnits();

    // 培训项目的参训人类型
    @ResultMap("persistence.cet.CetTraineeTypeMapper.BaseResultMap")
    @Select("select ctt.* from cet_project p, cet_trainee_type ctt " +
            "where find_in_set(ctt.id, p.trainee_type_ids) and p.id=#{projectId}" +
            " order by ctt.sort_order asc")
    List<CetTraineeType> getCetTraineeTypes(@Param("projectId") Integer projectId);

    // 学员列表
    @Select("select id from cet_project_obj where project_id=#{projectId}")
    List<Integer> getCetProjectObjIds(@Param("projectId") Integer projectId);

    // 专题培训 - 已选课学员列表
    @Select("select distinct user_id from cet_train_obj_view where project_id=#{projectId}")
    List<Integer> getCetProjectHasApplyUserIds(@Param("projectId") Integer projectId);

    // 学员的培训列表
    List<CetProject> selectCetProjectList(@Param("userId") Integer userId,
                                                     @Param("projectType") byte projectType,
                                                     @Param("year") Integer year,
                                                     @Param("name") String name, RowBounds rowBounds);

    int countCetProjectList(@Param("userId") Integer userId,
                                       @Param("projectType") byte projectType,
                                       @Param("year") Integer year,
                                       @Param("name") String name);

    // 学员的可选课的党校培训班列表
    List<ICetTrain> selectUserCetTrainList(@Param("userId") Integer userId, RowBounds rowBounds);
    int countUserCetTrainList(@Param("userId") Integer userId);

    // 学员的可选课的二级党委培训班列表
    List<ICetProject> selectUserCetProjectList(@Param("userId") Integer userId, RowBounds rowBounds);
    int countUserCetProjectList(@Param("userId") Integer userId);

    // 学员已选课程
    List<ICetTrainCourse> selectedCetTrainCourses(@Param("trainId") Integer trainId,
                                                  @Param("projectId") Integer projectId,
                                                  @Param("userId") int userId);

    // 学员未选课程
    List<CetTrainCourse> unSelectedCetTrainCourses(@Param("trainId") Integer trainId,
                                                   @Param("projectId") Integer projectId, @Param("userId") int userId);

    // 已选课学员
    @Select("select user_id from cet_train_obj_view where train_course_id=#{trainCourseId} order by choose_time asc")
    List<Integer> applyUserIds(@Param("trainCourseId") Integer trainCourseId);

    // 更新培训课程 已选课人数 和 签到人数
    @Update("update cet_train_course ctc " +
            "left join " +
            "(select train_course_id, count(id) as selected_count, sum(if(is_finished, 1,0)) as finish_count " +
            "from  cet_train_obj group by train_course_id)tmp on tmp.train_course_id=ctc.id " +
            "set ctc.selected_count=tmp.selected_count, ctc.finish_count = tmp.finish_count where ctc.id=#{trainCourseId}")
    int refreshTrainCourseSelectedCount(@Param("trainCourseId") Integer trainCourseId);

    // 更新培训课程 已评课人数
    @Update("update cet_train_course ctc left join " +
            "(select train_course_id, count(id) as eva_finish_count from cet_train_inspector_course " +
            "group by train_course_id)tmp on tmp.train_course_id=ctc.id " +
            "set ctc.eva_finish_count=tmp.eva_finish_count where ctc.id=#{trainCourseId}")
    int refreshTrainCourseEvaCount(@Param("trainCourseId") Integer trainCourseId);

    // 培训班已选课学员数量
    @Select("select count(distinct user_id) from cet_train_obj where train_id=#{trainId}")
    int traineeCount(@Param("trainId") Integer trainId);

    // 某培训方案中学员已选的培训班
    @Select("select distinct train_id from cet_train_obj_view where user_id=#{userId} and plan_id=#{planId}")
    List<Integer> selectUserTrainIds(@Param("userId") Integer userId, @Param("planId") Integer planId);
    @Select("select distinct train_id from cet_train_obj_view where obj_id=#{objId} and plan_id=#{planId}")
    List<Integer> selectObjTrainIds(@Param("objId") Integer objId, @Param("planId") Integer planId);

    // 已分组学员
    List<Integer> groupUserIds(@Param("discussGroupId") int discussGroupId,
                                      @Param("isFinished") Boolean isFinished);

    // 已完成学员（自主学习、上级网上专题班）
    List<Integer> finishUserIds(@Param("planCourseId") Integer planCourseId,
                                       @Param("isFinished") Boolean isFinished);

    // 未选课学员
    List<Integer> notApplyUserIds(@Param("projectId") Integer projectId,
                                         @Param("trainCourseIds") Integer[] trainCourseIds);

    // 未上传心得体会学员
    List<Integer> notUploadWriteUserIds(@Param("projectId") Integer projectId,
                                               @Param("objIds") Integer[] objIds);

    // 培训对象参与的讨论组
    @ResultMap("persistence.cet.common.ICetMapper.ICetDiscussGroupBaseResultMap")
    @Select("select cdg.*, cd.period, cd.plan_id, cpo.user_id, cdgo.is_finished from cet_discuss_group_obj cdgo " +
            "left join cet_project_obj cpo on cpo.id=cdgo.obj_id " +
            "left join cet_discuss cd on cd.id=cdgo.discuss_id " +
            "left join cet_discuss_group cdg on cdgo.discuss_group_id=cdg.id " +
            "where cd.plan_id=#{planId} and cpo.user_id=#{userId} order by cd.sort_order asc, cdg.sort_order asc")
    List<ICetDiscussGroup> userDiscussGroup(@Param("planId") Integer planId, @Param("userId") Integer userId);

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

    // 获取当天还未开课课程
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("<script>" + "select * from cet_train_course " +
            "where <if test='trainId!=null'> train_id=#{trainId} and</if> left(start_time, 10) = curdate() and start_time > now() " +
            "order by start_time asc" + "</script>")
    List<CetTrainCourse> getTodayTrainCourseList(@Param("trainId") Integer trainId);

    // 更新培训项目总学时: 删除培训方案时调用
    void updateProjectTotalPeriodByPlan(@Param("projectId") int projectId);

    // 更新培训项目总学时: 二级党委过程培训时调用
    void updateProjectTotalPeriodByCourse(@Param("projectId") int projectId);

    // 更新培训方案总学时：分组研讨
    void updateDiscussTotalPeriod(@Param("planId") int planId);

    // 更新培训方案总学时：自主学习、上级网上专题
    void updatePlanCourseTotalPeriod(@Param("planId") int planId);

    // 更新培训方案总学时：线下、线上、实践教学 （删除培训班时也调用）
    void updateTrainCourseTotalPeriod(@Param("planId") int planId);

    // 更新培训班课程数量
    @Update("update cet_train ct left join (select train_id, count(*) num " +
            "from cet_train_course group by train_id) tmp on tmp.train_id=ct.id " +
            "set ct.course_num=tmp.num where ct.id=#{trainId}")
    void updateTrainCourseNum(@Param("trainId") int trainId);

    // 获取培训所包含的培训班（在培训方案下面的培训班，针对线下培训、实践教学）
    @ResultMap("persistence.cet.CetTrainMapper.BaseResultMap")
    @Select("select ct.* from cet_train ct, cet_project_plan cpp where  cpp.project_id=#{projectId} and ct.plan_id=cpp.id ")
    List<CetTrain> getCetTrain(@Param("projectId") int projectId);

    // 获取培训班所在的培训项目
    @ResultMap("persistence.cet.CetProjectMapper.BaseResultMap")
    @Select("select cp.* from cet_project cp, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cp.id ")
    CetProject getCetProject(@Param("trainId") int trainId);

    // 获取分组讨论所在的培训项目
    @ResultMap("persistence.cet.CetProjectMapper.BaseResultMap")
    @Select("select cp.* from cet_project cp, cet_project_plan cpp, cet_discuss cd , cet_discuss_group cdg " +
            "where cdg.id=#{discussGroupId} and cdg.discuss_id=cd.id and cd.plan_id=cpp.id and cpp.project_id=cp.id ")
    CetProject getCetProjectOfDiscussGroup(@Param("discussGroupId") int discussGroupId);

    // 获取培训对象在一个培训方案中的已完成学时（针对线下培训、线上培训和实践教学、二级党委培训（planId=null)）
    BigDecimal getTrainObjFinishPeriod(@Param("planId") Integer planId,
                                       @Param("objId") int objId);

    // 获取培训对象在每年或某个培训项目中的培训方案中的已完成学时（针对线下培训、线上培训和实践教学）
    BigDecimal getPlanFinishPeriod(@Param("type") byte planType,
                                          @Param("userId") int userId,
                                          @Param("year") Integer year,
                                          @Param("projectId") Integer projectId);

    // 获取培训对象已完成的网络学时（针对二级党委过程培训）
    BigDecimal getOnlineFinishPeriod(@Param("userId") int userId, @Param("projectId") int projectId);

    @Select("select obj_id as objId, sum(if(is_finished, period, 0)) as period from cet_train_obj_view where plan_id=#{planId} group by obj_id")
    List<FinishPeriodBean> getPlanFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对自主学习）
    @Select("select sum(cc.period) from cet_plan_course_obj cpco " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "left join cet_course cc on cc.id=cpc.course_id " +
            "where cpc.plan_id=#{planId} and is_finished=1 and obj_id=#{objId}")
    BigDecimal getSelfFinishPeriod(@Param("planId") int planId,
                                          @Param("objId") int objId);

    @Select("select obj_id as objId, sum(cc.period) as period from cet_plan_course_obj cpco " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "left join cet_course cc on cc.id=cpc.course_id " +
            "where cpc.plan_id=#{planId} and is_finished=1 group by obj_id")
    List<FinishPeriodBean> getSelfFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对分组研讨）
    @Select("select sum(cd.period) from cet_discuss_group_obj cdgo " +
            "left join cet_discuss cd on cd.id=cdgo.discuss_id " +
            "where cd.plan_id=#{planId} and cdgo.obj_id=#{objId} and cdgo.is_finished=1")
    BigDecimal getGroupFinishPeriod(@Param("planId") int planId,
                                           @Param("objId") int objId);

    @Select("select cdgo.obj_id as objId, sum(cd.period) as period from cet_discuss_group_obj cdgo " +
            "left join cet_discuss cd on cd.id=cdgo.discuss_id " +
            "where cd.plan_id=#{planId} and cdgo.is_finished=1 group by cdgo.obj_id")
    List<FinishPeriodBean> getGroupFinishPeriods(@Param("planId") int planId);

    // 获取培训对象在一个培训方案中的已完成学时（针对上级网上专题）
    @Select("select cpco.period from cet_plan_course_obj cpco" +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "where cpc.plan_id=#{planId} and cpco.obj_id=#{objId}")
    BigDecimal getSpecialPlanFinishPeriod(@Param("planId") int planId,
                                             @Param("objId") int objId);
    /*@Select("select sum(cci.period) from cet_plan_course_obj_result cpcor " +
            "left join cet_course_item cci on cci.id=cpcor.course_item_id " +
            "left join cet_plan_course_obj cpco on cpco.id = cpcor.plan_course_obj_id " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "where cpc.plan_id=#{planId} and cpco.obj_id=#{objId} and cpco.is_finished=1")
    BigDecimal getSpecialPlanFinishPeriod(@Param("planId") int planId,
                                             @Param("objId") int objId);*/

    // 获取课程总学时，针对上级网上专题
    @Select("select sum(cci.period) from cet_course cc " +
            "left join cet_course_item cci on cci.course_id=cc.id where cc.id=#{courseId}")
    BigDecimal getCoursePeriod(@Param("courseId") int courseId);

    // 获取培训对象在每年或某培训项目中的培训方案中的已完成学时（针对上级网上专题）
    BigDecimal getSpecialFinishPeriod(@Param("type") byte planType,
                                             @Param("userId") int userId,
                                             @Param("year") Integer year,
                                             @Param("projectId") Integer projectId);

 /*   @Select("select cpco.obj_id as objId, sum(cci.period) as period from cet_plan_course_obj_result cpcor " +
            "left join cet_course_item cci on cci.id=cpcor.course_item_id " +
            "left join cet_plan_course_obj cpco on cpco.id = cpcor.plan_course_obj_id " +
            "left join cet_plan_course cpc on cpc.id=cpco.plan_course_id " +
            "where cpc.plan_id=#{planId} and cpco.is_finished=1 group by cpco.obj_id")
    List<FinishPeriodBean> getSpecialFinishPeriods(@Param("planId") int planId);*/

    // 获取培训对象在一个培训方案中的已完成学时（针对撰写心得体会）
    @Select("select cpp.period from cet_project_plan cpp " +
            "left join cet_project_obj cpo on cpo.project_id=cpp.project_id " +
            "where cpp.id=#{planId} and cpo.id=#{objId} and cpo.write_file_path is not null")
    BigDecimal getWriteFinishPeriod(@Param("planId") int planId,
                                           @Param("objId") int objId);

    @Select("select cpo.id as objId, cpp.period from cet_project_plan cpp " +
            "left join cet_project_obj cpo on cpo.project_id=cpp.project_id " +
            "where cpp.id=#{planId} and cpo.write_file_path is not null group by cpo.id")
    List<FinishPeriodBean> getWriteFinishPeriods(@Param("planId") int planId);

    // 获取某个培训班下面，每个参训人员的年度参加培训情况（年度参加培训的总学时数）
    @Select("select user_id as userId, sum(period) as yearPeriod from cet_train_obj_view  " +
            "where year=(select cp.year from cet_project cp, cet_project_plan cpp, cet_train ct " +
            "where ct.id=#{trainId} and ct.plan_id=cpp.id and cpp.project_id=cp.id) " +
            "and train_id=#{trainId} group by user_id")
    List<Map> listTraineeYearPeriod(@Param("trainId") int trainId);

    // 一个培训班内，每个参训人对每个课程的评价情况
    @Select("select result.train_course_id as trainCourseId, result.inspector_id as inspectorId, sum(rank.score) as totalScore, ic.feedback " +
            "from cet_train_eva_result result, cet_train_eva_rank rank, cet_train_inspector_course ic " +
            "where result.train_id=#{trainId} and rank.id=result.rank_id and " +
            "ic.train_course_id=result.train_course_id and ic.inspector_id=result.inspector_id " +
            "group by result.inspector_id, result.train_course_id")
    List<StatTrainBean> stat(@Param("trainId") int trainId);

    // 二级党委过程培训数量统计
    List<Map> projectGroupByStatus(@Param("type") byte type, @Param("addPermits") Boolean addPermits,
                                 @Param("adminPartyIdList") List<Integer> adminPartyIdList);

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
