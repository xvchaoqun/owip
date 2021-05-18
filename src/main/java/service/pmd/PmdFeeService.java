package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.pmd.common.PmdFeeStatBean;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.PmdConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdFeeService extends PmdBaseMapper {

    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

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
    public int bacthImport(List<PmdFee> records) {

        int addCount = 0;
        for (PmdFee record : records) {

            PmdFee _record = null;

            // 覆盖未缴费的、起始月份相同的记录
            PmdFeeExample example = new PmdFeeExample();
            example.createCriteria().andUserIdEqualTo(record.getUserId())
                    .andStartMonthEqualTo(record.getStartMonth())
                    .andHasPayEqualTo(false);
            List<PmdFee> pmdFees = pmdFeeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

            if(pmdFees.size()>0){
                _record = pmdFees.get(0);
            }

            if(_record==null){

                insertSelective(record);
                addCount++;
            }else{
                record.setId(_record.getId());
                updateByPrimaryKeySelective(record);
            }
        }

        return addCount;
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
            record.setStatus(PmdConstants.PMD_FEE_STATUS_DELETED);
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
            record.setAmt(null); // 已缴费记录不允许更新 金额
        }

        pmdFeeMapper.updateByPrimaryKeySelective(record);
        sysApprovalLogService.add(record.getId(), ShiroHelper.getCurrentUserId(),
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_FEE,
                "更新", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 统计每个月的缴费总额/人数，以实际缴费成功的时间计算缴费月份
    public Map<Byte, PmdFeeStatBean> statPmdFee(Integer partyId, Date payMonth){

        String _payMonth = DateUtils.formatDate(payMonth, DateUtils.YYYYMM);
        List<PmdFeeStatBean> pmdFeeStatBeans = iPmdMapper.statPmdFee(partyId, _payMonth);
        Map<Byte, PmdFeeStatBean> statMap = new HashMap<>();
        for (PmdFeeStatBean pmdFeeStatBean : pmdFeeStatBeans) {
            statMap.put(pmdFeeStatBean.getUserType(), pmdFeeStatBean);
        }

        return statMap;
    }
}
