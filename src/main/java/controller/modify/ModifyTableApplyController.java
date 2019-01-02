package controller.modify;

import domain.cadre.CadreView;
import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.LogConstants;
import sys.constants.ModifyConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tool.paging.CommonList;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ModifyTableApplyController extends ModifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 申请列表
    @RequiresPermissions("modifyTableApply:list")
    @RequestMapping("/modifyTableApply")
    public String modifyTableApply(
            Integer cadreId,
            Integer userId, // 搜索字段
            byte module,
            Byte cls, //  0 列表 1 修改申请 2 完成审核 3 删除
            ModelMap modelMap) {

        if (cls == null) {
            // 默认列表
            cls = (byte) (ShiroHelper.hasAnyRoles(RoleConstants.ROLE_CADRE, RoleConstants.ROLE_CADRERESERVE) ? 0 : 1);
        }
        modelMap.put("cls", cls);

        if (cadreId != null) {
            CadreView cadre = iCadreMapper.getCadre(cadreId);
            modelMap.put("cadre", cadre);
        }

        modelMap.put("module", module);
        if (cls == 0) {
            // 干部只能看到自己的
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            modelMap.put("cadre", cadre);

            // 当module>100时，是干部本人的申请地址
            module = (byte) (module%100);
            switch (module){
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:

                    return "modify/modifyCadreBook/modifyCadreBook_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:

                    return "modify/modifyCadreCompany/modifyCadreCompany_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:

                    return "modify/modifyCadreCourse/modifyCadreCourse_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:

                    List<Integer> needTutorEduTypes = cadreEduService.needTutorEduTypes();
                    modelMap.put("needTutorEduTypes", needTutorEduTypes);

                    return "modify/modifyCadreEdu/modifyCadreEdu_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:

                    return "modify/modifyCadrePaper/modifyCadrePaper_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:

                    return "modify/modifyCadreParttime/modifyCadreParttime_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:

                    Byte researchType = null;
                    if(module == ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT){
                        researchType= CadreConstants.CADRE_RESEARCH_TYPE_DIRECT;
                    }else if(module == ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN){
                        researchType=CadreConstants.CADRE_RESEARCH_TYPE_IN;
                    }
                    modelMap.put("researchType", researchType);

                    return "modify/modifyCadreResearch/modifyCadreResearch_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:

                    Byte rewardType = null;
                    if(module == ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH){
                        rewardType = CadreConstants.CADRE_REWARD_TYPE_TEACH;
                    }else if(module == ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH){
                        rewardType = CadreConstants.CADRE_REWARD_TYPE_RESEARCH;
                    }else if(module == ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER){
                        rewardType = CadreConstants.CADRE_REWARD_TYPE_OTHER;
                    }

                    modelMap.put("rewardType", rewardType);

                    return "modify/modifyCadreReward/modifyCadreReward_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:

                    return "modify/modifyCadreTrain/modifyCadreTrain_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:

                    return "modify/modifyCadreWork/modifyCadreWork_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO:

                    return "modify/modifyCadrePostPro/modifyCadrePostPro_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN:

                    return "modify/modifyCadrePostAdmin/modifyCadrePostAdmin_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK:

                    return "modify/modifyCadrePostWork/modifyCadrePostWork_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY:

                    return "modify/modifyCadreFamily/modifyCadreFamily_page";

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD:

                    return "modify/modifyCadreFamilyAbroad/modifyCadreFamilyAbroad_page";
            }
        }

        if (userId != null) {
            modelMap.put("sysUser", sysUserService.findById(userId));
        }

        return "modify/modifyTableApply/modifyTableApply_page";
    }

    // 申请详情
    @RequiresPermissions("modifyTableApply:list")
    @RequestMapping("/modifyTableApply_detail")
    public String modifyTableApply_detail(int applyId, ModelMap modelMap) {

        // 申请记录
        ModifyTableApply mta = modifyTableApplyMapper.selectByPrimaryKey(applyId);
        modelMap.put("mta", mta);
        int userId = mta.getUserId();

        CadreView cadre = cadreService.dbFindByUserId(userId);
        modelMap.put("cadre", cadre);

        byte module = mta.getModule();
        // 修改记录
        Integer modifyId = mta.getModifyId();

        switch (module){
            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:

                modelMap.put("modify", cadreBookMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreBook/modifyCadreBook_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:

                modelMap.put("modify", cadreCompanyMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreCompany/modifyCadreCompany_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:

                modelMap.put("modify", cadreCourseMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreCourse/modifyCadreCourse_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:

                List<Integer> needTutorEduTypes = cadreEduService.needTutorEduTypes();
                modelMap.put("needTutorEduTypes", needTutorEduTypes);

                modelMap.put("modify", cadreEduMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreEdu/modifyCadreEdu_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:

                modelMap.put("modify", cadrePaperMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadrePaper/modifyCadrePaper_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:

                modelMap.put("modify", cadreParttimeMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreParttime/modifyCadreParttime_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:

                modelMap.put("modify", cadreResearchMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreResearch/modifyCadreResearch_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:

                modelMap.put("modify", cadreRewardMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreReward/modifyCadreReward_detail";


            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:

                modelMap.put("modify", cadreTrainMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreTrain/modifyCadreTrain_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:

                modelMap.put("modify", cadreWorkMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreWork/modifyCadreWork_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO:

                modelMap.put("modify", cadrePostProMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadrePostPro/modifyCadrePostPro_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN:

                modelMap.put("modify", cadrePostAdminMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadrePostAdmin/modifyCadrePostAdmin_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK:

                modelMap.put("modify", cadrePostAdminMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadrePostWork/modifyCadrePostWork_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY:

                modelMap.put("modify", cadreFamilyMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreFamily/modifyCadreFamily_detail";

            case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD:

                modelMap.put("modify", cadreFamilyAbroadMapper.selectByPrimaryKey(modifyId));
                return "modify/modifyCadreFamilyAbroad/modifyCadreFamilyAbroad_detail";
        }

        return null;
    }

    @RequiresPermissions("modifyTableApply:list")
    @RequestMapping("/modifyTableApply_data")
    public void modifyTableApply_data(@CurrentUser SysUserView loginUser, HttpServletResponse response,
                                      @RequestParam(required = false, defaultValue = "1") Byte cls,
                                      byte module,
                                      Integer userId,
                                      Integer pageSize, Integer pageNo) throws IOException {

        if (null == pageSize) {
            pageSize = springProps.pageSize;
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        pageNo = Math.max(1, pageNo);

        ModifyTableApplyExample example = new ModifyTableApplyExample();
        ModifyTableApplyExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (ShiroHelper.hasRole(RoleConstants.ROLE_ADMIN)) {
            if (userId != null) {
                criteria.andUserIdEqualTo(userId);
            }
        } else { // 干部只能看到自己的
            criteria.andUserIdEqualTo(loginUser.getId());
        }

        criteria.andModuleEqualTo((byte) (module%100));

        if (cls == 1) { // 待审核
            criteria.andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
        } else if (cls == 2) { // 审核完成
            List<Byte> statusList = new ArrayList<>();
            statusList.add(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_PASS);
            statusList.add(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_DENY);
            criteria.andStatusIn(statusList);
        } else if (cls == 3) { // 已删除
            criteria.andStatusEqualTo(ModifyConstants.MODIFY_TABLE_APPLY_STATUS_DELETE);
        } else {
            criteria.andStatusIsNull();
        }

        int count = modifyTableApplyMapper.countByExample(example);
        if ((pageNo - 1) * pageSize >= count) {

            pageNo = Math.max(1, pageNo - 1);
        }
        List<ModifyTableApply> records = modifyTableApplyMapper.selectByExampleWithRowbounds(example, new RowBounds((pageNo - 1) * pageSize, pageSize));

        CommonList commonList = new CommonList(count, pageNo, pageSize);
        Map resultMap = new HashMap();
        resultMap.put("rows", records);
        resultMap.put("records", count);
        resultMap.put("page", pageNo);
        resultMap.put("total", commonList.pageNum);

        JSONUtils.jsonp(resultMap);
        return;
    }

    // 假删除
    @RequiresPermissions("modifyTableApply:fakeDel")
    @RequestMapping(value = "/modifyTableApply_fakeDel", method = RequestMethod.POST)
    @ResponseBody
    public Map modifyTableApply_fakeDel(@RequestParam(value = "ids[]") Integer[] ids,
                      HttpServletRequest request) {

        if (null != ids && ids.length > 0) {
            modifyTableApplyService.fakeDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量【假】删除信息修改申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 真删除
    @RequiresPermissions("modifyTableApply:del")
    @RequestMapping(value = "/modifyTableApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map modifyTableApply_batchDel(@RequestParam(value = "ids[]") Integer[] ids,
                      HttpServletRequest request) {

        if (null != ids && ids.length > 0) {
            modifyTableApplyService.batchDel(ids);
            logger.info(addLog(LogConstants.LOG_ADMIN, "批量删除信息修改申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    // 审核
    @RequiresPermissions("modifyTableApply:approval")
    @RequestMapping(value = "/modifyTableApply_approval", method = RequestMethod.POST)
    @ResponseBody
    public Map do_modifyTableApply_approval(Integer id,
                                            Boolean status,
                                            String checkRemark,
                                            String checkReason){

        if (null != id){
            modifyTableApplyService.approval(id, status, checkRemark, checkReason);
            logger.info(addLog(LogConstants.LOG_ADMIN, "审核信息修改申请：%s, %s", id, status));
        }

        return success(FormUtils.SUCCESS);
    }
}
