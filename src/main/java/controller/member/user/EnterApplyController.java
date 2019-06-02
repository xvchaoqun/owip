package controller.member.user;

import controller.global.OpException;
import controller.member.MemberBaseController;
import domain.member.*;
import domain.party.Branch;
import domain.party.EnterApply;
import domain.party.Party;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.*;
import sys.shiro.CurrentUser;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

// 权限开通
@Controller
@RequestMapping("/user")
public class EnterApplyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/apply")
    public String apply(HttpServletResponse response, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        EnterApply currentApply = enterApplyService.getCurrentApply(userId);
        if (currentApply == null) {
            modelMap.put("applyList", enterApplyService.findApplyList(userId));
            modelMap.put("member", memberService.get(userId));
            return "member/user/enterApply/apply";
        }
        switch (currentApply.getType()) {
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY:
                return "forward:/user/memberApply";
            case OwConstants.OW_ENTER_APPLY_TYPE_RETURN:
                return "forward:/user/memberReturn";
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERIN:
                return "forward:/user/memberIn";
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERINFLOW:
                return "forward:/user/memberInflow";
        }

        throw new OpException("系统异常");
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/memberApply")
    public String memberApply(Boolean isMobile, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        EnterApply currentApply = enterApplyService.getCurrentApply(userId);
        isMobile = BooleanUtils.isTrue(isMobile);
        if (currentApply == null) {

            MemberApply memberApply = memberApplyService.get(ShiroHelper.getCurrentUserId());
            modelMap.put("memberApply", memberApply);

            if (memberApply != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Map<Integer, Party> partyMap = partyService.findAll();
                Integer partyId = memberApply.getPartyId();
                Integer branchId = memberApply.getBranchId();
                if (partyId != null) {
                    modelMap.put("party", partyMap.get(partyId));
                }
                if (branchId != null) {
                    modelMap.put("branch", branchMap.get(branchId));
                }
            }

            return isMobile?"member/mobile/memberApply":"member/user/enterApply/memberApply";
        } else {
            MemberApply memberApply = memberApplyService.get(ShiroHelper.getCurrentUserId());
            modelMap.put("memberApply", memberApply);

            return isMobile?"member/mobile/memberApply_view":"member/user/enterApply/memberApply_view";
        }
    }

    // 撤回申请
    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/applyBack", method = RequestMethod.POST)
    @ResponseBody
    public Map applyBack(@CurrentUser SysUserView loginUser, String remark) {

        int userId = loginUser.getId();
        enterApplyService.applyBack(userId, remark, OwConstants.OW_ENTER_APPLY_STATUS_SELF_ABORT);

        logger.info(addLog(LogConstants.LOG_USER, "撤回申请"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply(@CurrentUser SysUserView loginUser, Integer partyId,
                              Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

        enterApplyService.checkMemberApplyAuth(loginUser.getId());

        MemberApply memberApply = new MemberApply();
        memberApply.setUserId(loginUser.getId());

        if (loginUser.getType() == SystemConstants.USER_TYPE_JZG) {
            memberApply.setType(OwConstants.OW_APPLY_TYPE_TEACHER); // 教职工
        } else if (loginUser.getType() == SystemConstants.USER_TYPE_BKS
                || loginUser.getType() == SystemConstants.USER_TYPE_YJS) {
            memberApply.setType(OwConstants.OW_APPLY_TYPE_STU); // 学生
        } else {
            return failed("没有权限。");
        }

        Date birth = loginUser.getBirth();
        if (birth != null && DateUtils.intervalYearsUntilNow(birth) < 18) {
            return failed("您未满18周岁，不能申请入党。");
        }

        memberApply.setPartyId(partyId);
        memberApply.setBranchId(branchId);

        Date applyTime = DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD);
        if (applyTime == null) {
            return failed("提交书面申请书时间不允许为空。");
        }

        memberApply.setApplyTime(applyTime);
        memberApply.setRemark(remark);
        memberApply.setFillTime(new Date());
        memberApply.setCreateTime(new Date());
        memberApply.setStage(OwConstants.OW_APPLY_STAGE_INIT);
        enterApplyService.memberApply(memberApply);

        applyApprovalLogService.add(loginUser.getId(),
                memberApply.getPartyId(), memberApply.getBranchId(), loginUser.getId(),
                loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_INIT),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交入党申请");

        logger.info(addLog(LogConstants.LOG_MEMBER, "提交入党申请"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/memberReturn")
    public String memberReturn(Boolean isMobile, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        EnterApply currentApply = enterApplyService.getCurrentApply(userId);
        isMobile = BooleanUtils.isTrue(isMobile);

        if (currentApply == null) {

            MemberReturn memberReturn = memberReturnService.get(userId);
            modelMap.put("memberReturn", memberReturn);

            if (memberReturn != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Map<Integer, Party> partyMap = partyService.findAll();
                Integer partyId = memberReturn.getPartyId();
                Integer branchId = memberReturn.getBranchId();
                if (partyId != null) {
                    modelMap.put("party", partyMap.get(partyId));
                }
                if (branchId != null) {
                    modelMap.put("branch", branchMap.get(branchId));
                }
            }
            return isMobile?"member/mobile/memberReturn":"member/user/enterApply/memberReturn";
        } else {

            MemberReturn memberReturn = memberReturnService.get(userId);
            modelMap.put("memberReturn", memberReturn);

            modelMap.put("partyMap", partyService.findAll());
            modelMap.put("branchMap", branchService.findAll());

            return isMobile?"member/mobile/memberReturn_view":"member/user/enterApply/memberReturn_view";
        }
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberReturn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn(@CurrentUser SysUserView loginUser, String _returnApplyTime,
                               String _applyTime, String _activeTime, String _candidateTime,
                               String _growTime, String _positiveTime,
                               MemberReturn record, HttpServletRequest request) {

        enterApplyService.checkMemberApplyAuth(loginUser.getId());

        record.setUserId(loginUser.getId());

        if (StringUtils.isNotBlank(_returnApplyTime)) {
            record.setReturnApplyTime(DateUtils.parseDate(_returnApplyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_applyTime)) {
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
            if (record.getApplyTime() != null && activeTime.before(record.getApplyTime())) {
                return failed("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
            record.setActiveTime(activeTime);
        }
        if (StringUtils.isNotBlank(_candidateTime)) {

            Date candidateTime = DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD);
            if (record.getActiveTime() != null && candidateTime.before(record.getActiveTime())) {
                return failed("确定为发展对象时间应该在确定为入党积极分子之后");
            }
            record.setCandidateTime(candidateTime);
        }
        if (StringUtils.isNotBlank(_growTime)) {

            Date growTime = DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD);
            if (record.getCandidateTime() != null && growTime.before(record.getCandidateTime())) {
                return failed("入党时间应该在确定为发展对象之后");
            }
            record.setGrowTime(growTime);
        }
        if (StringUtils.isNotBlank(_positiveTime)) {

            Date positiveTime = DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD);
            if (record.getGrowTime() != null && positiveTime.before(record.getGrowTime())) {
                return failed("转正时间应该在入党之后");
            }
            record.setPositiveTime(positiveTime);
        }

        enterApplyService.memberReturn(record);

        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), loginUser.getId(),
                loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN,
                "提交",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交留学归国申请");

        logger.info(addLog(LogConstants.LOG_USER, "留学归国申请"));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/memberIn")
    public String memberIn(Boolean isMobile, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        EnterApply currentApply = enterApplyService.getCurrentApply(userId);
        isMobile = BooleanUtils.isTrue(isMobile);

        if (currentApply == null) {

            modelMap.put("userBean", userBeanService.get(userId));
            MemberIn memberIn = memberInService.get(userId);
            // 允许转出后用原账号转入
            Member member = memberService.get(userId);
            if (member != null && member.getStatus() == MemberConstants.MEMBER_STATUS_TRANSFER) {
                if (memberIn == null)
                    memberIn = new MemberIn();
                memberIn.setPoliticalStatus(member.getPoliticalStatus());
                memberIn.setPartyId(member.getPartyId());
                memberIn.setBranchId(member.getBranchId());
                memberIn.setUserId(userId);
                memberIn.setApplyTime(member.getApplyTime());
                memberIn.setActiveTime(member.getActiveTime());
                memberIn.setCandidateTime(member.getCandidateTime());
                memberIn.setGrowTime(member.getGrowTime());
                memberIn.setPositiveTime(member.getPositiveTime());
            }
            modelMap.put("memberIn", memberIn);

            if (memberIn != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Map<Integer, Party> partyMap = partyService.findAll();
                Integer partyId = memberIn.getPartyId();
                Integer branchId = memberIn.getBranchId();
                if (partyId != null) {
                    modelMap.put("party", partyMap.get(partyId));
                }
                if (branchId != null) {
                    modelMap.put("branch", branchMap.get(branchId));
                }
            }

            return isMobile?"member/mobile/memberIn":"member/user/enterApply/memberIn";

        } else {
            MemberIn memberIn = memberInService.get(userId);
            modelMap.put("memberIn", memberIn);

            modelMap.put("partyMap", partyService.findAll());
            modelMap.put("branchMap", branchService.findAll());

            return isMobile?"member/mobile/memberIn_view":"member/user/enterApply/memberIn_view";
        }
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberIn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn(@CurrentUser SysUserView loginUser, MemberIn record, String _payTime, String _applyTime, String _activeTime, String _candidateTime,
                           String _growTime, String _positiveTime,
                           String _fromHandleTime, String _handleTime, HttpServletRequest request) {

        enterApplyService.checkMemberApplyAuth(loginUser.getId());

        record.setUserId(loginUser.getId());
        record.setHasReceipt(null);
        record.setReason(null);

        if (StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if (StringUtils.isNotBlank(_applyTime)) {
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_activeTime)) {
            Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
            if (record.getApplyTime() != null && activeTime.before(record.getApplyTime())) {
                return failed("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
            record.setActiveTime(activeTime);
        }
        if (StringUtils.isNotBlank(_candidateTime)) {

            Date candidateTime = DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD);
            if (record.getActiveTime() != null && candidateTime.before(record.getActiveTime())) {
                return failed("确定为发展对象时间应该在确定为入党积极分子之后");
            }
            record.setCandidateTime(candidateTime);
        }
        if (StringUtils.isNotBlank(_growTime)) {

            Date growTime = DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD);
            if (record.getCandidateTime() != null && growTime.before(record.getCandidateTime())) {
                return failed("入党时间应该在确定为发展对象之后");
            }
            record.setGrowTime(growTime);
        }
        if (StringUtils.isNotBlank(_positiveTime)) {

            Date positiveTime = DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD);
            if (record.getGrowTime() != null && positiveTime.before(record.getGrowTime())) {
                return failed("转正时间应该在入党之后");
            }
            record.setPositiveTime(positiveTime);
        }

        if (StringUtils.isNotBlank(_fromHandleTime)) {
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_handleTime)) {
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        enterApplyService.memberIn(record);

        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), loginUser.getId(),
                loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_IN,
                "提交",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交组织关系转入申请");

        logger.info(addLog(LogConstants.LOG_USER, "组织关系转入申请"));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/memberInflow")
    public String memberInflow(Boolean isMobile, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        EnterApply currentApply = enterApplyService.getCurrentApply(userId);
        isMobile = BooleanUtils.isTrue(isMobile);

        if (currentApply == null) {

            MemberInflow memberInflow = memberInflowService.get(userId);
            modelMap.put("memberInflow", memberInflow);
            if (memberInflow != null) {
                Map<Integer, Branch> branchMap = branchService.findAll();
                Map<Integer, Party> partyMap = partyService.findAll();
                Integer partyId = memberInflow.getPartyId();
                Integer branchId = memberInflow.getBranchId();
                if (partyId != null) {
                    modelMap.put("party", partyMap.get(partyId));
                }
                if (branchId != null) {
                    modelMap.put("branch", branchMap.get(branchId));
                }
            }
            return isMobile?"member/mobile/memberInflow":"member/user/enterApply/memberInflow";
        } else {
            MemberInflow memberInflow = memberInflowService.get(userId);
            modelMap.put("memberInflow", memberInflow);

            modelMap.put("locationMap", locationService.codeMap());
            return isMobile?"member/mobile/memberInflow_view":"member/user/enterApply/memberInflow_view";
        }
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberInflow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow(@CurrentUser SysUserView loginUser, MemberInflow record,
                               String _flowTime, String _growTime, HttpServletRequest request) {

        //
        record.setUserId(loginUser.getId());
        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());
        if (StringUtils.isNotBlank(_flowTime)) {
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_growTime)) {
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }

        if (record.getPartyId() != null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if (record.getBranchId() != null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        enterApplyService.memberInflow(record);

        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), loginUser.getId(),
                loginUser.getId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW,
                "提交",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交流入党员申请");

        logger.info(addLog(LogConstants.LOG_USER, "流入党员申请"));

        return success(FormUtils.SUCCESS);
    }
}
