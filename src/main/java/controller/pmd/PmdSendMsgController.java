package controller.pmd;

import controller.PmdBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lm on 2017/12/10.
 */
@Controller
@RequestMapping("/pmd")
public class PmdSendMsgController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 通知个人
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notify")
    public String pmdMember_notify(int id, ModelMap modelMap) {

        String msg = pmdSendMsgService.notifyMemberMsg(id);
        modelMap.put("msg", msg);

        return "pmd/pmdSendMsg/pmdSendMsg_notify";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notify(int id, HttpServletRequest request) {

        /*if(StringUtils.isBlank(mobile) || !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误");
        }*/

        pmdSendMsgService.notifyMember(id);

        logger.info(addLog(SystemConstants.LOG_PMD, "通知未缴费党员：%s", id));
        return success(FormUtils.SUCCESS);
    }

    // 通知所有分党委
    @RequiresPermissions("pmdSendMsg:notifyAllPartyAdmins")
    @RequestMapping(value = "/pmdSendMsg_notifyAllPartyAdmins", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyAllPartyAdmins(HttpServletRequest request) {

        pmdSendMsgService.notifyAllPartyAdmins();

        logger.info(addLog(SystemConstants.LOG_PMD, "通知所有分党委管理员"));
        return success(FormUtils.SUCCESS);
    }

    // 通知分党委的所有支部
    @RequiresPermissions("pmdSendMsg:notifyAllBranchAdmins")
    @RequestMapping(value = "/pmdSendMsg_notifyAllBranchAdmins", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyAllBranchAdmins(int partyId, HttpServletRequest request) {

        pmdSendMsgService.notifyAllBranchAdmins(partyId);

        logger.info(addLog(SystemConstants.LOG_PMD, "通知分党委的所有支部管理员"));
        return success(FormUtils.SUCCESS);
    }

    // 通知某个支部的所有未缴费党员
    @RequiresPermissions("pmdSendMsg:notifyAllMembers")
    @RequestMapping(value = "/pmdSendMsg_notifyAllMembers", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyAllMembers(int partyId, Integer branchId, HttpServletRequest request) {

        pmdSendMsgService.notifyAllMembers(partyId, branchId);

        logger.info(addLog(SystemConstants.LOG_PMD, "通知支部的所有未缴费党员"));
        return success(FormUtils.SUCCESS);
    }
}
