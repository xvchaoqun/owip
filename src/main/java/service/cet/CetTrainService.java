package service.cet;

import controller.global.OpException;
import domain.cet.CetTrain;
import domain.cet.CetTrainExample;
import domain.cet.CetTrainTraineeType;
import domain.cet.CetTrainTraineeTypeExample;
import domain.cet.CetTrainee;
import domain.cet.CetTraineeExample;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.constants.CetConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetTrainService extends BaseMapper {

    @Autowired
    private CetTraineeService cetTraineeService;

    public boolean idDuplicate(Integer id, int type, int year, int num){

        CetTrainExample example = new CetTrainExample();
        CetTrainExample.Criteria criteria = example.createCriteria()
                .andNumEqualTo(num)
                .andTypeEqualTo(type).andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetTrainMapper.countByExample(example) > 0;
    }

    // 编号（针对校内培训）
    public int genNum(int type, int year){

        int num ;
        CetTrainExample example = new CetTrainExample();
        example.createCriteria().andYearEqualTo(year)
                .andTypeEqualTo(type)
                .andIsOnCampusEqualTo(true);
        example.setOrderByClause("num desc");
        List<CetTrain> records = cetTrainMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(records.size()>0){
            num = records.get(0).getNum() + 1;
        }else{
            num = 1;
        }

        return num;
    }

    @Transactional
    public void insertSelective(CetTrain record, Integer[] traineeTypeIds){

        if(record.getIsOnCampus()) {
            if (idDuplicate(null, record.getType(), record.getYear(), record.getNum())) {
                throw new OpException("编号重复。");
            }
        }

        record.setEnrollStatus(CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT);
        record.setPubStatus(CetConstants.CET_TRAIN_PUB_STATUS_UNPUBLISHED);
        record.setIsDeleted(false);
        record.setCreateTime(new Date());
        cetTrainMapper.insertSelective(record);

        if(record.getIsOnCampus()) {
            updateTrainTypes(record.getId(), traineeTypeIds);
        }
    }

    @Transactional
    public void fakeDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainExample example = new CetTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetTrain record = new CetTrain();
        record.setIsDeleted(true);

        cetTrainMapper.updateByExampleSelective(record, example);
    }

    // 彻底删除
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        {
            CetTraineeExample example = new CetTraineeExample();
            example.createCriteria().andTrainIdIn(Arrays.asList(ids));
            List<CetTrainee> cetTrainees = cetTraineeMapper.selectByExample(example);

            for (CetTrainee cetTrainee : cetTrainees) {

                cetTraineeService.delRoleIfNotTrainee(cetTrainee.getUserId());
            }
        }

        {
            CetTrainExample example = new CetTrainExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetTrainMapper.deleteByExample(example);
        }
    }

    @Transactional
    public void updateBase(CetTrain record){

        cetTrainMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateWithTraineeTypes(CetTrain record, Integer[] traineeTypeIds){

        if(record.getIsOnCampus()) {
            if (idDuplicate(record.getId(), record.getType(), record.getYear(), record.getNum())) {
                throw new OpException("编号重复。");
            }
        }

        cetTrainMapper.updateByPrimaryKeySelective(record);

        if(record.getIsOnCampus()) {
            updateTrainTypes(record.getId(), traineeTypeIds);
        }
    }

    // 已选参训人类型
    public List<Integer> findTraineeTypeIds(Integer trainId) {

        CetTrainTraineeTypeExample example = new CetTrainTraineeTypeExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        List<CetTrainTraineeType> cetTrainTraineeTypes = cetTrainTraineeTypeMapper.selectByExample(example);
        List<Integer> traineeTypeIds = new ArrayList<>();
        for (CetTrainTraineeType cetTrainTraineeType : cetTrainTraineeTypes) {
            traineeTypeIds.add(cetTrainTraineeType.getTraineeTypeId());
        }

        return traineeTypeIds;
    }

    // 更新参训人类型
    private void updateTrainTypes(int trainId, Integer[] traineeTypeIds){

        CetTrainTraineeTypeExample example = new CetTrainTraineeTypeExample();
        example.createCriteria().andTrainIdEqualTo(trainId);
        cetTrainTraineeTypeMapper.deleteByExample(example);

        if(traineeTypeIds==null || traineeTypeIds.length==0) return;

        for (Integer traineeTypeId : traineeTypeIds) {

            CetTrainTraineeType record = new CetTrainTraineeType();
            record.setTrainId(trainId);
            record.setTraineeTypeId(traineeTypeId);
            cetTrainTraineeTypeMapper.insertSelective(record);
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
