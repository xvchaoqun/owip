package controller.user;

import controller.BaseController;
import domain.Branch;
import domain.Party;
import domain.RetireApply;
import domain.SysUser;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserRetireApplyController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequiresRoles("member")
    @RequestMapping("/retireApply")
    public String retireApply(@CurrentUser SysUser loginUser, ModelMap modelMap) {

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
            return "user/retireApply/retireApply_au";
        }

        return "user/retireApply/retireApply";
    }
    @RequiresRoles("member")
    @RequestMapping(value = "/retireApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_retireApply_au(@CurrentUser SysUser loginUser, RetireApply retireApply, HttpServletRequest request) {

        retireApply.setUserId(loginUser.getId());
        retireApply.setApplyId(loginUser.getId());
        retireApply.setCreateTime(new Date());
        retireApply.setStatus(SystemConstants.RETIRE_APPLY_STATUS_UNCHECKED);

        retireApplyService.insertSelective(retireApply);
        logger.info(addLog(SystemConstants.LOG_USER, "党员退休-本人提交"));
        return success(FormUtils.SUCCESS);
    }
}
