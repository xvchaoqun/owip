package service.cet;

import controller.global.OpException;
import domain.cet.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CetProjectService extends CetBaseMapper {

    @Autowired
    private CetProjectObjService cetProjectObjService;
    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;

    public CetProjectView getView(int projectId){

        CetProjectViewExample example = new CetProjectViewExample();
        example.createCriteria().andIdEqualTo(projectId);
        List<CetProjectView> cetProjectViews = cetProjectViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetProjectViews.size()==1?cetProjectViews.get(0):null;
    }

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

        Map<Integer, CetTraineeType> cetTraineeTypeMap = cetTraineeTypeService.findAll();
        {
            Set<Integer> traineeTypeIdSet = findTraineeTypeIdSet(projectId);
            traineeTypeIdSet.removeAll(Arrays.asList(traineeTypeIds));
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
