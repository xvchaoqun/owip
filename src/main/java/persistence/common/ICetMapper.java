package persistence.common;

import domain.cet.CetCourse;
import domain.cet.CetTrainCourse;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;
import persistence.common.bean.ICetTrain;

import java.util.List;

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
}
