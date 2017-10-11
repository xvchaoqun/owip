package controller.modify;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.modify.ModifyTableApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;

@Controller
public class ModifyCadreCompanyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 系统菜单
    @RequiresPermissions("modifyCadreCompany:list")
    @RequestMapping("/modifyCadreCompany")
    public String modifyCadreCompany(@CurrentUser SysUserView loginUser, Byte cls, // 0 列表 1 修改申请 2 完成审核 3 删除
                                      Integer cadreId, ModelMap modelMap) {

        if (cls == null) {
            cls = (byte) (ShiroHelper.hasAnyRoles(SystemConstants.ROLE_CADRE, SystemConstants.ROLE_CADRERESERVE) ? 0 : 1);
        }
        modelMap.put("cls", cls);
        if (cadreId != null) {

            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        byte module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY;
        modelMap.put("module", module);
        if (cls == 0) {

            // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            modelMap.put("cadre", cadre);

            /*CadreCompanyExample example = new CadreCompanyExample();
            Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            criteria.andCadreIdEqualTo(cadre.getId());
            List<CadreCompany> cadreCompanys = cadreCompanyMapper.selectByExample(example);
            modelMap.put("cadreCompanys", cadreCompanys);*/

            return "modify/modifyCadreCompany/modifyCadreCompany_page";
        } else {
            return "forward:/modifyTableApply?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreCompany:list")
    @RequestMapping("/modifyCadreCompany_detail")
    public String modifyCadreCompany_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);
        /*CadreCompanyExample example = new CadreCompanyExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreCompany> cadreCompanys = cadreCompanyMapper.selectByExample(example);
        modelMap.put("cadreCompanys", cadreCompanys);*/

        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreCompanyMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreCompany/modifyCadreCompany_detail";

    }
}
