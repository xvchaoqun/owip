package service.cet;

import domain.cet.CetTrain;
import domain.cet.CetTrainExample;
import domain.cet.CetTrainTraineeType;
import domain.cet.CetTrainTraineeTypeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.CetConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CetTrainService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        CetTrainExample example = new CetTrainExample();
        CetTrainExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetTrainMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetTrain record, Integer[] traineeTypeIds){

        record.setEnrollStatus(CetConstants.CET_TRAIN_ENROLL_STATUS_DEFAULT);
        record.setPubStatus(CetConstants.CET_TRAIN_PUB_STATUS_UNPUBLISHED);
        record.setIsDeleted(false);
        record.setCreateTime(new Date());
        cetTrainMapper.insertSelective(record);

        updateTrainTypes(record.getId(), traineeTypeIds);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTrainExample example = new CetTrainExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetTrain record = new CetTrain();
        record.setIsDeleted(true);

        cetTrainMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void updateBase(CetTrain record){

        cetTrainMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateWithTraineeTypes(CetTrain record, Integer[] traineeTypeIds){

        cetTrainMapper.updateByPrimaryKeySelective(record);

        updateTrainTypes(record.getId(), traineeTypeIds);
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
}
