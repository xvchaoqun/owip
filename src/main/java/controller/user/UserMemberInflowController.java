package controller.user;

import controller.BaseController;
import domain.Branch;
import domain.MemberInflow;
import domain.Party;
import domain.SysUser;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserMemberInflowController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("inflowMember")
    @RequestMapping("/memberInflow_base")
    public String memberInflow_base() {

        return "index";
    }

    @RequiresRoles("inflowMember")
    @RequestMapping("/memberInflow_base_page")
    public String memberInflow_base_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();
        MemberInflow memberInflow = memberInflowService.get(userId);
        modelMap.put("memberInflow", memberInflow);

        return "user/memberInflow/memberInflow_base";
    }

    @RequiresRoles("inflowMember")
    @RequestMapping("/memberInflowOut")
    public String memberInflowOut(@CurrentUser SysUser loginUser) {

        return "index";
    }

    @RequiresRoles("inflowMember")
    @RequestMapping("/memberInflowOut_page")
    public String memberInflowOut_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        MemberInflow memberInflow = memberInflowService.get(loginUser.getId());
        if(memberInflow==null || memberInflow.getInflowStatus()!=SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY){
            throw new RuntimeException("状态异常");
        }
        modelMap.put("memberInflow", memberInflow);
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

        if(memberInflow.getOutStatus()==null ||
                memberInflow.getOutStatus()<= SystemConstants.MEMBER_INFLOW_OUT_STATUS_BACK)
            return "user/memberInflow/memberInflowOut_au";

        return "user/memberInflow/memberInflowOut";
    }

    @RequiresRoles("inflowMember")
    @RequestMapping(value = "/memberInflowOut", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberInflowOut(@CurrentUser SysUser loginUser, String outUnit, Integer outLocation,
                               String _outTime, HttpServletRequest request) {


        MemberInflow memberInflow = memberInflowOutService.out(loginUser.getId(), outUnit, outLocation, _outTime, true);

        applyApprovalLogService.add(memberInflow.getId(),
                memberInflow.getPartyId(), memberInflow.getBranchId(), loginUser.getId(),
                loginUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交流入党员转出申请");

        logger.info(addLog(SystemConstants.LOG_USER, "流入党员转出申请"));

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("inflowMember")
    @RequestMapping(value = "/memberInflowOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberInflowOut_back(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        memberInflowOutService.back(userId);
        logger.info(addLog(SystemConstants.LOG_USER, "取消流入党员转出申请"));
        return success(FormUtils.SUCCESS);
    }

}
