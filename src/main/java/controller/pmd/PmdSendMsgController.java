package controller.pmd;

import controller.PmdBaseController;
import domain.base.ContentTpl;
import domain.party.Branch;
import domain.party.Party;
import domain.pmd.PmdMonth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
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

    // 通知所有分党委管理员
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notifyAllPartyAdmins")
    public String pmdSendMsg_notifyAllPartyAdmins(ModelMap modelMap) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_PARTY);
        String msg = MessageFormat.format(tpl.getContent(), DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyy年MM月"));

        modelMap.put("msg", msg);

        return "pmd/pmdSendMsg/pmdSendMsg_notifyAllPartyAdmins";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notifyAllPartyAdmins", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyAllPartyAdmins(HttpServletRequest request) {

        SecurityUtils.getSubject().checkRole(SystemConstants.ROLE_PMD_OW);

        pmdSendMsgService.notifyAllPartyAdmins();

        logger.info(addLog(SystemConstants.LOG_PMD, "通知所有分党委管理员"));
        return success(FormUtils.SUCCESS);
    }

    // 通知某个分党委的所有支部管理员
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notifyAllBranchAdmins")
    public String pmdSendMsg_notifyAllBranchAdmins(int partyId, ModelMap modelMap) {

        Party party = partyService.findAll().get(partyId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_BRANCH);
        String msg = MessageFormat.format(tpl.getContent(),
                "党支部",
                DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyy年MM月"),
                party.getName());

        modelMap.put("msg", msg);
        modelMap.put("party", partyService.findAll().get(partyId));

        return "pmd/pmdSendMsg/pmdSendMsg_notifyAllBranchAdmins";
    }


    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notifyAllBranchAdmins", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyAllBranchAdmins(int partyId, HttpServletRequest request) {

        if(ShiroHelper.lackRole(SystemConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        pmdSendMsgService.notifyAllBranchAdmins(partyId);

        logger.info(addLog(SystemConstants.LOG_PMD, "通知分党委的所有支部管理员"));
        return success(FormUtils.SUCCESS);
    }

    // 通知某个支部的所有未缴费党员
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notifyAllMembers")
    public String pmdSendMsg_notifyAllMembers(int partyId, Integer branchId, ModelMap modelMap) {

        Party party = partyService.findAll().get(partyId);
        String branchName = party.getName();
        if(branchId!=null){
            Branch branch = branchService.findAll().get(branchId);
            branchName = branch.getName();
        }

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        ContentTpl tpl = shortMsgService.getShortMsgTpl(SystemConstants.CONTENT_TPL_PMD_NOTIFY_MEMBER);
        String msg = MessageFormat.format(tpl.getContent(),
                DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyy年MM月"),
                branchName);

        modelMap.put("msg", msg);

        modelMap.put("branchName", branchName);

        return "pmd/pmdSendMsg/pmdSendMsg_notifyAllMembers";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notifyAllMembers", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyAllMembers(int partyId, Integer branchId, HttpServletRequest request) {

        if(ShiroHelper.lackRole(SystemConstants.ROLE_PMD_OW)) {
            if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                throw new UnauthorizedException();
            }
        }

        pmdSendMsgService.notifyAllMembers(partyId, branchId);

        logger.info(addLog(SystemConstants.LOG_PMD, "通知支部的所有未缴费党员"));
        return success(FormUtils.SUCCESS);
    }
}
