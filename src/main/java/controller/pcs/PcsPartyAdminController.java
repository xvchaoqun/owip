package controller.pcs;

import controller.global.OpException;
import domain.pcs.PcsAdmin;
import domain.pcs.PcsAdminExample;
import domain.pcs.PcsConfig;
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
import persistence.party.common.OwAdmin;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.tags.CmTag;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pcs")
public class PcsPartyAdminController extends PcsBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("pcsPartyAdmin:list")
    @RequestMapping("/pcsPartyAdmin")
    public String pcsPartyAdmin(ModelMap modelMap) {

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null) {
            throw new UnauthorizedException();
        }

        modelMap.put("isPartySecretary",
                partyMemberService.isPartySecretary(pcsAdmin.getUserId(), pcsAdmin.getPartyId()));

        Integer partyId = pcsAdmin.getPartyId();
        modelMap.put("party", partyService.findAll().get(partyId));

        {
            PcsConfig currentPcsConfig = pcsConfigService.getCurrentPcsConfig();
            int configId = currentPcsConfig.getId();

            PcsAdminExample example = new PcsAdminExample();
            example.createCriteria().andConfigIdEqualTo(configId).andPartyIdEqualTo(partyId);
            /*example.setOrderByClause("type asc");*/
            List<PcsAdmin> pcsAdmins = pcsAdminMapper.selectByExample(example);
            modelMap.put("pcsAdmins", pcsAdmins);
        }

        return "pcs/pcsPartyAdmin/pcsPartyAdmin_page";
    }

    // 分党委书记添加/更新其他管理员
    @RequiresPermissions("pcsPartyAdmin:edit")
    @RequestMapping(value = "/pcsPartyAdmin_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPartyAdmin_au(PcsAdmin record, HttpServletRequest request) {

        String mobile = record.getMobile();
        if(StringUtils.isNotBlank(mobile) && !CmTag.validMobile(mobile)){
            return failed("手机号码有误："+ mobile);
        }

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null || !partyMemberService.isPartySecretary(pcsAdmin.getUserId(), pcsAdmin.getPartyId())) {

        }
        // 添加本单位管理员

        List<OwAdmin> owAdmins = partyAdminService.getOwAdmins(record.getUserId());
        if(owAdmins.size()==0) {
            throw new OpException("该用户不是党代会管理员");
        }

        OwAdmin owAdmin = owAdmins.get(0); // 按分党委顺序仅读取管理的第一个分党委
        int partyId = owAdmin.getPartyId();
        if(pcsAdmin.getPartyId().intValue() !=partyId){
            throw new UnauthorizedException();
        }
        record.setPartyId(partyId);

        pcsAdminService.addOrUpdate(record);
        logger.info(addLog(LogConstants.LOG_PCS, "[分党委书记]添加/修改党代会分党委管理员信息：%s-%s"
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

        return "pcs/pcsAdmin/pcsAdmin_au";
    }

    // 分党委书记删除其他管理员
    @RequiresPermissions("pcsPartyAdmin:del")
    @RequestMapping(value = "/pcsPartyAdmin_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_pcsPartyAdmin_del(HttpServletRequest request, Integer id, ModelMap modelMap) {

        PcsAdmin admin = pcsAdminMapper.selectByPrimaryKey(id);
        if (admin == null) return success();

        PcsAdmin pcsAdmin = pcsAdminService.getAdmin(ShiroHelper.getCurrentUserId());
        if (pcsAdmin == null || !partyMemberService.isPartySecretary(pcsAdmin.getUserId(), pcsAdmin.getPartyId())
                || admin.getPartyId().intValue() != pcsAdmin.getPartyId()) {
            throw new UnauthorizedException();
        }

        pcsAdminService.batchDel(new Integer[]{id});
        SysUserView user = admin.getUser();
        logger.info(addLog(LogConstants.LOG_PCS, "[分党委书记]删除党代会分党委管理员信息-%s(%s)", user.getRealname(), user.getCode()));

        return success(FormUtils.SUCCESS);
    }
}
