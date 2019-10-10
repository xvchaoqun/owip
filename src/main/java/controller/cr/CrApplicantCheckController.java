package controller.cr;

import domain.cr.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.tags.CmTag;
import sys.utils.FormUtils;

import java.util.Map;

@Controller
public class CrApplicantCheckController extends CrBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("crApplicant:edit")
    @RequestMapping("/crApplicant_requireCheck")
    public String crApplicant_requireCheck(int applicantId, int postId, ModelMap modelMap) {

        CrApplicant crApplicant = crApplicantMapper.selectByPrimaryKey(applicantId);
        modelMap.put("crApplicant", crApplicant);

        CrPost crPost = crPostMapper.selectByPrimaryKey(postId);
        modelMap.put("crPost", crPost);
        if (crPost != null) {
            CrRequire crRequire = crRequireMapper.selectByPrimaryKey(crPost.getRequireId());
            modelMap.put("crRequire", crRequire);
        }

        boolean isFirstPost = (crApplicant.getFirstPostId() == postId);

        if(postId==crApplicant.getFirstPostId()){
            modelMap.put("status", crApplicant.getFirstCheckStatus());
            modelMap.put("remark", crApplicant.getFirstCheckRemark());
        }else{
            modelMap.put("status", crApplicant.getSecondCheckStatus());
            modelMap.put("remark", crApplicant.getSecondCheckRemark());
        }

        if (crApplicant != null) {

            Map<Byte, String> realValMap = crRequireService.getRealValMap(crApplicant.getUserId());
            modelMap.put("realValMap", realValMap);

            Map<Integer, CrApplicantCheck> checkMap = crApplicantCheckService.getRuleCheckMap(crApplicant.getId(), isFirstPost);
            modelMap.put("checkMap", checkMap);

            modelMap.put("cadre", CmTag.getCadreByUserId(crApplicant.getUserId()));
        }

        return "cr/crApplicant/crApplicant_requireCheck";
    }

    @RequiresPermissions("crApplicant:edit")
    @RequestMapping(value = "/crApplicant_requireCheckItem", method = RequestMethod.POST)
    @ResponseBody
    public Map crApplicant_requireCheckItem(int applicantId, int postId, int ruleId, boolean status) {

        CrApplicant crApplicant = crApplicantMapper.selectByPrimaryKey(applicantId);
        boolean isFirstPost = (crApplicant.getFirstPostId() == postId);

        crApplicantCheckService.ruleCheck(applicantId, isFirstPost, ruleId, status);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("crApplicant:edit")
    @RequestMapping(value = "/crApplicant_requireCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map do_crApplicant_requireCheck(int id, int postId, boolean status, String remark) {

        crApplicantCheckService.requireCheck(id, postId, status, remark);

        return success(FormUtils.SUCCESS);
    }
}
