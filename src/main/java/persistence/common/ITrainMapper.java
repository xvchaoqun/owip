package persistence.common;

import bean.analysis.StatTrainBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ITrainMapper {

    @Update("update train_course c, train_inspector_course ic set c.finish_count=c.finish_count-1 " +
            "where ic.inspector_id=#{inspectorId} and ic.status=1 and c.id=ic.course_id and c.status=1 and c.finish_count>=1")
    void abolishTrainInspector(Integer inspectorId);

    @Update("update train t , (select train_id, sum(IF(status=1, 1, 0)) as course_num from train_course where is_global=0 group by train_id) tc " +
            "set t.course_num=tc.course_num where tc.train_id=t.id")
    void update_train_courseNum();

    //update train_eva_norm t1 left join (select fid, count(id) as norm_num from train_eva_norm group by fid) t2
    //on t2.fid=t1.id set t1.norm_num=t2.norm_num

    @ResultType(bean.analysis.StatTrainBean.class)
    @Select("select result.course_id as courseId, result.inspector_id as inspectorId, sum(rank.score) as totalScore, ic.feedback " +
            "from train_eva_result result, train_eva_rank rank, train_inspector_course ic " +
            "where result.train_id=#{trainId} and rank.id=result.rank_id and ic.course_id=result.course_id and ic.inspector_id=result.inspector_id " +
            "group by result.inspector_id, result.course_id")
    public List<StatTrainBean> stat(@Param("trainId") int trainId);
}
