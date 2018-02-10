package controller.user.modify;

import controller.ModifyBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.ModifyConstants;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserModifyTableApplyController extends ModifyBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 撤销申请
    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping(value = "/modifyTableApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map back(Integer id,
                    HttpServletRequest request) {

        try {
            modifyTableApplyService.back(id);
        } catch (Exception ex) {
            return failed(ex.getMessage());
        }

        return success();
    }

    // 提交[删除申请]
    @RequiresPermissions(SystemConstants.PERMISSION_CADREADMINSELF)
    @RequestMapping(value = "/modifyTableApply_del", method = RequestMethod.POST)
    @ResponseBody
    public Map do_cadreEdu_del(HttpServletRequest request, byte module,  Integer id) {

        if (id != null) {

            switch (module){

                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_EDU:
                    cadreEduService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_WORK:
                    cadreWorkService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK:
                    cadreBookService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY:
                    cadreCompanyService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE:
                    cadreCourseService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER:
                    cadrePaperService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME:
                    cadreParttimeService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN:
                    cadreTrainService.modifyApply(null, id, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT:
                    cadreResearchService.modifyApply(null, id, SystemConstants.CADRE_RESEARCH_TYPE_DIRECT, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN:
                    cadreResearchService.modifyApply(null, id, SystemConstants.CADRE_RESEARCH_TYPE_IN, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH:
                    cadreRewardService.modifyApply(null, id, SystemConstants.CADRE_REWARD_TYPE_TEACH, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH:
                    cadreRewardService.modifyApply(null, id, SystemConstants.CADRE_REWARD_TYPE_RESEARCH, true);
                    break;
                case ModifyConstants.MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER:
                    cadreRewardService.modifyApply(null, id, SystemConstants.CADRE_REWARD_TYPE_OTHER, true);
                    break;
            }

            logger.info(addLog(SystemConstants.LOG_ADMIN, "删除申请-"+
                    ModifyConstants.MODIFY_TABLE_APPLY_MODULE_MAP.get(module)+"：%s", id));
        }

        return success(FormUtils.SUCCESS);
    }
}
