package persistence.common;

import domain.cet.CetTraineeType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICetMapper {

    // 岗位报名分类统计
    @ResultMap("persistence.cet.CetTraineeTypeMapper.BaseResultMap")
    @Select("select  ctt.* from cet_train_trainee_type cttt, cet_trainee_type ctt where cttt.train_id=#{trainId}" +
            " and cttt.trainee_type_id = ctt.id order by ctt.sort_order asc")
    public List<CetTraineeType> getCetTraineeTypes(@Param("trainId") Integer trainId);
}
