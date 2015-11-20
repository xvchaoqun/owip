package controller;

import domain.MemberApply;
import domain.MetaType;
import domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

// 申请入党
@Controller
public class ApplyController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("guest")
    @RequestMapping("/apply")
    public String apply() {

        return "index";
    }

    @RequiresRoles("guest")
    @RequestMapping("/apply_page")
    public String apply_page(@CurrentUser SysUser loginUser, HttpServletResponse response, ModelMap modelMap) {

        modelMap.put("user", loginUser);
        MemberApply memberApply = memberApplyService.get(loginUser.getId());
        modelMap.put("memberApply", memberApply);
        if(memberApply==null)
            modelMap.put("partyClassMap", metaTypeService.metaTypes("mc_party_class"));

        return "memberApply/apply";
    }

    @RequiresRoles("guest")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public Map do_apply(@CurrentUser SysUser loginUser,Integer partyId, Integer branchId, String _applyTime, String remark, HttpServletRequest request) {

        MemberApply memberApply = new MemberApply();
        memberApply.setUserId(loginUser.getId());

        MetaType metaType = metaTypeService.findAll().get(loginUser.getTypeId());
        if(StringUtils.equals(metaType.getCode(), "mt_jgz")){
            memberApply.setType(SystemConstants.APPLY_TYPE_TECHER); // 教职工
        }else{
            memberApply.setType(SystemConstants.APPLY_TYPE_STU); // 学生
        }
        memberApply.setPartyId(partyId);
        memberApply.setBranchId(branchId);
        memberApply.setApplyTime(DateUtils.parseDate(_applyTime, DateUtils.YYYY_MM_DD));
        memberApply.setRemark(remark);
        memberApply.setFillTime(new Date());
        memberApply.setCreateTime(new Date());
        memberApply.setStage(SystemConstants.APPLY_STAGE_INIT);
        memberApplyService.insertSelective(memberApply);

        applyLogService.addApplyLog(loginUser.getId(), loginUser.getId(),
                SystemConstants.APPLY_STAGE_INIT, "提交入党申请", IpUtils.getIp(request));
        logger.info(addLog(request, SystemConstants.LOG_OW, "提交入党申请"));
        return success(FormUtils.SUCCESS);
    }
}
