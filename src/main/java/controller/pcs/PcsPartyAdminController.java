package controller.pcs;

import controller.BaseController;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsConfig;
import domain.sys.SysUserView;
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
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPartyAdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPartyAdmin:list")
    @RequestMapping("/pcsPartyAdmin")
    public String pcsPartyAdmin(ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null || pcsAdmin.getType() != SystemConstants.PCS_ADMIN_TYPE_SECRETARY) {
            throw new UnauthorizedException();
        }

        Integer partyId = pcsAdmin.getPartyId();
        modelMap.put("party", partyService.findAll().get(partyId));

        {
            PcsAdminExample example = new PcsAdminExample();
            example.createCriteria().andPartyIdEqualTo(partyId);
            example.setOrderByClause("type asc");
            List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);
            modelMap.put("pcsAdmins", pcsAdmins);
        }

        {
            PcsAdminExample example = new PcsAdminExample();
            example.createCriteria().andPartyIdEqualTo(partyId)
                    .andTypeEqualTo(SystemConstants.PCS_ADMIN_TYPE_NORMAL);
            if (pcsAdminMapper.countByExample(example) > 0) {
                modelMap.put("hasNormalAdmin", true);
            }
        }

        return "pcs/pcsPartyAdmin/pcsPartyAdmin_page";
    }

    // 分党委书记添加普通管理员
    @RequiresPermissions("pcsPartyAdmin:edit")
    @RequestMapping(value = "/pcsPartyAdmin_add", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPartyAdmin_add(int userId, HttpServletRequest request) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null || pcsAdmin.getType() != SystemConstants.PCS_ADMIN_TYPE_SECRETARY) {
            throw new UnauthorizedException();
        }
        int partyId = pcsAdmin.getPartyId();

        PcsAdminExample example = new PcsAdminExample();
        example.createCriteria().andPartyIdEqualTo(partyId)
                .andTypeEqualTo(SystemConstants.PCS_ADMIN_TYPE_NORMAL);
        if (pcsAdminMapper.countByExample(example) > 0) {
            return failed("已经存在党代会管理员，请不要重复添加");
        }

        PcsConfig pcsConfig = pcsConfigService.getCurrentPcsConfig();
        if(pcsConfig==null){
            return failed("目前没有召开党代会，不可以添加管理员");
        }

        PcsAdmin record = new PcsAdmin();
        record.setConfigId(pcsConfig.getId());
        record.setUserId(userId);
        record.setType(SystemConstants.PCS_ADMIN_TYPE_NORMAL);
        record.setPartyId(partyId);

        if (pcsAdminService.idDuplicate(null, userId)) {
            return failed("该用户已经是党代会管理员");
        }

        pcsAdminService.add(record);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "[分党委书记]添加党代会分党委管理员：%s", record.getId()));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPartyAdmin:edit")
    @RequestMapping("/pcsPartyAdmin_add")
    public String pcsPartyAdmin_add(ModelMap modelMap) {

        modelMap.put("pcsConfig", pcsConfigService.getCurrentPcsConfig());

        return "pcs/pcsPartyAdmin/pcsPartyAdmin_add";
    }

    // 分党委书记删除普通管理员
    @RequiresPermissions("pcsPartyAdmin:del")
    @RequestMapping(value = "/pcsPartyAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPartyAdmin_del(HttpServletRequest request, Integer id, ModelMap modelMap) {

        PcsAdmin admin = pcsAdminMapper.selectByPrimaryKey(id);
        if (admin == null) return success();

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null || pcsAdmin.getType() != SystemConstants.PCS_ADMIN_TYPE_SECRETARY
                || admin.getType() != SystemConstants.PCS_ADMIN_TYPE_NORMAL
                || admin.getPartyId().intValue() != pcsAdmin.getPartyId()) {
            throw new UnauthorizedException();
        }

        pcsAdminService.batchDel(new Integer[]{id});
        SysUserView user = admin.getUser();
        logger.info(addLog(SystemConstants.LOG_ADMIN, "[分党委书记]删除党代会分党委管理员-%s(%s)", user.getRealname(), user.getCode()));

        return success(FormUtils.SUCCESS);
    }
}
