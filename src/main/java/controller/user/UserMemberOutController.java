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
    public String memberOut(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        int userId= loginUser.getId();

       modelMap.put("userBean", userBeanService.get(userId));

        MemberOut memberOut = memberOutService.get(userId);
        modelMap.put("memberOut", memberOut);

        modelMap.put("jobMap", metaTypeService.metaTypes("mc_job"));
        modelMap.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));

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
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_handleTime)){
            record.setHandleTime(DateUtils.parseDate(_handleTime, DateUtils.YYYY_MM_DD));
        }

        MemberOut memberOut = memberOutService.get(userId);

        if(memberOut!=null && memberOut.getStatus()!=SystemConstants.MEMBER_OUT_STATUS_SELF_BACK
                && memberOut.getStatus()!=SystemConstants.MEMBER_OUT_STATUS_BACK)
            throw new RuntimeException("不允许修改");

        record.setUserId(loginUser.getId());
        record.setApplyTime(new Date());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_APPLY);

        if(loginUser.getType()==SystemConstants.USER_TYPE_JZG)
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER);
        else
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT);

        if (memberOut == null) {
            memberOutService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "提交流出党员申请：%s", record.getId()));
        } else {
            memberOutService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "提交修改流出党员申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberOut_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberOut_back(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        memberOutService.back(userId);

        return success(FormUtils.SUCCESS);
    }
}
