package service.cet;

import controller.global.OpException;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import domain.cet.CetProjectObjExample;
import domain.cet.CetTraineeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CetProjectService extends CetBaseMapper {

    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;

    @Transactional
    public void insertSelective(CetProject record, List<Integer> traineeTypeIdList){

        record.setCreateTime(new Date());

        if(ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN)){
            record.setStatus(CetConstants.CET_PROJECT_STATUS_PASS);
        }else{
            record.setStatus(CetConstants.CET_PROJECT_STATUS_UNREPORT);
        }

        cetProjectMapper.insertSelective(record);

        updateTrainTypes(record.getId(), traineeTypeIdList);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectExample example = new CetProjectExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        CetProject record = new CetProject();
        record.setIsDeleted(true);
        cetProjectMapper.updateByExampleSelective(record, example);
    }


    @Transactional
    public void updateWithTraineeTypes(CetProject record, List<Integer> traineeTypeIdList){

        cetProjectMapper.updateByPrimaryKeySelective(record);

        updateTrainTypes(record.getId(), traineeTypeIdList);
    }

    // 已选参训人类型
    public Set<Integer> findTraineeTypeIdSet(int projectId) {

        return getCetTraineeTypes(projectId).stream().map(CetTraineeType::getId).collect(Collectors.toSet());
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

    // 得到培训项目的参训人员类型（含其他类型）
    public List<CetTraineeType> getCetTraineeTypes(int projectId){

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);

        return getCetTraineeTypes(cetProject);
    }

    public List<CetTraineeType> getCetTraineeTypes(CetProject cetProject){

        int projectId = cetProject.getId();
        List<CetTraineeType> cetTraineeTypes = iCetMapper.getCetTraineeTypes(projectId);

        if(StringUtils.contains(","+ cetProject.getTraineeTypeIds() +",", ",0,")){

            CetTraineeType cetTraineeType = new CetTraineeType();
            cetTraineeType.setId(0);
            cetTraineeType.setName(cetProject.getOtherTraineeType());

            cetTraineeTypes.add(cetTraineeType);
        }

        return cetTraineeTypes;
    }
}
