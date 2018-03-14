package persistence.common;

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
                                             @Param("isFinished")Boolean isFinished,
                                             RowBounds rowBounds);
    public int countUserCetTrains(@Param("userId") Integer userId,
                                  @Param("hasSelected")Boolean hasSelected,
                                  @Param("isFinished")Boolean isFinished);
}
