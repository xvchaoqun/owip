package service.cet;

import controller.global.OpException;
import domain.cadre.CadreView;
import domain.cet.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.JSONUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CetUpperTrainService extends CetBaseMapper {

    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected CetUpperTrainAdminService cetUpperTrainAdminService;
    @Autowired
    protected CetUnitTrainService cetUnitTrainService;

    @Transactional
    public void insertSelective(CetUpperTrain record) {

        int userId = record.getUserId();

        record.setAddUserId(ShiroHelper.getCurrentUserId());
        record.setAddTime(new Date());

        CadreView cadre = CmTag.getCadreByUserId(userId);
        if (cadre != null) {
            if(StringUtils.isBlank(record.getTitle())) {
                record.setTitle(cadre.getTitle());
            }
        }

        cetUpperTrainMapper.insertSelective(record);

        sysApprovalLogService.add(record.getId(), record.getUserId(),
                (record.getAddType() == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF) ?
                        SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                "添加", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    @Transactional
    public void del(Integer id, boolean real) {

        CetUpperTrain oldRecord = cetUpperTrainMapper.selectByPrimaryKey(id);

        byte addType = oldRecord.getAddType();
        Integer unitId = oldRecord.getUnitId();

        int currentUserId = ShiroHelper.getCurrentUserId();
        if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW) { // 组织部添加的只有组织部可以删除

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:del");
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT) { // 单位添加的组织部、本单位可以删除

            if (!ShiroHelper.isPermitted("cetUpperTrain:del")) {

                SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_CET_ADMIN_UPPER);

                if (oldRecord.getIsValid() != null) { // 组织部确认后，不允许单位修改
                    throw new UnauthorizedException();
                }

                Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);

                if (unitId == null || !adminUnitIdSet.contains(unitId)) {
                    throw new UnauthorizedException(); // 非单位管理员
                }
            }
        } else if (addType == CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF) {

            if (!ShiroHelper.isPermitted("cetUpperTrain:del")) {
                if (ShiroHelper.hasRole(RoleConstants.ROLE_CET_ADMIN_UPPER)) {

                    if (oldRecord.getIsValid() != null) { // 组织部确认后，不允许单位修改
                        throw new OpException("党委组织部已确认，不允许删除。");
                    }
                    Set<Integer> adminUnitIdSet = cetUpperTrainAdminService.adminUnitIdSet(currentUserId);

                    if (unitId == null || !adminUnitIdSet.contains(unitId)) {

                        if (currentUserId == oldRecord.getUserId()) { // 是单位管理员，但是是删除本人的记录

                            if (oldRecord.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_PASS) {// 审核通过后，不允许本人修改
                                throw new OpException("审核已通过，不允许删除。");
                            }
                        }

                        throw new UnauthorizedException(); // 非本人、非单位管理员，也不是校领导管理员
                    }
                } else if (currentUserId == oldRecord.getUserId()) {

                    if (oldRecord.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_PASS) {// 审核通过后，不允许本人修改
                        throw new OpException("审核已通过，不允许删除。");
                    }
                } else {
                    throw new UnauthorizedException();
                }
            }
        }
        if (real) {

            SecurityUtils.getSubject().checkPermission("cetUpperTrain:del");
            cetUpperTrainMapper.deleteByPrimaryKey(id);

        } else {
            CetUpperTrain record = new CetUpperTrain();
            record.setId(id);
            record.setIsDeleted(true);
            cetUpperTrainMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                    "删除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids, boolean real) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            del(id, real);
        }
    }

    @Transactional
    public void batchDelBySelf(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        int currentUserId = ShiroHelper.getCurrentUserId();
        CetUpperTrainExample example = new CetUpperTrainExample();
        example.createCriteria().andUserIdEqualTo(currentUserId).andIdIn(Arrays.asList(ids))
                .andStatusEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_INIT);
        cetUpperTrainMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CetUpperTrain record, boolean isCheck) {

        CetUpperTrain oldRecord = cetUpperTrainMapper.selectByPrimaryKey(record.getId());

        cetUpperTrainMapper.updateByPrimaryKeySelective(record);
        if (record.getType() == CetConstants.CET_UPPER_TRAIN_TYPE_OW) {// 组织部门派出
            commonMapper.excuteSql("update cet_upper_train set unit_id=null where id=" + record.getId());
        }

        if (isCheck) {
            if (record.getStatus() != null && !CetConstants.CET_UPPER_TRAIN_STATUS_MAP.containsKey(record.getStatus())) {
                throw new OpException("参数有误。");
            }
            sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                    "审批", (record.getStatus() == null || record.getStatus() == CetConstants.CET_UPPER_TRAIN_STATUS_PASS)
                            ? SystemConstants.SYS_APPROVAL_LOG_STATUS_PASS : SystemConstants.SYS_APPROVAL_LOG_STATUS_BACK,
                    record.getBackReason());
        } else {
            sysApprovalLogService.add(oldRecord.getId(), oldRecord.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN,
                    "更新", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, JSONUtils.toString(oldRecord, false));
        }
    }

    // 供导入去重使用
    public CetUpperTrain get(CetUpperTrain record) {

        CetUpperTrainExample example = new CetUpperTrainExample();
        CetUpperTrainExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(record.getType())
                .andIsOnlineEqualTo(record.getIsOnline())
                .andUserIdEqualTo(record.getUserId())
                .andTraineeTypeIdEqualTo(record.getTraineeTypeId())
                .andYearEqualTo(record.getYear())
                .andTrainNameEqualTo(record.getTrainName())
                .andIsDeletedEqualTo(false)
                .andStatusNotEqualTo(CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS);
        if (record.getTrainType() != null){
            criteria.andTrainTypeEqualTo(record.getTrainType());
        }
        if (record.getTraineeTypeId() == 0){
            criteria.andOtherTraineeTypeEqualTo(record.getOtherTraineeType());
        }
        if (record.getOrganizer() != null) {
            criteria.andOrganizerEqualTo(record.getOrganizer());
            if (record.getOrganizer() == 0) {
                criteria.andOtherOrganizerEqualTo(record.getOtherOrganizer());
            }
        }

        List<CetUpperTrain> cetUpperTrains = cetUpperTrainMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetUpperTrains.size() == 1 ? cetUpperTrains.get(0) : null;
    }

    @Transactional
    public int batchImport(List<CetUpperTrain> records) {

        int addCount = 0;
        for (CetUpperTrain record : records) {

            CetUpperTrain _cetUpperTrain = get(record);
            if (_cetUpperTrain == null) {

                record.setAddType(CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW);
                record.setStatus(CetConstants.CET_UPPER_TRAIN_STATUS_PASS);

                insertSelective(record);
                addCount++;
            } else {

                record.setId(_cetUpperTrain.getId());
                cetUpperTrainMapper.updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
    }

    @Transactional
    public void batchTransfer(Integer[] ids, Byte cetType, Byte specialType, Integer projectId) {

        if (cetType == CetConstants.CET_TYPE_T_PARTY_SCHOOL){
            CetUpperTrain record = new CetUpperTrain();
            record.setType(CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL);
            record.setSpecialType(specialType);
            CetUpperTrainExample example = new CetUpperTrainExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetUpperTrainMapper.updateByExampleSelective(record, example);
        }else if (cetType == CetConstants.CET_TYPE_T_PARTY){

            for (Integer id : ids) {
                CetUnitTrain dest = new CetUnitTrain();
                CetUpperTrain orig = cetUpperTrainMapper.selectByPrimaryKey(id);
                try {
                    PropertyUtils.copyProperties(dest, orig);
                } catch (Exception e) {
                    throw new OpException("批量转移参训人员异常");
                }
                dest.setProjectId(projectId);
                dest.setStatus(CetConstants.CET_UNITTRAIN_RERECORD_PASS);
                dest.setId(null);
                cetUnitTrainMapper.insert(dest);
                cetUpperTrainMapper.deleteByPrimaryKey(id);
            }
            cetUnitTrainService.updateTotalCount(projectId);
        }
    }
}
