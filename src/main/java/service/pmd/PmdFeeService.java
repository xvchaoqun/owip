package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;

import java.util.Date;

@Service
public class PmdFeeService extends PmdBaseMapper {

    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    public boolean idDuplicate(Integer id, Integer userId, Date startMonth, Date endMonth){

        PmdFeeExample example = new PmdFeeExample();
        PmdFeeExample.Criteria criteria = example.createCriteria().andStatusEqualTo(PmdConstants.PMD_FEE_STATUS_NORMAL);
        if(id!=null) criteria.andIdNotEqualTo(id);
        criteria.andUserIdEqualTo(userId).andStartMonthGreaterThanOrEqualTo(startMonth)
                .andEndMonthLessThanOrEqualTo(endMonth);

        return pmdFeeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PmdFee record){

        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if(!pmdBranchAdminService.adminBranch(loginUserId, record.getPartyId(), record.getBranchId())){
            throw new UnauthorizedException();
        }
        pmdFeeMapper.insertSelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_FEE,
                "添加", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            PmdFee pmdFee=pmdFeeMapper.selectByPrimaryKey(id);
            Integer loginUserId = ShiroHelper.getCurrentUserId();

            if(!pmdBranchAdminService.adminBranch(loginUserId, pmdFee.getPartyId(), pmdFee.getBranchId())){
                throw new UnauthorizedException();
            }

            if(pmdFee.getHasPay()){
                throw new OpException("已缴费记录不允许删除");
            }

            PmdFee record = new PmdFee();
            record.setId(id);
            record.setStatus(PmdConstants.PMD_FEE_STATUS_NORMAL);
            pmdFeeMapper.updateByPrimaryKeySelective(record);

            sysApprovalLogService.add(id, ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_FEE,
                "删除", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }
    }

    @Transactional
    public void updateByPrimaryKeySelective(PmdFee record){

        PmdFee pmdFee=pmdFeeMapper.selectByPrimaryKey(record.getId());
        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if(!pmdBranchAdminService.adminBranch(loginUserId, pmdFee.getPartyId(), pmdFee.getBranchId())){
            throw new UnauthorizedException();
        }

        if(pmdFee.getHasPay()){
            pmdFee.setAmt(null); // 已缴费记录不允许更新 金额
        }

        pmdFeeMapper.updateByPrimaryKeySelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_FEE,
                "更新", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }
}
