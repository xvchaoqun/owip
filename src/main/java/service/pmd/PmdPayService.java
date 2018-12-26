package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMonth;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/11/7.
 */
@Service
public class PmdPayService extends PmdBaseMapper {

    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 现金支付
    @Transactional
    public void payCash(int memberId) {

        pmdMemberService.checkAdmin(memberId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(memberId);

        int payStatus = pmdMember.getPayStatus();
        if(payStatus==0){
            throw new OpException("缴费失败，请稍后再试。");
        }
        if(pmdMember.getDuePay()==null){
            throw new OpException("请先设置缴纳额度。");
        }

        BigDecimal realPay = pmdMember.getDuePay();
        if(payStatus==1){

            // 缴费时，需更新快照
            PmdMember record = new PmdMember();
            record.setId(memberId);
            record.setHasPay(true);
            record.setRealPay(realPay);
            record.setIsSelfPay(false);
            record.setPayTime(new Date());
            record.setChargeUserId(ShiroHelper.getCurrentUserId());

            PmdMemberExample example = new PmdMemberExample();
            example.createCriteria().andIdEqualTo(memberId)
                    .andHasPayEqualTo(false);
            if(pmdMemberMapper.updateByExampleSelective(record, example)==0){
                throw new OpException("更新快照失败");
            }
        }

        PmdMemberPay record = new PmdMemberPay();
        record.setMemberId(memberId);
        record.setHasPay(true);
        record.setRealPay(realPay);
        record.setIsSelfPay(false);
        record.setPayMonthId(currentMonthId);

        // 当前所在的单位快照
        PmdMember _pmdMember = pmdMemberService.get(currentMonthId, pmdMember.getUserId());
        // 把当前所在党委、支部，设置为缴费的单位
        int partyId = _pmdMember.getPartyId();
        Integer branchId = _pmdMember.getBranchId();

        record.setChargePartyId(partyId);
        record.setChargeBranchId(branchId);
        record.setPayTime(new Date());
        record.setChargeUserId(ShiroHelper.getCurrentUserId());

        PmdMemberPayExample example = new PmdMemberPayExample();
        example.createCriteria().andMemberIdEqualTo(memberId)
                .andHasPayEqualTo(false);
        if(pmdMemberPayMapper.updateByExampleSelective(record, example)==0){
            throw new OpException("更新账单失败");
        }

        sysApprovalLogService.add(memberId, pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "现金支付", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }

    // 延迟缴费
    @Transactional
    public void delay(int pmdMemberId, String delayReason) {

        pmdMemberService.checkAdmin(pmdMemberId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 只有当前缴费月份才允许操作
        if (currentPmdMonth == null || currentPmdMonth.getId().intValue() != pmdMember.getMonthId()) {
            throw new OpException("操作失败，请稍后再试。");
        }

        if(pmdMember.getDuePay()==null){
            throw new OpException("请先设置缴纳额度。");
        }

        PmdMember record = new PmdMember();
        record.setIsDelay(true);
        record.setDelayReason(delayReason);

        PmdMemberExample example = new PmdMemberExample();
        example.createCriteria().andIdEqualTo(pmdMemberId)
                .andHasPayEqualTo(false)
                .andIsDelayEqualTo(false);

        pmdMemberMapper.updateByExampleSelective(record, example);

        sysApprovalLogService.add(pmdMemberId, pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "延迟缴费", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }


    // 当前缴费月份下，分党委或支部批量延迟缴费
    @Transactional
    public void delayAll(int partyId, Integer branchId, String delayReason) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        // 只有当前缴费月份才允许操作
        if (currentPmdMonth == null) {
            throw new OpException("操作失败，请稍后再试。");
        }

        PmdMember record = new PmdMember();
        record.setIsDelay(true);
        record.setDelayReason(StringUtils.defaultIfBlank(delayReason, "批量延迟缴费"));

        PmdMemberExample example = new PmdMemberExample();
        PmdMemberExample.Criteria criteria = example.createCriteria()
                .andMonthIdEqualTo(currentPmdMonth.getId())
                .andPartyIdEqualTo(partyId)
                .andDuePayIsNotNull()  // 必须设置了缴纳额度才允许延迟缴费
                .andHasPayEqualTo(false)
                .andIsDelayEqualTo(false);

        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);

            // 组织部管理员、分党委管理员、党支部管理员允许延迟
            if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
                if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                    if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                        throw new UnauthorizedException();
                    }
                }
            }
        }else{
            // 组织部管理员、分党委管理员允许延迟
            if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
                if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                    throw new UnauthorizedException();
                }
            }
        }

        List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);
        for (PmdMember pmdMember : pmdMembers) {

            sysApprovalLogService.add(pmdMember.getId(), pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                    "批量延迟缴费", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
        }

        pmdMemberMapper.updateByExampleSelective(record, example);
    }

    // 取消延迟缴费
    @Transactional
    public void unDelay(int pmdMemberId) {

        pmdMemberService.checkAdmin(pmdMemberId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 只有当前缴费月份才允许操作
        if (currentPmdMonth == null || currentPmdMonth.getId().intValue() != pmdMember.getMonthId()) {
            throw new OpException("操作失败，请稍后再试。");
        }

        commonMapper.excuteSql("update pmd_member set is_delay=0, delay_reason=null " +
                "where has_pay=0 and is_delay=1 and id=" + pmdMemberId);

        sysApprovalLogService.add(pmdMemberId, pmdMember.getUserId(), SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER,
                "取消延迟缴费", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, null);
    }
}
