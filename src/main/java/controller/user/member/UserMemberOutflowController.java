package controller.user.member;

import controller.BaseController;
import domain.member.MemberOutflow;
import domain.party.Branch;
import domain.party.Party;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserMemberOutflowController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/memberOutflow")
    public String memberOutflow(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        modelMap.put("sysUser", loginUser);

        MemberOutflow memberOutflow = memberOutflowService.get(loginUser.getId());
        modelMap.put("memberOutflow", memberOutflow);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        if(memberOutflow!=null) {
            Integer partyId = memberOutflow.getPartyId();
            Integer branchId = memberOutflow.getBranchId();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
        }

        if(memberOutflow==null
                || memberOutflow.getStatus()==SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK
                || memberOutflow.getStatus()==SystemConstants.MEMBER_OUTFLOW_STATUS_BACK)
            return "user/member/memberOutflow/memberOutflow_au";

        return "user/member/memberOutflow/memberOutflow";
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberOutflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_au(@CurrentUser SysUserView loginUser,
                                   MemberOutflow record, String _flowTime, HttpServletRequest request) {

        Integer userId = loginUser.getId();
        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }

        MemberOutflow memberOutflow = memberOutflowService.get(loginUser.getId());
        if(memberOutflow!=null && memberOutflow.getStatus()!=SystemConstants.MEMBER_OUTFLOW_STATUS_BACK
                && memberOutflow.getStatus()!=SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK)
            throw new RuntimeException("不允许修改");

        record.setUserId(userId);
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        record.setIsBack(false);
        if (memberOutflow == null) {
            memberOutflowService.add(record);
            logger.info(addLog(SystemConstants.LOG_USER, "提交流出党员申请"));
            memberOutflow = record;
        } else {
            memberOutflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "提交修改流出党员申请"));
        }
        applyApprovalLogService.add(memberOutflow.getId(),
                memberOutflow.getPartyId(), memberOutflow.getBranchId(), memberOutflow.getUserId(),
                userId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交流出党员申请");
        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberOutflow_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberOutflow_back(@CurrentUser SysUserView loginUser, String remark){

        int userId = loginUser.getId();
        memberOutflowService.back(userId);
        logger.info(addLog(SystemConstants.LOG_USER, "取消流出党员申请"));
        return success(FormUtils.SUCCESS);
    }
}
