package controller.user;

import controller.BaseController;
import domain.Branch;
import domain.MemberOutflow;
import domain.Party;
import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserMemberOutflowController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("member")
    @RequestMapping("/memberOutflow")
    public String memberOutflow(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("sysUser", loginUser);

        MemberOutflow memberOutflow = memberOutflowService.get(loginUser.getId());
        modelMap.put("memberOutflow", memberOutflow);

        modelMap.put("locationMap", locationService.codeMap());
        modelMap.put("jobMap", metaTypeService.metaTypes("mc_job"));
        modelMap.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);

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

        if(memberOutflow==null || memberOutflow.getStatus()==SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK
                || memberOutflow.getStatus()==SystemConstants.MEMBER_OUTFLOW_STATUS_DENY)
            return "user/memberOutflow/memberOutflow_au";

        return "user/memberOutflow/memberOutflow";
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberOutflow_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOutflow_au(@CurrentUser SysUser loginUser,
                                   MemberOutflow record, String _flowTime, HttpServletRequest request) {

        if(StringUtils.isNotBlank(_flowTime)){
            record.setFlowTime(DateUtils.parseDate(_flowTime, DateUtils.YYYY_MM_DD));
        }
        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());

        if(record.getPartyId()!=null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if(record.getBranchId()!=null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        MemberOutflow memberOutflow = memberOutflowService.get(loginUser.getId());

        if(memberOutflow!=null && memberOutflow.getStatus()!=SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK
                && memberOutflow.getStatus()!=SystemConstants.MEMBER_OUTFLOW_STATUS_DENY)
            throw new RuntimeException("不允许修改");

        record.setUserId(loginUser.getId());
        record.setCreateTime(new Date());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);

        if(loginUser.getType()==SystemConstants.USER_TYPE_JZG)
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER);
        else
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT);

        if (memberOutflow == null) {
            memberOutflowService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "提交流出党员申请：%s", record.getId()));
        } else {

            memberOutflowService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "提交修改流出党员申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberOutflow_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberOutflow_back(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        memberOutflowService.back(userId);

        return success(FormUtils.SUCCESS);
    }
}
