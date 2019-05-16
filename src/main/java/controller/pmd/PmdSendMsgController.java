package controller.pmd;

import domain.base.ContentTpl;
import domain.party.Branch;
import domain.party.Party;
import domain.pmd.PmdMemberExample;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.ContentTplConstants;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by lm on 2017/12/10.
 */
@Controller
@RequestMapping("/pmd")
public class PmdSendMsgController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 本月党费收缴已经启动，短信通知本支部党员缴纳党费
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notifyMembers")
    public String pmdSendMsg_notifyMembers(int partyId, Integer branchId, ModelMap modelMap) {

        String msg = pmdSendMsgService.notifyMembersMsg(partyId, branchId);
        modelMap.put("msg", msg);

        return "pmd/pmdSendMsg/pmdSendMsg_notifyMembers";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notifyMembers", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyMembers(int partyId, Integer branchId, HttpServletRequest request) {

        /*if(StringUtils.isBlank(mobile) || !CmTag.validMobile(mobile)){
            return failed("手机号码有误");
        }*/

        pmdSendMsgService.notifyMembers(partyId, branchId);

        logger.info(addLog(LogConstants.LOG_PMD, "本月党费收缴已经启动，短信通知本支部党员缴纳党费"));
        return success(FormUtils.SUCCESS);
    }

    // 通知所有分党委管理员
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notifyPartyAdmins")
    public String pmdSendMsg_notifyPartyAdmins(ModelMap modelMap) {

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_PARTY);
        String msg = MessageFormat.format(tpl.getContent(), DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyy年MM月"));

        modelMap.put("msg", msg);

        return "pmd/pmdSendMsg/pmdSendMsg_notifyPartyAdmins";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notifyPartyAdmins", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyPartyAdmins(HttpServletRequest request) {

        SecurityUtils.getSubject().checkRole(RoleConstants.ROLE_PMD_OW);

        pmdSendMsgService.notifyPartyAdmins();

        logger.info(addLog(LogConstants.LOG_PMD, "通知所有分党委管理员"));
        return success(FormUtils.SUCCESS);
    }

    // 通知某个分党委的所有支部管理员
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_notifyBranchAdmins")
    public String pmdSendMsg_notifyBranchAdmins(int partyId, ModelMap modelMap) {

        Party party = partyService.findAll().get(partyId);

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_NOTIFY_BRANCH);
        String msg = MessageFormat.format(tpl.getContent(),
                "党支部",
                DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyy年MM月"),
                party.getName());

        modelMap.put("msg", msg);
        modelMap.put("party", partyService.findAll().get(partyId));

        return "pmd/pmdSendMsg/pmdSendMsg_notifyBranchAdmins";
    }


    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_notifyBranchAdmins", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_notifyBranchAdmins(int partyId, HttpServletRequest request) {

        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdPartyAdminService.isPartyAdmin(ShiroHelper.getCurrentUserId(), partyId)) {
                throw new UnauthorizedException();
            }
        }

        pmdSendMsgService.notifyBranchAdmins(partyId);

        logger.info(addLog(LogConstants.LOG_PMD, "通知分党委的所有支部管理员"));
        return success(FormUtils.SUCCESS);
    }

    // 通知某个支部的所有未缴费党员
    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping("/pmdSendMsg_urgeMembers")
    public String pmdSendMsg_urgeMembers(@RequestParam(required = false, value = "ids[]") Integer[] ids,
                                         int partyId, Integer branchId, ModelMap modelMap) {

        Party party = partyService.findAll().get(partyId);
        String branchName = party.getName();
        if(branchId!=null){
            Branch branch = branchService.findAll().get(branchId);
            branchName = branch.getName();
        }

        PmdMonth currentPmdMonth = pmdMonthService.getCurrentPmdMonth();
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PMD_URGE_MEMBERS);
        String msg = MessageFormat.format(tpl.getContent(),
                DateUtils.formatDate(currentPmdMonth.getPayMonth(), "yyyy年MM月"),
                branchName);

        modelMap.put("msg", msg);

        modelMap.put("branchName", branchName);


        Integer monthId = currentPmdMonth.getId();
        PmdMemberExample example = new PmdMemberExample();
        PmdMemberExample.Criteria criteria =
                example.createCriteria().andMonthIdEqualTo(monthId)
                        .andPartyIdEqualTo(partyId)
                        .andHasPayEqualTo(false)
                        .andIsDelayEqualTo(false);
        if(branchId!=null){
            criteria.andBranchIdEqualTo(branchId);
        }
        if(ids!=null && ids.length>0){
            criteria.andIdIn(Arrays.asList(ids));
        }
        modelMap.put("count", pmdMemberMapper.countByExample(example));

        return "pmd/pmdSendMsg/pmdSendMsg_urgeMembers";
    }

    @RequiresPermissions("pmdSendMsg:notify")
    @RequestMapping(value = "/pmdSendMsg_urgeMembers", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdSendMsg_urgeMembers(@RequestParam(required = false, value = "ids[]") Integer[] ids,
                                         int partyId, Integer branchId, HttpServletRequest request) {

        if(ShiroHelper.lackRole(RoleConstants.ROLE_PMD_OW)) {
            if (!pmdBranchAdminService.isBranchAdmin(ShiroHelper.getCurrentUserId(), partyId, branchId)) {
                throw new UnauthorizedException();
            }
        }

        pmdSendMsgService.urgeMembers(ids, partyId, branchId);

        logger.info(addLog(LogConstants.LOG_PMD, "通知支部的所有未缴费党员"));
        return success(FormUtils.SUCCESS);
    }
}
