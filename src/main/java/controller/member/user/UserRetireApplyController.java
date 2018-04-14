package controller.member.user;

import controller.member.MemberBaseController;
import domain.party.Branch;
import domain.party.Party;
import domain.party.RetireApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.LogConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserRetireApplyController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequiresRoles(RoleConstants.ROLE_MEMBER)
    @RequestMapping("/retireApply")
    public String retireApply(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        modelMap.put("sysUser", loginUser);

        RetireApply retireApply = retireApplyService.get(loginUser.getId());
        modelMap.put("retireApply", retireApply);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);

        if(retireApply!=null) {
            Integer partyId = retireApply.getPartyId();
            Integer branchId = retireApply.getBranchId();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
        }

        if(retireApply==null) {

            if(retireApply!=null){
                modelMap.put("party", partyService.findAll().get(retireApply.getPartyId()));
                if(retireApply.getBranchId()!=null){
                    modelMap.put("branch", branchService.findAll().get(retireApply.getBranchId()));
                }
            }
            return "member/user/retireApply/retireApply_au";
        }

        return "member/user/retireApply/retireApply";
    }
    @RequiresRoles(RoleConstants.ROLE_MEMBER)
    @RequestMapping(value = "/retireApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_retireApply_au(@CurrentUser SysUserView loginUser, RetireApply retireApply, HttpServletRequest request) {

        retireApply.setUserId(loginUser.getId());
        retireApply.setApplyId(loginUser.getId());
        retireApply.setCreateTime(new Date());
        retireApply.setStatus(OwConstants.OW_RETIRE_APPLY_STATUS_UNCHECKED);

        retireApplyService.insertSelective(retireApply);
        logger.info(addLog(LogConstants.LOG_USER, "党员退休-本人提交"));
        return success(FormUtils.SUCCESS);
    }
}
