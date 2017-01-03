package controller.modify;

import controller.BaseController;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
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
public class ModifyTableApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("modifyTableApply:list")
    @RequestMapping("/modifyTableApply_page")
    public String modifyTableApply_page(
            Integer userId,
            byte module,
            Byte cls, // 1 修改申请 2 完成审核 3 删除
            ModelMap modelMap) {

        if (userId != null)
            modelMap.put("sysUser", sysUserService.findById(userId));

        modelMap.put("cls", cls);
        modelMap.put("module", module);

        return "modify/modifyTableApply/modifyTableApply_page";
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

        if (ShiroHelper.hasRole(SystemConstants.ROLE_ADMIN)) {
            if (userId != null) {
                criteria.andUserIdEqualTo(userId);
            }
        } else { // 干部只能看到自己的
            criteria.andUserIdEqualTo(loginUser.getId());
        }

        criteria.andModuleEqualTo(module);

        if (cls == 1) { // 待审核
            criteria.andStatusEqualTo(SystemConstants.MODIFY_TABLE_APPLY_STATUS_APPLY);
        } else if (cls == 2) { // 审核完成
            List<Byte> statusList = new ArrayList<>();
            statusList.add(SystemConstants.MODIFY_TABLE_APPLY_STATUS_PASS);
            statusList.add(SystemConstants.MODIFY_TABLE_APPLY_STATUS_DENY);
            criteria.andStatusIn(statusList);
        } else if (cls == 3) { // 已删除
            criteria.andStatusEqualTo(SystemConstants.MODIFY_TABLE_APPLY_STATUS_DELETE);
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
    @RequiresPermissions("modifyTableApply:del")
    @RequestMapping(value = "/modifyTableApply_batchDel", method = RequestMethod.POST)
    @ResponseBody
    public Map modify(
                      @RequestParam(value = "ids[]") Integer[] ids,
                      HttpServletRequest request) {

        if (null != ids && ids.length > 0) {
            modifyTableApplyService.batchDel(ids);
            logger.info(addLog(SystemConstants.LOG_ADMIN, "批量删除信息修改申请：%s", StringUtils.join(ids, ",")));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("modifyTableApply:list")
    @RequestMapping("/modifyTableApply_detail")
    public String modifyCadreEdu_detail(byte module, int applyId, ModelMap modelMap) {

        if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU){
            return "forward:/modifyCadreEdu_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK){
            return "forward:/modifyCadreWork_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK){
            return "forward:/modifyCadreBook_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY){
            return "forward:/modifyCadreCompany_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE){
            return "forward:/modifyCadreCourse_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER){
            return "forward:/modifyCadrePaper_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME){
            return "forward:/modifyCadreParttime_detail?applyId=" + applyId;
        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT){

            return "forward:/modifyCadreResearch_detail?applyId=" + applyId
                    +"&researchType=" + SystemConstants.CADRE_RESEARCH_TYPE_DIRECT;

        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN){

            return "forward:/modifyCadreResearch_detail?applyId=" + applyId
                    +"&researchType=" + SystemConstants.CADRE_RESEARCH_TYPE_IN;

        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH){

            return "forward:/modifyCadreReward_detail?applyId=" + applyId
                    + "&rewardType=" + SystemConstants.CADRE_REWARD_TYPE_TEACH;

        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH){

            return "forward:/modifyCadreReward_detail?applyId=" + applyId
                    + "&rewardType=" + SystemConstants.CADRE_REWARD_TYPE_RESEARCH;

        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER){

            return "forward:/modifyCadreReward_detail?applyId=" + applyId
                    + "&rewardType=" + SystemConstants.CADRE_REWARD_TYPE_OTHER;

        }else if(module==SystemConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN){
            return "forward:/modifyCadreTrain_detail?applyId=" + applyId;
        }

        return null;
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
            logger.info(addLog(SystemConstants.LOG_ADMIN, "审核信息修改申请：%s, %s", id, status));
        }

        return success(FormUtils.SUCCESS);
    }
}
