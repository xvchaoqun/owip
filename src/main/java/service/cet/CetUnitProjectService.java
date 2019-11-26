package service.cet;

import controller.global.OpException;
import domain.cet.CetUnitProject;
import domain.cet.CetUnitProjectExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.LoginUserService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class CetUnitProjectService extends CetBaseMapper {
    
    @Autowired
    protected CetUpperTrainAdminService cetUpperTrainAdminService;
    @Autowired
    protected SysApprovalLogService sysApprovalLogService;
    @Autowired
    protected LoginUserService loginUserService;
    
    @Transactional
    public void insertSelective(CetUnitProject record) {

        record.setTotalCount(0);
        cetUnitProjectMapper.insertSelective(record);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN,
                    "添加", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }
    
    @Transactional
    public void del(Integer id) {

        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(id);
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                throw new OpException("没有权限。");
            }
        }
        if(cetUnitProject.getStatus() != CetConstants.CET_UNIT_PROJECT_STATUS_UNREPORT){
            throw new OpException("只允许删除未报送的记录");
        }

        cetUnitProjectMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    public void batchDel(Integer[] ids) {
        
        if (ids == null || ids.length == 0) return;
        
        for (Integer id : ids) {
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN,
                    "删除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }

        CetUnitProjectExample example = new CetUnitProjectExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetUnitProject record = new CetUnitProject();
        record.setStatus(CetConstants.CET_UNIT_PROJECT_STATUS_DELETE);
        cetUnitProjectMapper.updateByExampleSelective(record, example);
    }

    // 返回待报送
    @Transactional
    public void back(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
                CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(id);
                List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
                if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                    throw new OpException("没有权限。");
                }
            }
            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN,
                    "返回待报送", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }

        CetUnitProjectExample example = new CetUnitProjectExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        CetUnitProject record = new CetUnitProject();
        record.setStatus(CetConstants.CET_UNIT_PROJECT_STATUS_UNREPORT);
        cetUnitProjectMapper.updateByExampleSelective(record, example);
    }
    
    @Transactional
    public void updateByPrimaryKeySelective(CetUnitProject record) {

        cetUnitProjectMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN,
                    "更新", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 报送
    @Transactional
    public void report(int id) {

        CetUnitProject cetUnitProject = cetUnitProjectMapper.selectByPrimaryKey(id);
        if (ShiroHelper.lackRole(RoleConstants.ROLE_CET_ADMIN)) {
            List<Integer> adminPartyIdList = loginUserService.adminPartyIdList();
            if (!adminPartyIdList.contains(cetUnitProject.getPartyId())) {
                throw new OpException("没有权限。");
            }
        }

        CetUnitProject record = new CetUnitProject();
        record.setId(id);
        record.setStatus(CetConstants.CET_UNIT_PROJECT_STATUS_REPORT);
        cetUnitProjectMapper.updateByPrimaryKeySelective(record);

        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN,
                    "报送", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }
}
