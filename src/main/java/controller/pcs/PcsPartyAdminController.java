package controller.pcs;

import controller.PcsBaseController;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
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
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PcsPartyAdminController extends PcsBaseController {

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

      /*  {
            PcsAdminExample example = new PcsAdminExample();
            example.createCriteria().andPartyIdEqualTo(partyId)
                    .andTypeEqualTo(SystemConstants.PCS_ADMIN_TYPE_NORMAL);
            if (pcsAdminMapper.countByExample(example) > 0) {
                modelMap.put("hasNormalAdmin", true);
            }
        }*/

        return "pcs/pcsPartyAdmin/pcsPartyAdmin_page";
    }

    // 分党委书记添加/更新普通管理员
    @RequiresPermissions("pcsPartyAdmin:edit")
    @RequestMapping(value = "/pcsPartyAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPartyAdmin_au(PcsAdmin record, String mobile, HttpServletRequest request) {

        if(StringUtils.isBlank(mobile) || !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误："+ mobile);
        }

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null || pcsAdmin.getType() != SystemConstants.PCS_ADMIN_TYPE_SECRETARY) {
            throw new UnauthorizedException();
        }
        // 添加本单位管理员
        record.setPartyId( pcsAdmin.getPartyId());
        pcsAdminService.addOrUpdate(record, mobile);
        logger.info(addLog(SystemConstants.LOG_ADMIN, "[分党委书记]添加/修改党代会分党委管理员：%s-%s"
                , JSONUtils.toString(record, false), mobile));

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("pcsPartyAdmin:edit")
    @RequestMapping("/pcsPartyAdmin_au")
    public String pcsPartyAdmin_au(Integer id, ModelMap modelMap) {

        if(id!=null){
            PcsAdmin pcsAdmin = pcsAdminMapper.selectByPrimaryKey(id);
            modelMap.put("pcsAdmin", pcsAdmin);
            modelMap.put("sysUser", sysUserService.findById(pcsAdmin.getUserId()));
        }

        //modelMap.put("pcsConfig", pcsConfigService.getCurrentPcsConfig());

        return "pcs/pcsAdmin/pcsAdmin_au";
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
