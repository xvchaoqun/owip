package controller.user;

import controller.BaseController;
import domain.Member;
import domain.MemberStay;
import domain.SysUser;
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
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2015/12/7.
 */
@Controller
@RequestMapping("/user")
public class UserMemberStayController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("member")
    @RequestMapping("/memberStay")
    public String memberStay(@CurrentUser SysUser loginUser) {

        return "index";
    }

    @RequiresRoles("member")
    @RequestMapping("/memberStay_page")
    public String memberStay_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();

        MemberStay memberStay = memberStayService.get(userId);
        modelMap.put("memberStay", memberStay);

        modelMap.put("userBean", userBeanService.get(userId));

        if(memberStay==null || memberStay.getStatus()== SystemConstants.MEMBER_STAY_STATUS_SELF_BACK
                || memberStay.getStatus()==SystemConstants.MEMBER_STAY_STATUS_BACK)
            return "user/memberStay/memberStay_au";

        return "user/memberStay/memberStay";
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberStay_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_au(@CurrentUser SysUser loginUser,
                                   MemberStay record,
                                   String _abroadTime, String _returnTime, String _payTime,HttpServletRequest request) {

        //Integer userId = record.getUserId();
        Integer userId = loginUser.getId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if(StringUtils.isNotBlank(_abroadTime)) {
            record.setAbroadTime(DateUtils.parseDate(_abroadTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_returnTime)) {
            record.setReturnTime(DateUtils.parseDate(_returnTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }

        MemberStay memberStay = memberStayService.get(userId);

        if(memberStay!=null && memberStay.getStatus()!=SystemConstants.MEMBER_STAY_STATUS_SELF_BACK
                && memberStay.getStatus()!=SystemConstants.MEMBER_STAY_STATUS_BACK)
            throw new RuntimeException("不允许修改");

        if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getMobile())){
            return failed("手机号码有误");
        }
        if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getContactMobile())){
            return failed("国内联系人手机号码有误");
        }
        record.setUserId(loginUser.getId());
        record.setApplyTime(new Date());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_APPLY);
        record.setIsBack(false);
        if (memberStay == null) {
            memberStayService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "提交暂留申请"));
            memberStay = record;
        } else {

            memberStayService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "修改暂留申请"));
        }
        applyApprovalLogService.add(memberStay.getId(),
                memberStay.getPartyId(), memberStay.getBranchId(), memberStay.getUserId(), userId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交暂留申请");

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberStay_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberStay_back(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        memberStayService.back(userId);
        logger.info(addLog(SystemConstants.LOG_USER, "取消暂留申请"));
        return success(FormUtils.SUCCESS);
    }
}
