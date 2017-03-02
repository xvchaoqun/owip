package persistence.common;

import bean.analysis.StatTrainBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fafa on 2017/3/2.
 */
public interface StatTrainMapper {

    @ResultType(bean.analysis.StatTrainBean.class)
    @Select("select result.course_id as courseId, result.inspector_id as inspectorId, sum(rank.score) as totalScore, ic.feedback " +
            "from train_eva_result result, train_eva_rank rank, train_inspector_course ic " +
            "where result.train_id=#{trainId} and rank.id=result.rank_id and ic.course_id=result.course_id and ic.inspector_id=result.inspector_id " +
            "group by result.inspector_id, result.course_id")
    public List<StatTrainBean> stat(@Param("trainId") int trainId);
}
