package controller.user;

import controller.BaseController;
import domain.*;
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
import sys.utils.IpUtils;

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

        EnterApply currentApply = enterApplyService.getCurrentApply(loginUser.getId());
        if(currentApply==null) {
            return "user/enterApply/apply";
        }
        if(currentApply.getType()==SystemConstants.ENTER_APPLY_TYPE_MEMBERAPPLY){
            return "foward:/user/memberApply_view";
        }
        if(currentApply.getType()==SystemConstants.ENTER_APPLY_TYPE_RETURN){
            return "foward:/user/memberReturn_view";
        }
        if(currentApply.getType()==SystemConstants.ENTER_APPLY_TYPE_MEMBERIN){
            return "foward:/user/memberIn_view";
        }
        if(currentApply.getType()==SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW){
            return "foward:/user/memberInflow_view";
        }

        throw new RuntimeException("系统异常");
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberApply_view")
    public String memberApply_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {


        MemberApply memberApply = memberApplyService.get(loginUser.getId());
        modelMap.put("memberApply", memberApply);
        if(memberApply==null)
            modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));

        return "user/enterApply/memberApply_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberApply")
    public String memberApply(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberApply memberApply = memberApplyService.get(loginUser.getId());
        modelMap.put("memberApply", memberApply);
        if(memberApply==null)
            modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));

        return "user/enterApply/memberApply";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberApply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberApply(@CurrentUser SysUser loginUser,Integer partyId, Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

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
        memberApply.setPartyId(partyId);
        memberApply.setBranchId(branchId);
        memberApply.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        memberApply.setRemark(remark);
        memberApply.setFillTime(new Date());
        memberApply.setCreateTime(new Date());
        memberApply.setStage(SystemConstants.APPLY_STAGE_INIT);
        memberApplyService.insertSelective(memberApply);

        applyLogService.addApplyLog(loginUser.getId(), loginUser.getId(),
                SystemConstants.APPLY_STAGE_INIT, "提交入党申请", IpUtils.getIp(request));
        logger.info(addLog(request, SystemConstants.LOG_OW, "提交入党申请"));
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberRetun_view")
    public String memberRetun_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberReturn memberRetun = memberReturnService.get(loginUser.getId());
        modelMap.put("memberRetun", memberRetun);

        return "user/enterApply/memberRetun_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberReturn")
    public String memberReturn(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        MemberReturn memberReturn = memberReturnService.get(loginUser.getId());
        modelMap.put("memberReturn", memberReturn);
        if(memberReturn==null)
            modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));
        return "user/enterApply/memberReturn";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberReturn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberReturn(@CurrentUser SysUser loginUser,Integer partyId,
                              Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

       //
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberIn_view")
    public String memberIn_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberIn memberIn = memberInService.get(loginUser.getId());
        modelMap.put("memberIn", memberIn);

        return "user/enterApply/memberIn_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberIn")
    public String memberIn(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        MemberIn memberIn = memberInService.get(loginUser.getId());
        modelMap.put("memberIn", memberIn);
        if(memberIn==null)
            modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));

        return "user/enterApply/memberIn";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberIn", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberIn(@CurrentUser SysUser loginUser,Integer partyId,
                              Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

        //
        return success(FormUtils.SUCCESS);
    }


    @RequiresRoles("guest")
    @RequestMapping("/memberInflow_view")
    public String memberInflow_view(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberInflow memberInflow = memberInflowService.get(loginUser.getId());
        modelMap.put("memberInflow", memberInflow);

        return "user/enterApply/memberInflow_view";
    }

    @RequiresRoles("guest")
    @RequestMapping("/memberInflow")
    public String memberInflow(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        MemberInflow memberInflow = memberInflowService.get(loginUser.getId());
        modelMap.put("memberInflow", memberInflow);
        if(memberInflow==null)
            modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));
        return "user/enterApply/memberInflow";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/memberInflow", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflow(@CurrentUser SysUser loginUser,Integer partyId,
                           Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

        //
        return success(FormUtils.SUCCESS);
    }
}
