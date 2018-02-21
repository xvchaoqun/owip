package controller.modify;

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
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ModifyCadreRewardController extends ModifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 系统菜单
    @RequiresPermissions("modifyCadreReward:list")
    @RequestMapping({"/modifyCadreReward"})
    public String modifyCadreReward_page(byte rewardType, @CurrentUser SysUserView loginUser, Byte cls, // 0 列表 1 修改申请 2 完成审核 3 删除
                                         Integer cadreId,
                                         HttpServletRequest request,
                                         ModelMap modelMap) {

        //byte rewardType = (byte)request.getAttribute("rewardType");

        if (cls == null) {
            cls = (byte) (ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADRERESERVE) ? 0 : 1);
        }
        modelMap.put("cls", cls);
        modelMap.put("rewardType", rewardType);
        if (cadreId != null) {

            CadreView cadre = cadreViewMapper.selectByPrimaryKey(cadreId);
            modelMap.put("cadre", cadre);
            SysUserView sysUser = sysUserService.findById(cadre.getUserId());
            modelMap.put("sysUser", sysUser);
        }

        byte module = 0;
        if(rewardType==SystemConstants.CADRE_REWARD_TYPE_TEACH){
            module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH;
        }else if(rewardType==SystemConstants.CADRE_REWARD_TYPE_RESEARCH){
            module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH;
        }else if(rewardType==SystemConstants.CADRE_REWARD_TYPE_OTHER){
            module = ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER;
        }
        modelMap.put("module", module);
        if (cls == 0) {
            // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            modelMap.put("cadre", cadre);
            /*CadreRewardExample example = new CadreRewardExample();
            Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            criteria.andCadreIdEqualTo(cadre.getId());
            List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
            modelMap.put("cadreRewards", cadreRewards);*/

            return "modify/modifyCadreReward/modifyCadreReward_page";
        } else {
            return "forward:/modifyTableApply?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreReward:list")
    @RequestMapping("/modifyCadreReward_detail")
    public String modifyCadreReward_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);
        /*CadreRewardExample example = new CadreRewardExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreReward> cadreRewards = cadreRewardMapper.selectByExample(example);
        modelMap.put("cadreRewards", cadreRewards);
*/
        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreRewardMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreReward/modifyCadreReward_detail";

    }
}
