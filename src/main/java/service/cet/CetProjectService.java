package service.cet;

import controller.global.OpException;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectObjExample;
import domain.cet.CetProjectTraineeType;
import domain.cet.CetProjectTraineeTypeExample;
import domain.cet.CetTraineeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CetProjectService extends BaseMapper {

    @Autowired
    private CetProjectObjService cetProjectObjService;
    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;

    @Transactional
    public void insertSelective(CetProject record, Integer[] traineeTypeIds){

        record.setCreateTime(new Date());
        cetProjectMapper.insertSelective(record);

        updateTrainTypes(record.getId(), traineeTypeIds);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        {
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andProjectIdIn(Arrays.asList(ids));
            List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);

            for (CetProjectObj record : cetProjectObjs) {

                cetProjectObjService.delRoleIfNotTrainee(record.getUserId());
            }
        }

        {
            CetProjectExample example = new CetProjectExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetProjectMapper.deleteByExample(example);
        }
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetProject record){

        return cetProjectMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateWithTraineeTypes(CetProject record, Integer[] traineeTypeIds){

        cetProjectMapper.updateByPrimaryKeySelective(record);

        updateTrainTypes(record.getId(), traineeTypeIds);
    }

    // 已选参训人类型
    public Set<Integer> findTraineeTypeIdSet(Integer projectId) {

        CetProjectTraineeTypeExample example = new CetProjectTraineeTypeExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<CetProjectTraineeType> cetProjectTraineeTypes = cetProjectTraineeTypeMapper.selectByExample(example);
        Set<Integer> traineeTypeIds = new HashSet<>();
        for (CetProjectTraineeType cetProjectTraineeType : cetProjectTraineeTypes) {
            traineeTypeIds.add(cetProjectTraineeType.getTraineeTypeId());
        }

        return traineeTypeIds;
    }

    // 更新参训人类型
    private void updateTrainTypes(int projectId, Integer[] traineeTypeIds){

        {
            Set<Integer> traineeTypeIdSet = findTraineeTypeIdSet(projectId);
            traineeTypeIdSet.removeAll(Arrays.asList(traineeTypeIds));
            if(traineeTypeIdSet.size()>0) {
                CetProjectObjExample example = new CetProjectObjExample();
                example.createCriteria().andProjectIdEqualTo(projectId).andTraineeTypeIdIn(new ArrayList<>(traineeTypeIdSet));
                if (cetProjectObjMapper.countByExample(example) > 0) {

                    List<String> traineeTypeList = new ArrayList<>();
                    Map<Integer, CetTraineeType> cetTraineeTypeMap = cetTraineeTypeService.findAll();
                    for (Integer traineeTypeId : traineeTypeIdSet) {
                        traineeTypeList.add(cetTraineeTypeMap.get(traineeTypeId).getName());
                    }

                    throw new OpException("参训人员类型（{}）已设置培训对象，不可删除。", StringUtils.join(traineeTypeList, "、"));
                }
            }
        }

        CetProjectTraineeTypeExample example = new CetProjectTraineeTypeExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        cetProjectTraineeTypeMapper.deleteByExample(example);

        if(traineeTypeIds==null || traineeTypeIds.length==0) return;

        for (Integer traineeTypeId : traineeTypeIds) {

            CetProjectTraineeType record = new CetProjectTraineeType();
            record.setProjectId(projectId);
            record.setTraineeTypeId(traineeTypeId);
            cetProjectTraineeTypeMapper.insertSelective(record);
        }
    }
}
