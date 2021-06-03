package service.cet;

import controller.global.OpException;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import domain.cet.CetProjectObjExample;
import domain.cet.CetTraineeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CetProjectService extends CetBaseMapper {

    @Autowired
    private CetTraineeTypeService cetTraineeTypeService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;

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

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_PROJECT,
                    "创建", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        HashSet<Integer> adminPartyIdSet = new HashSet<>();
        if(ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)){

            List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
            adminPartyIdSet.addAll(adminPartyIdList);
        }

        for (Integer id : ids) {

            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(id);
            boolean isPartyProject = cetProject.getIsPartyProject();

            if(isPartyProject && ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)){

                if(!adminPartyIdSet.contains(cetProject.getCetPartyId())){
                    throw new UnauthorizedException();
                }
            }

            CetProject record = new CetProject();
            record.setId(id);
            record.setIsDeleted(true);
            cetProjectMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_PROJECT,
                    "删除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }
    }


    @Transactional
    public void updateWithTraineeTypes(CetProject record, List<Integer> traineeTypeIdList){

        record.setHasArchive(false);
        cetProjectMapper.updateByPrimaryKeySelective(record);

        updateTrainTypes(record.getId(), traineeTypeIdList);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_PROJECT,
                    "更新", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 返回待报送
    @Transactional
    public void back(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_PROJECT,
                    "返回待报送", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }

        CetProjectExample example = new CetProjectExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetProject record = new CetProject();
        record.setStatus(CetConstants.CET_PROJECT_STATUS_UNREPORT);
        cetProjectMapper.updateByExampleSelective(record, example);
    }

    // 报送
    @Transactional
    public void report(int[] ids) {

        for (int id : ids) {

            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(id);
            if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
                List<Integer> adminPartyIdList = iCetMapper.getAdminPartyIds(ShiroHelper.getCurrentUserId());
                if (!adminPartyIdList.contains(cetProject.getCetPartyId())) {
                    throw new OpException("没有权限。");
                }
            }

            CetProject record = new CetProject();
            record.setId(id);
            record.setStatus(CetConstants.CET_PROJECT_STATUS_REPORT);
            cetProjectMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_PROJECT,
                        "报送", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }
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
