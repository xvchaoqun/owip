package persistence.common;

import bean.analysis.StatTrainBean;
import domain.cet.CetCourse;
import domain.cet.CetTrainCourse;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import persistence.common.bean.ICetTrain;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICetMapper {

    // 培训班的参训人类型
    @ResultMap("persistence.cet.CetTraineeTypeMapper.BaseResultMap")
    @Select("select  ctt.* from cet_train_trainee_type cttt, cet_trainee_type ctt where cttt.train_id=#{trainId}" +
            " and cttt.trainee_type_id = ctt.id order by ctt.sort_order asc")
    public List<CetTraineeType> getCetTraineeTypes(@Param("trainId") Integer trainId);

    // 参训人的培训班列表
    public List<ICetTrain> findUserCetTrains(@Param("userId") Integer userId,
                                             @Param("hasSelected")Boolean hasSelected,
                                             @Param("isFinished")Byte isFinished,
                                             RowBounds rowBounds);
    public int countUserCetTrains(@Param("userId") Integer userId,
                                  @Param("hasSelected")Boolean hasSelected,
                                  @Param("isFinished")Byte isFinished);


    // 参训人已选课程
    @ResultMap("persistence.cet.CetTraineeCourseMapper.BaseResultMap")
    @Select("select cteec.* from cet_trainee_course cteec, cet_train_course ctc " +
            "where cteec.trainee_id=#{traineeId} and cteec.train_course_id=ctc.id order by ctc.sort_order asc")
    public List<CetTraineeCourse> selectedCetTraineeCourses(@Param("traineeId") Integer traineeId);

    // 参训人未选课程
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("select * from cet_train_course where train_id=#{trainId} and " +
            "id not in(select train_course_id from cet_trainee_course where  trainee_id=#{traineeId}) order by sort_order asc")
    public List<CetTrainCourse> unSelectedCetTrainCourses(@Param("trainId") Integer trainId,
                                                             @Param("traineeId") Integer traineeId);

    // 培训班 选择 课程
    List<CetCourse> cetTrainCourse_selectCourses(@Param("trainId") int trainId,
                                                 @Param("expertId") Integer expertId,
                                                 @Param("name") String name, RowBounds rowBounds);
    int cetTrainCourse_countCourses(@Param("trainId") int trainId,
                                    @Param("expertId") Integer expertId,
                                    @Param("name") String name);

    // 获取参训人员第二天的第一堂课
    @ResultMap("persistence.cet.CetTrainCourseMapper.BaseResultMap")
    @Select("select ctc.* from cet_train_course ctc, cet_trainee_course cteec " +
            "where ctc.id = cteec.train_course_id and cteec.trainee_id=#{traineeId} and " +
            "left(ctc.start_time, 10) = date_add(curdate(),interval 1 day) " +
            "order by ctc.start_time asc limit 1")
    public CetTrainCourse getTomorrowFirstCourse(@Param("traineeId") int traineeId);

    // 获取某个培训班下面，每个参训人员的年度参加培训情况（年度参加培训的总学时数）
    @ResultType(java.util.HashMap.class)
    @Select("select cteecv.user_id as userId, sum(cteecv.period) as yearPeriod from cet_trainee ctee, cet_train ct, cet_trainee_course_view cteecv " +
            "where ctee.train_id=#{trainId} and ct.id=ctee.train_id " +
            "and cteecv.is_finished=1 and cteecv.user_id=ctee.user_id and cteecv.year=ct.year " +
            "group by cteecv.user_id")
    public List<Map> listTraineeYearPeriod(@Param("trainId") int trainId);


    @ResultType(bean.analysis.StatTrainBean.class)
    @Select("select result.train_course_id as trainCourseId, result.inspector_id as inspectorId, sum(rank.score) as totalScore, ic.feedback " +
            "from cet_train_eva_result result, cet_train_eva_rank rank, cet_train_inspector_course ic " +
            "where result.train_id=#{trainId} and rank.id=result.rank_id and " +
            "ic.train_course_id=result.train_course_id and ic.inspector_id=result.inspector_id " +
            "group by result.inspector_id, result.train_course_id")
    public List<StatTrainBean> stat(@Param("trainId") int trainId);

}
