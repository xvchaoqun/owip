package controller.member.user;

import controller.member.MemberBaseController;
import domain.member.*;
import domain.party.Branch;
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
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

// 权限开通
@Controller
@RequestMapping("/user")
public class EnterApplyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/apply")
    public String apply(ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        Byte currentApplyType = enterApplyService.getCurrentApplyType(userId);
        if (currentApplyType == null) {
            modelMap.put("member", memberService.get(userId));
            return "member/user/enterApply/apply";
        }

        switch (currentApplyType) {
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERAPPLY:
                return "forward:/user/memberApply";
            case OwConstants.OW_ENTER_APPLY_TYPE_RETURN:
                return "forward:/user/memberReturn";
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERIN:
                return "forward:/user/memberIn";
            case OwConstants.OW_ENTER_APPLY_TYPE_MEMBERINFLOW:
                return "forward:/user/memberInflow";
        }

        return null;
    }

    //@RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/memberApply")
    public String memberApply(Boolean isMobile, Integer userId, boolean preview, ModelMap modelMap) {

        if(!ShiroHelper.isPermitted("memberApply:admin")){
            ShiroHelper.checkRole(RoleConstants.ROLE_GUEST);
            userId = ShiroHelper.getCurrentUserId();
        }

        if(userId==null){
            userId = ShiroHelper.getCurrentUserId();
        }
        MemberApply memberApply = memberApplyService.get(userId);
        isMobile = BooleanUtils.isTrue(isMobile);
        if (memberApply == null || memberApply.getStage()<OwConstants.OW_APPLY_STAGE_INIT || preview) {

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
            modelMap.put("memberApply", memberApply);

            return isMobile?"member/mobile/memberApply_view":"member/user/enterApply/memberApply_view";
        }
    }

    // 撤回申请
    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/applyBack", method = RequestMethod.POST)
    @ResponseBody
    public Map applyBack(byte type, String remark) {

        int userId = ShiroHelper.getCurrentUserId();
        enterApplyService.applyBack(userId, remark, type, OwConstants.OW_ENTER_APPLY_STATUS_SELF_ABORT);

        logger.info(addLog(LogConstants.LOG_USER, "撤回申请"));
        return success(FormUtils.SUCCESS);
    }

    //@RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply(Integer partyId, MemberApply memberApply,
                              Integer branchId, String remark, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        enterApplyService.checkMemberApplyAuth(userId);

        SysUserView uv = CmTag.getUserById(userId);
        //MemberApply memberApply = new MemberApply();
        memberApply.setUserId(userId);

        if (uv.getType() == SystemConstants.USER_TYPE_JZG) {
            memberApply.setType(OwConstants.OW_APPLY_TYPE_TEACHER); // 教职工
        } else if (uv.getType() == SystemConstants.USER_TYPE_BKS
                || uv.getType() == SystemConstants.USER_TYPE_YJS) {
            memberApply.setType(OwConstants.OW_APPLY_TYPE_STU); // 学生
        } else {
            return failed("没有权限。");
        }

        Date birth = uv.getBirth();
        if (birth != null && DateUtils.intervalYearsUntilNow(birth) < 18) {
            return failed("您未满18周岁，不能申请入党。");
        }

        memberApply.setPartyId(partyId);
        memberApply.setBranchId(branchId);

        if (memberApply.getApplyTime() == null) {
            return failed("提交书面申请书时间不允许为空。");
        }

        memberApply.setRemark(remark);
        memberApply.setFillTime(new Date());
        memberApply.setCreateTime(new Date());
        memberApply.setStage(OwConstants.OW_APPLY_STAGE_INIT);

        if(memberApply.getApplyStage()==null){
            memberApply.setApplyStage(OwConstants.OW_APPLY_STAGE_INIT);
        }

        enterApplyService.memberApply(memberApply);

        applyApprovalLogService.add(userId,
                memberApply.getPartyId(), memberApply.getBranchId(), userId,
                userId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_INIT),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                memberApply.getApplyStage()==OwConstants.OW_APPLY_STAGE_INIT?"提交入党申请":"提交继续培养申请");

        logger.info(addLog(LogConstants.LOG_MEMBER, memberApply.getApplyStage()==OwConstants.OW_APPLY_STAGE_INIT?"提交入党申请":"提交继续培养申请"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping("/memberReturn")
    public String memberReturn(Boolean isMobile, ModelMap modelMap) {

        Integer userId = ShiroHelper.getCurrentUserId();
        isMobile = BooleanUtils.isTrue(isMobile);

        MemberReturn memberReturn = memberReturnService.get(userId);
        if (memberReturn == null || memberReturn.getStatus()<MemberConstants.MEMBER_RETURN_STATUS_APPLY) {

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

            modelMap.put("memberReturn", memberReturn);

            modelMap.put("partyMap", partyService.findAll());
            modelMap.put("branchMap", branchService.findAll());

            return isMobile?"member/mobile/memberReturn_view":"member/user/enterApply/memberReturn_view";
        }
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberReturn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn(String _returnApplyTime,
                               String _applyTime, String _activeTime, String _candidateTime,
                               String _growTime, String _positiveTime,
                               MemberReturn record, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        enterApplyService.checkMemberApplyAuth(userId);

        record.setUserId(userId);

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
                record.getPartyId(), record.getBranchId(), userId,
                userId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
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
        isMobile = BooleanUtils.isTrue(isMobile);

        MemberIn memberIn = memberInService.get(userId);
        if (memberIn == null || memberIn.getStatus()<MemberConstants.MEMBER_IN_STATUS_APPLY) {

            modelMap.put("userBean", userBeanService.get(userId));
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

            modelMap.put("memberIn", memberIn);

            return isMobile?"member/mobile/memberIn":"member/user/enterApply/memberIn";

        } else {

            modelMap.put("memberIn", memberIn);
            modelMap.put("partyMap", partyService.findAll());
            modelMap.put("branchMap", branchService.findAll());

            return isMobile?"member/mobile/memberIn_view":"member/user/enterApply/memberIn_view";
        }
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberIn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn(MemberIn record, String _payTime, String _applyTime, String _activeTime, String _candidateTime,
                           String _growTime, String _positiveTime,
                           String _fromHandleTime, String _handleTime, HttpServletRequest request) {

        int userId = ShiroHelper.getCurrentUserId();
        enterApplyService.checkMemberApplyAuth(userId);

        record.setUserId(userId);
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
                record.getPartyId(), record.getBranchId(), userId,
                userId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
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
        isMobile = BooleanUtils.isTrue(isMobile);

        MemberInflow memberInflow = memberInflowService.get(userId);
        modelMap.put("memberInflow", memberInflow);
        if (memberInflow == null || memberInflow.getInflowStatus()<MemberConstants.MEMBER_INFLOW_OUT_STATUS_APPLY) {

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
            modelMap.put("locationMap", locationService.codeMap());
            return isMobile?"member/mobile/memberInflow_view":"member/user/enterApply/memberInflow_view";
        }
    }

    @RequiresRoles(RoleConstants.ROLE_GUEST)
    @RequestMapping(value = "/memberInflow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow(MemberInflow record,
                               String _flowTime, String _growTime, HttpServletRequest request) {

        //
        int userId = ShiroHelper.getCurrentUserId();
        record.setUserId(userId);
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
                record.getPartyId(), record.getBranchId(), userId,
                userId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW,
                "提交",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交流入党员申请");

        logger.info(addLog(LogConstants.LOG_USER, "流入党员申请"));

        return success(FormUtils.SUCCESS);
    }
}
