package service.cet;

import controller.global.OpException;
import domain.cet.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CetProjectService extends CetBaseMapper {

    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;

    @Transactional
    public void insertSelective(CetProject record, List<Integer> traineeTypeIdList){

        record.setCreateTime(new Date());
        cetProjectMapper.insertSelective(record);

        updateTrainTypes(record.getId(), traineeTypeIdList);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectExample example = new CetProjectExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetProject record){

        return cetProjectMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void updateWithTraineeTypes(CetProject record, List<Integer> traineeTypeIdList){

        cetProjectMapper.updateByPrimaryKeySelective(record);

        updateTrainTypes(record.getId(), traineeTypeIdList);
    }

    // 已选参训人类型
    public Set<Integer> findTraineeTypeIdSet(Integer projectId) {

        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);
        Set<Integer> traineeTypeIds = new HashSet<>();
        for (CetTraineeType cetTraineeType : cetTraineeTypes) {
            traineeTypeIds.add(cetTraineeType.getId());
        }
        traineeTypeIds.add(0);

        return traineeTypeIds;
    }

    // 更新参训人类型
    private void updateTrainTypes(int projectId, List<Integer> traineeTypeIdList){

        Map<Integer, CetTraineeType> cetTraineeTypeMap = cetTraineeTypeService.findAll();
        {
            Set<Integer> traineeTypeIdSet = findTraineeTypeIdSet(projectId);
            traineeTypeIdSet.removeAll(traineeTypeIdList);
            // 待删除的类型
            if(traineeTypeIdSet.size()>0) {
                for (Integer traineeTypeId : traineeTypeIdSet) {
                    CetProjectObjExample example = new CetProjectObjExample();
                    example.createCriteria().andProjectIdEqualTo(projectId).andTraineeTypeIdEqualTo(traineeTypeId);
                    if (cetProjectObjMapper.countByExample(example) > 0) {
                        throw new OpException("参训人员类型（{0}）已设置了培训对象，不可删除。",
                                cetTraineeTypeMap.get(traineeTypeId).getName());
                    }
                }
            }
        }

        if(traineeTypeIdList == null || traineeTypeIdList.size() == 0) return;

        CetProject record = new CetProject();
            record.setId(projectId);
            record.setTraineeTypeIds(StringUtils.join(traineeTypeIdList, ","));
            cetProjectMapper.updateByPrimaryKeySelective(record);
    }

    public CetProject getByName(String projectName){

        CetProjectExample cetProjectExample = new CetProjectExample();
        cetProjectExample.createCriteria().andNameEqualTo(projectName);
        List<CetProject> cetProjects = cetProjectMapper.selectByExample(cetProjectExample);
        if (cetProjects.size() == 1)
            return cetProjects.get(0);

        return null;
    }

    //处理其他参训人类型
    public CetTraineeType dealOtherType(CetProject cetProject){

        String[] t = cetProject.getTraineeTypeIds().split(",");
        if (t == null || t.length == 0) return null;
        List<String> _traineeTypeIds = Arrays.asList(t);
        if (_traineeTypeIds.contains("0")){
            CetTraineeType cetTraineeType = new CetTraineeType();
            cetTraineeType.setId(0);
            cetTraineeType.setName(cetProject.getOtherTraineeType());
            return cetTraineeType;
        }
        return null;
    }

}
