package controller.user;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

// 权限开通
@Controller
@RequestMapping("/user")
public class EnterApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("guest")
    @RequestMapping("/apply")
    public String apply() {

        return "index";
    }

    @RequiresRoles("guest")
    @RequestMapping("/apply_page")
    public String apply_page(@CurrentUser SysUser loginUser, HttpServletResponse response, ModelMap modelMap) {

        Integer userId = loginUser.getId();
        EnterApply currentApply = enterApplyService.getCurrentApply(userId);
        if(currentApply==null) {
            modelMap.put("applyList", enterApplyService.findApplyList(userId));
            modelMap.put("member", memberService.get(userId));
            return "user/enterApply/apply";
        }
        switch (currentApply.getType()){
            case SystemConstants.ENTER_APPLY_TYPE_MEMBERAPPLY:
                return "forward:/user/memberApply_view";
            case SystemConstants.ENTER_APPLY_TYPE_RETURN:
                return "forward:/user/memberReturn_view";
             case SystemConstants.ENTER_APPLY_TYPE_MEMBERIN:
                return "forward:/user/memberIn_view";
             case SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW:
                return "forward:/user/memberInflow_view";
        }

        throw new RuntimeException("系统异常");
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberApply_view")
    public String memberApply_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberApply memberApply = memberApplyService.get(loginUser.getId());
        modelMap.put("memberApply", memberApply);

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("branchMap", branchService.findAll());


        return "user/enterApply/memberApply_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberApply")
    public String memberApply(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberApply memberApply = memberApplyService.get(loginUser.getId());
        modelMap.put("memberApply", memberApply);

        if(memberApply!=null){
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

        return "user/enterApply/memberApply";
    }

    // 撤回申请
    @RequiresRoles("guest")
    @RequestMapping(value = "/applyBack", method = RequestMethod.POST)
    @ResponseBody
    public Map applyBack(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        enterApplyService.applyBack(userId, remark, SystemConstants.ENTER_APPLY_STATUS_SELF_ABORT);

        logger.info(addLog(SystemConstants.LOG_USER, "取消入党申请"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply(@CurrentUser SysUser loginUser,Integer partyId,
                              Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

        enterApplyService.checkMemberApplyAuth(loginUser.getId());

        MemberApply memberApply = new MemberApply();
        memberApply.setUserId(loginUser.getId());

        if(loginUser.getType() == SystemConstants.USER_TYPE_JZG){
            memberApply.setType(SystemConstants.APPLY_TYPE_TECHER); // 教职工
        } else if(loginUser.getType() == SystemConstants.USER_TYPE_BKS
                || loginUser.getType() == SystemConstants.USER_TYPE_YJS){
            memberApply.setType(SystemConstants.APPLY_TYPE_STU); // 学生
        }else{
            throw new UnauthorizedException("没有权限");
        }

        Date birth = loginUser.getBirth();
        if(birth!=null && DateUtils.intervalYearsUntilNow(birth)<18){
            throw new RuntimeException("您未满18周岁，不能申请入党。");
        }

        memberApply.setPartyId(partyId);
        memberApply.setBranchId(branchId);
        memberApply.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        memberApply.setRemark(remark);
        memberApply.setFillTime(new Date());
        memberApply.setCreateTime(new Date());
        memberApply.setStage(SystemConstants.APPLY_STAGE_INIT);
        enterApplyService.memberApply(memberApply);

        applyApprovalLogService.add(loginUser.getId(),
                memberApply.getPartyId(), memberApply.getBranchId(), loginUser.getId(), loginUser.getId(),
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                SystemConstants.APPLY_STAGE_MAP.get(SystemConstants.APPLY_STAGE_INIT), (byte) 1,
                "提交入党申请");

        logger.info(addLog(SystemConstants.LOG_OW, "提交入党申请"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberReturn_view")
    public String memberReturn_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberReturn memberReturn = memberReturnService.get(loginUser.getId());
        modelMap.put("memberReturn", memberReturn);

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("branchMap", branchService.findAll());

        return "user/enterApply/memberReturn_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberReturn")
    public String memberReturn(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);

        MemberReturn memberReturn = memberReturnService.get(loginUser.getId());
        modelMap.put("memberReturn", memberReturn);

        if(memberReturn!=null){
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
        return "user/enterApply/memberReturn";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberReturn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn(@CurrentUser SysUser loginUser,String _applyTime, String _activeTime, String _candidateTime,
                               String _growTime, String _positiveTime,
                               MemberReturn record, HttpServletRequest request) {

        enterApplyService.checkMemberApplyAuth(loginUser.getId());

        record.setUserId(loginUser.getId());


        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
            if(activeTime.before(record.getApplyTime())){
                throw new RuntimeException("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
            record.setActiveTime(activeTime);
        }
        if(StringUtils.isNotBlank(_candidateTime)){

            Date candidateTime = DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD);
            if(candidateTime.before(record.getActiveTime())){
                throw new RuntimeException("确定为发展对象时间应该在确定为入党积极分子之后");
            }
            record.setCandidateTime(candidateTime);
        }
        if(StringUtils.isNotBlank(_growTime)){

            Date growTime = DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD);
            if(growTime.before(record.getCandidateTime())){
                throw new RuntimeException("入党时间应该在确定为发展对象之后");
            }
            record.setGrowTime(growTime);
        }
        if(StringUtils.isNotBlank(_positiveTime)){

            Date positiveTime = DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD);
            if(positiveTime.before(record.getGrowTime())){
                throw new RuntimeException("转正时间应该在入党之后");
            }
            record.setPositiveTime(positiveTime);
        }

        enterApplyService.memberReturn(record);

        logger.info(addLog(SystemConstants.LOG_USER, "留学归国申请"));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberIn_view")
    public String memberIn_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberIn memberIn = memberInService.get(loginUser.getId());
        modelMap.put("memberIn", memberIn);

        modelMap.put("partyMap", partyService.findAll());
        modelMap.put("branchMap", branchService.findAll());

        return "user/enterApply/memberIn_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberIn")
    public String memberIn(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();
        modelMap.put("userBean", userBeanService.get(userId));

        MemberIn memberIn = memberInService.get(userId);
        modelMap.put("memberIn", memberIn);

        if(memberIn!=null){
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

        return "user/enterApply/memberIn";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberIn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn(@CurrentUser SysUser loginUser, MemberIn record, String _payTime, String _applyTime, String _activeTime, String _candidateTime,
                           String _growTime, String _positiveTime,
                           String _fromHandleTime, String _handleTime, HttpServletRequest request) {

        enterApplyService.checkMemberApplyAuth(loginUser.getId());

        record.setUserId(loginUser.getId());
        record.setHasReceipt(null);
        record.setReason(null);

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_applyTime)){
            record.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_activeTime)){
            Date activeTime = DateUtils.parseDate(_activeTime, DateUtils.YYYY_MM_DD);
            if(activeTime.before(record.getApplyTime())){
                throw new RuntimeException("确定为入党积极分子时间不能早于提交书面申请书时间");
            }
            record.setActiveTime(activeTime);
        }
        if(StringUtils.isNotBlank(_candidateTime)){

            Date candidateTime = DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD);
            if(candidateTime.before(record.getActiveTime())){
                throw new RuntimeException("确定为发展对象时间应该在确定为入党积极分子之后");
            }
            record.setCandidateTime(candidateTime);
        }
        if(StringUtils.isNotBlank(_growTime)){

            Date growTime = DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD);
            if(growTime.before(record.getCandidateTime())){
                throw new RuntimeException("入党时间应该在确定为发展对象之后");
            }
            record.setGrowTime(growTime);
        }
        if(StringUtils.isNotBlank(_positiveTime)){

            Date positiveTime = DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD);
            if(positiveTime.before(record.getGrowTime())){
                throw new RuntimeException("转正时间应该在入党之后");
            }
            record.setPositiveTime(positiveTime);
        }

        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        enterApplyService.memberIn(record);

        logger.info(addLog(SystemConstants.LOG_USER, "组织关系转入申请"));

        return success(FormUtils.SUCCESS);
    }


    @RequiresRoles("guest")
    @RequestMapping("/memberInflow_view")
    public String memberInflow_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberInflow memberInflow = memberInflowService.get(loginUser.getId());
        modelMap.put("memberInflow", memberInflow);

        modelMap.put("locationMap", locationService.codeMap());
        return "user/enterApply/memberInflow_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberInflow")
    public String memberInflow(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);

        MemberInflow memberInflow = memberInflowService.get(loginUser.getId());
        modelMap.put("memberInflow", memberInflow);
        if(memberInflow!=null){
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
        return "user/enterApply/memberInflow";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberInflow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow(@CurrentUser SysUser loginUser,MemberInflow record,
                               String _flowTime, String _growTime, String _outflowTime, HttpServletRequest request) {

        //
        record.setUserId(loginUser.getId());
        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());
        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_growTime)){
            record.setGrowTime(DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(_outflowTime)) {
            record.setOutflowTime(DateUtils.parseDate(_outflowTime, DateUtils.YYYY_MM_DD));
        }
        if(record.getPartyId()!=null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if(record.getBranchId()!=null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        enterApplyService.memberInflow(record);

        logger.info(addLog(SystemConstants.LOG_USER, "流入党员申请"));

        return success(FormUtils.SUCCESS);
    }
}
