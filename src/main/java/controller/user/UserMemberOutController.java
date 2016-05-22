package controller.user;

import controller.BaseController;
import domain.*;
import org.apache.commons.lang3.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserMemberOutController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("member")
    @RequestMapping("/memberOut")
    public String memberOut(@CurrentUser SysUser loginUser) {

        return "index";
    }

    @RequiresRoles("member")
    @RequestMapping("/memberOut_page")
    public String memberOut_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();

       modelMap.put("userBean", userBeanService.get(userId));

        MemberOut memberOut = memberOutService.get(userId);
        modelMap.put("memberOut", memberOut);

        if(memberOut==null || memberOut.getStatus()== SystemConstants.MEMBER_OUT_STATUS_SELF_BACK
                || memberOut.getStatus()==SystemConstants.MEMBER_OUT_STATUS_BACK)
            return "user/memberOut/memberOut_au";

        return "user/memberOut/memberOut";
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberOut_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_au(@CurrentUser SysUser loginUser,
                                   MemberOut record, String _payTime, String _handleTime, HttpServletRequest request) {

        //Integer userId = record.getUserId();
        Integer userId = loginUser.getId();
        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        MemberOut memberOut = memberOutService.get(userId);

        if(memberOut!=null && memberOut.getStatus()!=SystemConstants.MEMBER_OUT_STATUS_SELF_BACK
                && memberOut.getStatus()!=SystemConstants.MEMBER_OUT_STATUS_BACK)
            throw new RuntimeException("不允许修改");

        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        record.setUserId(loginUser.getId());
        record.setApplyTime(new Date());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_APPLY);
        record.setIsBack(false);
        if (memberOut == null) {
            memberOutService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "提交组织关系转出申请"));

            memberOut = record;
        } else {
            memberOutService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "修改组织关系转出申请"));
        }

        applyApprovalLogService.add(memberOut.getId(),
                memberOut.getPartyId(), memberOut.getBranchId(), memberOut.getUserId(),
                userId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交组织关系转出申请");

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberOut_back(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        memberOutService.back(userId);

        logger.info(addLog(SystemConstants.LOG_USER, "取消组织关系转出申请"));
        return success(FormUtils.SUCCESS);
    }
}
