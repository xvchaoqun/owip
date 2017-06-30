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
import sys.shiro.CurrentUser;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

@Controller
public class ModifyCadreTrainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 系统菜单
    @RequiresPermissions("modifyCadreTrain:list")
    @RequestMapping("/modifyCadreTrain")
    public String modifyCadreTrain(@CurrentUser SysUserView loginUser, Byte cls, // 0 列表 1 修改申请 2 完成审核 3 删除
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

        byte module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN;
        modelMap.put("module", module);
        if (cls == 0) {
            // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            modelMap.put("cadre", cadre);
            /*CadreTrainExample example = new CadreTrainExample();
            Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

            criteria.andCadreIdEqualTo(cadre.getId());
            List<CadreTrain> cadreTrains = cadreTrainMapper.selectByExample(example);
            modelMap.put("cadreTrains", cadreTrains);*/

            return "modify/modifyCadreTrain/modifyCadreTrain_page";
        } else {
            return "forward:/modifyTableApply?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreTrain:list")
    @RequestMapping("/modifyCadreTrain_detail")
    public String modifyCadreTrain_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);
       /* CadreTrainExample example = new CadreTrainExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);

        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreTrain> cadreTrains = cadreTrainMapper.selectByExample(example);
        modelMap.put("cadreTrains", cadreTrains);*/

        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreTrainMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreTrain/modifyCadreTrain_detail";

    }
}
