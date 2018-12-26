package service.cet;

import domain.cet.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys.constants.CetConstants;

import java.util.*;

@Service
public class CetTrainService extends CetBaseMapper {

    @Autowired
    private CetTraineeService cetTraineeService;

    public CetTrainView getView(int trainId){

        CetTrainViewExample example = new CetTrainViewExample();
        example.createCriteria().andIdEqualTo(trainId);
        List<CetTrainView> cetTrainViews = cetTrainViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTrainViews.size()==1?cetTrainViews.get(0):null;
    }

    @Transactional
    public void insertSelective(CetTrain record){

        record.setEnrollStatus(CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT);
        record.setPubStatus(CetConstants.CET_TRAIN_PUB_STATUS_UNPUBLISHED);
        record.setIsDeleted(false);
        record.setCreateTime(new Date());
        cetTrainMapper.insertSelective(record);

        int trainId = record.getId();
        // 同步培训班学员
        if(record.getPlanId()!=null){
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(record.getPlanId());
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andProjectIdEqualTo(cetProjectPlan.getProjectId());
            List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);
            for (CetProjectObj cetProjectObj : cetProjectObjs) {
                cetTraineeService.createIfNotExist(cetProjectObj.getUserId(), trainId);
            }
        }
    }

    /*@Transactional
    public void fakeDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainExample example = new CetTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetTrain record = new CetTrain();
        record.setIsDeleted(true);

        cetTrainMapper.updateByExampleSelective(record, example);
    }*/

    // 彻底删除
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainExample example = new CetTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTrainMapper.deleteByExample(example);
    }

    @Transactional
    public void updateBase(CetTrain record){

        cetTrainMapper.updateByPrimaryKeySelective(record);
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
