package controller.modify;

import controller.BaseController;
import domain.cadre.CadreView;
import domain.cadre.CadreWork;
import domain.cadre.CadreWorkExample;
import domain.modify.ModifyTableApply;
import domain.sys.SysUserView;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import sys.shiro.CurrentUser;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import java.util.List;

@Controller
public class ModifyCadreWorkController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 系统菜单
    @RequiresPermissions("modifyCadreWork:list")
    @RequestMapping("/modifyCadreWork")
    public String modifyCadreWork(@CurrentUser SysUserView loginUser, Byte cls, // 0 列表 1 修改申请 2 完成审核 3 删除
                                      Integer cadreId, ModelMap modelMap) {

        if (cls == null) {
            cls = (byte) (ShiroHelper.hasAnyRoles(SystemConstants.ROLE_CADRE, SystemConstants.ROLE_CADRERESERVE) ? 0 : 1);
        }
        modelMap.put("cls", cls);
        if (cadreId != null) {

            CadreView cadre = cadreService.findAll().get(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        byte module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK;
        modelMap.put("module", module);
        if (cls == 0) {

            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            modelMap.put("cadre", cadre);

            return "modify/modifyCadreWork/modifyCadreWork_page";
        } else {
            return "forward:/modifyTableApply?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreWork:list")
    @RequestMapping("/modifyCadreWork_detail")
    public String modifyCadreWork_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreWorkExample example = new CadreWorkExample();
        CadreWorkExample.Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        example.setOrderByClause("start_time asc");
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);
        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreWork> cadreWorks = cadreWorkMapper.selectByExample(example);
        modelMap.put("cadreWorks", cadreWorks);

        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreWorkMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreWork/modifyCadreWork_detail";

    }
}
