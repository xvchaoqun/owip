package controller.pmd;

import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdPartyAdminController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdPartyAdmin:list")
    @RequestMapping("/pmdPartyAdmin")
    public String pmdPartyAdmin(int partyId, ModelMap modelMap) {

        modelMap.put("party", partyService.findAll().get(partyId));
        modelMap.put("partyAdmins", pmdPartyAdminService.getAllPmdPartyAdmins(partyId));
        return "pmd/pmdPayParty/pmdPayParty_admin";
    }

    @RequiresPermissions("pmdPartyAdmin:edit")
    @RequestMapping(value = "/pmdPartyAdmin_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdPartyAdmin_add(int partyId, int userId, HttpServletRequest request) {

        if (pmdPartyAdminService.idDuplicate(null, partyId, userId)) {
            return failed("添加重复");
        }

        pmdPartyAdminService.add(partyId, userId);

        logger.info(addLog(SystemConstants.LOG_PMD, "添加缴费分党委管理员：%s， %s", partyId, userId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdPartyAdmin:del")
    @RequestMapping(value = "/pmdPartyAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdPartyAdmin_del(@CurrentUser SysUserView loginUser,
                                    HttpServletRequest request, Integer id) {

        pmdPartyAdminService.del(id);
        logger.info(addLog(SystemConstants.LOG_PMD, "删除缴费分党委管理员：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdPartyAdmin:sync")
    @RequestMapping(value = "/pmdPartyAdmin_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdPartyAdmin_sync() {

        pmdPartyAdminService.syncPartyAdmins();
        logger.info(addLog(SystemConstants.LOG_PMD, "同步缴费分党委管理员"));
        return success(FormUtils.SUCCESS);
    }
}
