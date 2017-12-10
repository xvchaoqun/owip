package service.pmd;

import controller.global.OpException;
import domain.pmd.PmdBranch;
import domain.pmd.PmdMember;
import domain.pmd.PmdMemberExample;
import domain.pmd.PmdMemberPay;
import domain.pmd.PmdMemberPayExample;
import domain.pmd.PmdMonth;
import domain.pmd.PmdParty;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lm on 2017/11/7.
 */
@Service
public class PmdPayService extends BaseMapper {

    @Autowired
    private PmdMemberService pmdMemberService;
    @Autowired
    private PmdBranchService pmdBranchService;
    @Autowired
    private PmdMonthService pmdMonthService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private PmdPartyService pmdPartyService;
    @Autowired
    private PmdPartyAdminService pmdPartyAdminService;
    @Autowired
    private PmdBranchAdminService pmdBranchAdminService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


    private Logger logger = LoggerFactory.getLogger(getClass());

    // 订单号起始值
    public final static int orderNoOffset = 10240000;
    // 新建订单号，缴费时的月份(yyyyMM) + 是否补缴(0,1) + (党员快照ID + 订单号起始值)
    public String createOrderNo(int pmdMemberId, PmdMonth currentPmdMonth,
                                boolean isDelay, byte payWay) {

        int _orderNo = pmdMemberId + orderNoOffset;
        if (_orderNo > 99999999) {
            throw new OpException("缴费失败，原因：订单号错误。");
        }

        return DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyyMM")
                + (isDelay ? "1" : "0")
                + payWay
                + _orderNo;
    }

    // 现金支付
    @Transactional
    public void payCash(int memberId) {

        checkAdmin(memberId);

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
            record.setIsOnlinePay(false);
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
        record.setIsOnlinePay(false);
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

        checkAdmin(pmdMemberId);

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

    // 取消延迟缴费
    @Transactional
    public void unDelay(int pmdMemberId) {

        checkAdmin(pmdMemberId);

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

    // 检测支部或直属党支部的操作权限
    public PmdMember checkAdmin(int pmdMemberId) {

        int userId = ShiroHelper.getCurrentUserId();

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        int currentMonthId = currentPmdMonth.getId();

        PmdMember pmdMember = pmdMemberMapper.selectByPrimaryKey(pmdMemberId);
        // 当前所在的单位快照
        PmdMember _pmdMember = pmdMemberService.get(currentMonthId, pmdMember.getUserId());

        // 检测党支部或直属党支部是否已经报送了
        Integer partyId = _pmdMember.getPartyId();
        Integer branchId = _pmdMember.getBranchId();

        if (partyService.isDirectBranch(partyId)) {

            List<Integer> adminPartyIds = pmdPartyAdminService.getAdminPartyIds(userId);
            Set<Integer> adminPartyIdSet = new HashSet<>();
            adminPartyIdSet.addAll(adminPartyIds);
            if (!adminPartyIdSet.contains(partyId)) {
                throw new UnauthorizedException();
            }

            PmdParty pmdParty = pmdPartyService.get(currentMonthId, partyId);
            if (pmdParty == null || pmdParty.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        } else {
            List<Integer> adminBranchIds = pmdBranchAdminService.getAdminBranchIds(userId);
            Set<Integer> adminBranchIdSet = new HashSet<>();
            adminBranchIdSet.addAll(adminBranchIds);
            if (!adminBranchIdSet.contains(branchId)) {
                throw new UnauthorizedException();
            }

            PmdBranch pmdBranch = pmdBranchService.get(currentMonthId, partyId, branchId);
            if (pmdBranch == null || pmdBranch.getHasReport()) {
                throw new OpException("数据已经报送，不允许操作。");
            }
        }
        return pmdMember;
    }
}
