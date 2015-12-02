package controller.party;

import controller.BaseController;
import domain.RetireApply;
import domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

// 党员退休
@Controller
public class RetireApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/retireApply")
    public String retireApply(int userId, ModelMap modelMap) {

        RetireApply retireApply = retireApplyMapper.selectByPrimaryKey(userId);
        modelMap.put("retireApply", retireApply);
        if(retireApply!=null){
            return "forward:/retireApply_verify";
        }
        return "forward:/retireApply_au";
    }

    @RequestMapping("/retireApply_au")
    public String retireApply_au(int userId, ModelMap modelMap) {

        RetireApply retireApply = retireApplyMapper.selectByPrimaryKey(userId);
        modelMap.put("retireApply", retireApply);

        modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));

        return "party/retireApply/retireApply_au";
    }

    @RequestMapping(value = "/retireApply_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_retireApply_au(@CurrentUser SysUser loginUser, RetireApply retireApply, HttpServletRequest request) {

        retireApply.setApplyId(loginUser.getId());
        retireApply.setCreateTime(new Date());
        retireApply.setStatus(SystemConstants.RETIRE_APPLY_STATUS_UNCHECKED);

        retireApplyService.insertSelective(retireApply);
        logger.info(addLog(request, SystemConstants.LOG_OW, "党员退休-提交"));
        return success(FormUtils.SUCCESS);
    }

    @RequestMapping("/retireApply_verify")
    public String retireApply_verify(int userId, ModelMap modelMap) {

        RetireApply retireApply = retireApplyMapper.selectByPrimaryKey(userId);
        modelMap.put("retireApply", retireApply);
        modelMap.put("party", partyService.findAll().get(retireApply.getPartyId()));
        if(retireApply.getBranchId()!=null){
            modelMap.put("branch", branchService.findAll().get(retireApply.getBranchId()));
        }

        return "party/retireApply/retireApply_verify";
    }

    @RequestMapping(value = "/retireApply_verify", method = RequestMethod.POST)
    @ResponseBody
    public Map do_retireApply_verify(@CurrentUser SysUser loginUser, int userId, HttpServletRequest request) {

        retireApplyService.verify(userId, loginUser.getId());

        logger.info(addLog(request, SystemConstants.LOG_OW, "党员退休-审核"));
        return success(FormUtils.SUCCESS);
    }
}
