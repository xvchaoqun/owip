package controller.modify;

import controller.ModifyBaseController;
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
import sys.constants.ModifyConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;

@Controller
public class ModifyCadreParttimeController extends ModifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 系统菜单
    @RequiresPermissions("modifyCadreParttime:list")
    @RequestMapping("/modifyCadreParttime")
    public String modifyCadreParttime(@CurrentUser SysUserView loginUser, Byte cls, // 0 列表 1 修改申请 2 完成审核 3 删除
                                      Integer cadreId, ModelMap modelMap) {

        if (cls == null) {
            cls = (byte) (ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADRERESERVE) ? 0 : 1);
        }
        modelMap.put("cls", cls);
        if (cadreId != null) {

            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        byte module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME;
        modelMap.put("module", module);
        if (cls == 0) {
            // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            modelMap.put("cadre", cadre);
            /*CadreParttimeExample example = new CadreParttimeExample();
            Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

            criteria.andCadreIdEqualTo(cadre.getId());
            List<CadreParttime> cadreParttimes = cadreParttimeMapper.selectByExample(example);
            modelMap.put("cadreParttimes", cadreParttimes);*/

            return "modify/modifyCadreParttime/modifyCadreParttime_page";
        } else {
            return "forward:/modifyTableApply?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreParttime:list")
    @RequestMapping("/modifyCadreParttime_detail")
    public String modifyCadreParttime_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);
        /*CadreParttimeExample example = new CadreParttimeExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreParttime> cadreParttimes = cadreParttimeMapper.selectByExample(example);
        modelMap.put("cadreParttimes", cadreParttimes);*/

        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreParttimeMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreParttime/modifyCadreParttime_detail";

    }
}
