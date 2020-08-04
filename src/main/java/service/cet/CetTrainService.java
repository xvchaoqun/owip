package service.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainExample;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CetTrainService extends CetBaseMapper {

    @Transactional
    public void insertSelective(CetTrain record){

        record.setIsDeleted(false);
        record.setCreateTime(new Date());
        cetTrainMapper.insertSelective(record);
    }

    // 彻底删除
    @Transactional
    public void batchDel(Integer[] ids, Integer planId){

        if(ids==null || ids.length==0) return;

        CetTrainExample example = new CetTrainExample();
        CetTrainExample.Criteria criteria = example.createCriteria().andIdIn(Arrays.asList(ids));
        if(planId!=null){
            criteria.andPlanIdEqualTo(planId);
        }
        cetTrainMapper.deleteByExample(example);

        if(planId!=null){
            iCetMapper.updateTrainCourseTotalPeriod(planId);
        }
    }

    // 每个参训人员的年度参加培训情况（年度参加培训的总学时数）
    public Map<Integer, Object> traineeYearPeriodMap(int trainId){

        Map<Integer, Object> userMap = new HashMap<>();
        List<Map> records = iCetMapper.listTraineeYearPeriod(trainId);
        for (Map record : records) {
            userMap.put(((Long) record.get("userId")).intValue(), record.get("yearPeriod"));
        }

        return userMap;
    }

    // 修改评课关闭时间
    @Transactional
    public void updateEvaCloseTime(int id, boolean evaClosed, Date closeTime) {

        if(evaClosed) {
            String sql = "update cet_train set eva_closed=1, eva_close_time=null where id="+id;
            commonMapper.excuteSql(sql);
        }else{
            CetTrain record = new CetTrain();
            record.setId(id);
            record.setEvaClosed(evaClosed);
            record.setEvaCloseTime(closeTime);
            cetTrainMapper.updateByPrimaryKeySelective(record);
        }
    }

    // 1:已关闭评课 3：评课已结束
    public int evaIsClosed(int trainId){

        CetTrain train = cetTrainMapper.selectByPrimaryKey(trainId);
        if(BooleanUtils.isTrue(train.getEvaClosed())){
            return 1;
        }

        Date now = new Date();
        Date closeTime = train.getEvaCloseTime();

        if(closeTime!=null && now.after(closeTime)){
            return 3;
        }

        return 0;
    }
}
