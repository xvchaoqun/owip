package controller.pmd;

import domain.party.Branch;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pmd")
public class PmdBranchAdminController extends PmdBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pmdBranchAdmin:list")
    @RequestMapping("/pmdBranchAdmin")
    public String pmdBranchAdmin(int branchId, ModelMap modelMap) {

        modelMap.put("branch", branchService.findAll().get(branchId));
        modelMap.put("branchAdmins", pmdBranchAdminService.getAllPmdBranchAdmins(branchId));
        return "pmd/pmdPayBranch/pmdPayBranch_admin";
    }

    @RequiresPermissions("pmdBranchAdmin:edit")
    @RequestMapping(value = "/pmdBranchAdmin_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranchAdmin_add(int branchId, int userId, HttpServletRequest request) {

        if (pmdBranchAdminService.idDuplicate(null, branchId, userId)) {
            return failed("添加重复");
        }

        Branch branch = branchService.findAll().get(branchId);
        pmdBranchAdminService.add(branch.getPartyId(), branchId, userId);

        logger.info(addLog(LogConstants.LOG_PMD, "添加缴费党支部管理员：%s， %s", branchId, userId));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranchAdmin:del")
    @RequestMapping(value = "/pmdBranchAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranchAdmin_del(@CurrentUser SysUserView loginUser,
                                    HttpServletRequest request, Integer id) {

        pmdBranchAdminService.del(id);
        logger.info(addLog(LogConstants.LOG_PMD, "删除缴费党支部管理员：%s", id));
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pmdBranchAdmin:sync")
    @RequestMapping(value = "/pmdBranchAdmin_sync", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pmdBranchAdmin_sync() {

        List<Integer> adminPartyIds = new ArrayList<>();
         if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PMDVIEWALL)) {
             adminPartyIds = pmdPartyAdminService.getAdminPartyIds(ShiroHelper.getCurrentUserId());
         }else{
             adminPartyIds = pmdPartyAdminService.getAllPartyIds();
         }
        pmdBranchAdminService.syncBranchAdmins(adminPartyIds);
        logger.info(addLog(LogConstants.LOG_PMD, "同步缴费党支部管理员:%s", StringUtils.join(adminPartyIds, ",")));
        return success(FormUtils.SUCCESS);
    }
}
