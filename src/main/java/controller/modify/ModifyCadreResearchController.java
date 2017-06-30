package controller.modify;

import controller.BaseController;
import domain.cadre.CadreResearch;
import domain.cadre.CadreResearchExample;
import domain.cadre.CadreResearchExample.Criteria;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ModifyCadreResearchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 系统菜单
    @RequiresPermissions("modifyCadreResearch:list")
    @RequestMapping({"/modifyCadreResearch"})
    public String modifyCadreResearch(byte researchType, @CurrentUser SysUserView loginUser, Byte cls, // 0 列表 1 修改申请 2 完成审核 3 删除
                                      Integer cadreId, HttpServletRequest request, ModelMap modelMap) {

        //byte researchType = (byte)request.getAttribute("researchType");

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

        byte module = 0;
        if(researchType==SystemConstants.CADRE_RESEARCH_TYPE_DIRECT){
            module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT;
        }else if(researchType==SystemConstants.CADRE_RESEARCH_TYPE_IN){
            module = SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN;
        }

        modelMap.put("module", module);
        if (cls == 0) {

            CadreResearchExample example = new CadreResearchExample();
            Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
            // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(loginUser.getUserId());
            modelMap.put("cadre", cadre);
            criteria.andCadreIdEqualTo(cadre.getId());
            List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExample(example);
            modelMap.put("cadreResearchs", cadreResearchs);

            return "modify/modifyCadreResearch/modifyCadreResearch_page";
        } else {
            return "forward:/modifyTableApply?module=" + module + "&cls=" + cls;
        }
    }

    // 详情
    @RequiresPermissions("modifyCadreResearch:list")
    @RequestMapping("/modifyCadreResearch_detail")
    public String modifyCadreResearch_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        // 正式数据
        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);
        /*CadreResearchExample example = new CadreResearchExample();
        Criteria criteria = example.createCriteria().andStatusEqualTo(SystemConstants.RECORD_STATUS_FORMAL);
        criteria.andCadreIdEqualTo(cadre.getId());
        List<CadreResearch> cadreResearchs = cadreResearchMapper.selectByExample(example);
        modelMap.put("cadreResearchs", cadreResearchs);*/

        // 修改记录
        Integer modifyId = mta.getModifyId();
        modelMap.put("modify", cadreResearchMapper.selectByPrimaryKey(modifyId));

        return "modify/modifyCadreResearch/modifyCadreResearch_detail";

    }
}
