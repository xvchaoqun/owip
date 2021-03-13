package controller.cla.user;

import controller.cla.ClaBaseController;
import domain.cadre.CadreView;
import domain.cla.ClaApply;
import mixin.MixinUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/user/cla")
public class UserClaApplyBackController extends ClaBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresPermissions("userClaApply:*")
    @RequestMapping("/claApply_back")
    public String claApply_back(int applyId, ModelMap modelMap) {

        ClaApply claApply = claApplyService.get(applyId);
        modelMap.put("claApply", claApply);

        if(claApply.getIsBack()){

            return "cla/user/claApply/claApply_back_view";
        }

        return "cla/user/claApply/claApply_back";
    }

    @RequestMapping(value = "/claApply_back", method = RequestMethod.POST)
    @ResponseBody
    public Map do_claApply_back(Integer cadreId,
                               ClaApply record,
                               HttpServletRequest request) {

        // 是否本人操作
        boolean self = false;
        if(cadreId==null || !ShiroHelper.isPermitted(RoleConstants.PERMISSION_CLAADMIN)){
            // 确认干部只能提交自己的申请
            CadreView cadre = cadreService.dbFindByUserId(ShiroHelper.getCurrentUserId());
            cadreId = cadre.getId();
            self = true;
        }
        int applyId = record.getId();
        ClaApply claApply = claApplyService.get(applyId);
        if(claApply.getCadreId().intValue()!=cadreId){
            return failed("参数有误。");
        }

        // 干部本人不允许修改
        if(self && claApply.getIsBack()){

            return failed("已销假，请勿重复操作。");
        }

        if(record.getRealStartTime().after(record.getRealEndTime())){
           return failed("实际出发时间不能晚于实际返校时间。");
        }

        ClaApply _update = new ClaApply();
        _update.setId(applyId);
        _update.setRealStartTime(record.getRealStartTime());
        _update.setRealEndTime(record.getRealEndTime());
        _update.setRealRemark(record.getRealRemark());
        _update.setIsBack(true);

        claApplyMapper.updateByPrimaryKeySelective(_update);

        CadreView cadre = iCadreMapper.getCadre(cadreId);
        sysApprovalLogService.add(applyId, cadre.getUserId(),
                self? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF:SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CLA_APPLY,
                "回校销假", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                JSONUtils.toString(record, MixinUtils.baseMixins(), false));

        return success(FormUtils.SUCCESS);
    }
}
