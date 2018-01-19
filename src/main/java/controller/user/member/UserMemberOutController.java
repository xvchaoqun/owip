package controller.user.member;

import bean.UserBean;
import controller.BaseController;
import domain.member.Member;
import domain.member.MemberOut;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.shiro.CurrentUser;
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

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/memberOut")
    public String memberOut(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();

        UserBean userBean = userBeanService.get(userId);
        modelMap.put("userBean", userBean);
        MemberOut memberOut = memberOutService.getLatest(userId);
        if(userBean.getMemberStatus()==SystemConstants.MEMBER_STATUS_NORMAL
                && memberOut.getStatus()==SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY){
            // 如果已是党员，可以进行转出操作
            memberOut = null;
        }

        modelMap.put("memberOut", memberOut);

        if(memberOut==null || memberOut.getStatus() <= SystemConstants.MEMBER_OUT_STATUS_BACK)
            return "user/member/memberOut/memberOut_au";

        return "user/member/memberOut/memberOut";
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberOut_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberOut_au(@CurrentUser SysUserView loginUser,
                                   MemberOut record, String _payTime, String _handleTime, HttpServletRequest request) {

        //Integer userId = record.getUserId();
        Integer userId = loginUser.getId();
        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        Member member = memberService.get(userId);
        MemberOut memberOut = null;
        if(member.getStatus()!=SystemConstants.MEMBER_STATUS_NORMAL){
            // 如果已是党员，可以进行转出操作
            memberOut = memberOutService.getLatest(userId);
        }

        if(memberOut!=null && memberOut.getStatus() > SystemConstants.MEMBER_OUT_STATUS_BACK)
           return failed("不允许修改");


        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        record.setUserId(loginUser.getId());
        record.setApplyTime(new Date());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_APPLY);
        record.setIsBack(false);
        if (memberOut == null) {
            memberOutService.insertOrUpdateSelective(record);
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

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberOut_back(@CurrentUser SysUserView loginUser, String remark){

        int userId = loginUser.getId();
        memberOutService.back(userId);

        logger.info(addLog(SystemConstants.LOG_USER, "取消组织关系转出申请"));
        return success(FormUtils.SUCCESS);
    }
}
