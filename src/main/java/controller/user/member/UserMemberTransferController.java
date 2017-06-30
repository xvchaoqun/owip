package controller.user.member;

import bean.UserBean;
import controller.BaseController;
import domain.member.Member;
import domain.member.MemberTransfer;
import domain.party.Branch;
import domain.party.Party;
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
import sys.shiro.CurrentUser;
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
public class UserMemberTransferController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/memberTransfer")
    public String memberTransfer(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        Integer userId = loginUser.getId();
        UserBean userBean = userBeanService.get(userId);
        modelMap.put("userBean", userBean);

        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();

        modelMap.put("fromParty", partyMap.get(userBean.getPartyId()));
        modelMap.put("fromBranch", branchMap.get(userBean.getBranchId()));

        MemberTransfer memberTransfer = memberTransferService.get(userId);
        modelMap.put("memberTransfer", memberTransfer);

        modelMap.put("locationMap", locationService.codeMap());

        if(memberTransfer!=null) {
            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }
        }

        if(memberTransfer==null || memberTransfer.getStatus()== SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK
                || memberTransfer.getStatus()==SystemConstants.MEMBER_TRANSFER_STATUS_BACK)
            return "user/member/memberTransfer/memberTransfer_au";

        return "user/member/memberTransfer/memberTransfer";
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_au(@CurrentUser SysUserView loginUser,
                                   MemberTransfer record, String _payTime, String _fromHandleTime,  HttpServletRequest request) {

        Integer userId = loginUser.getId();
        UserBean userBean = userBeanService.get(userId);

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }

        MemberTransfer memberTransfer = memberTransferService.get(userId);

        if(memberTransfer!=null && memberTransfer.getStatus()!=SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK
                && memberTransfer.getStatus()!=SystemConstants.MEMBER_TRANSFER_STATUS_BACK)
            throw new RuntimeException("不允许修改");

        if(userBean.getPartyId().byteValue() == record.getToPartyId()){
            return failed("转入不能是当前所在分党委");
        }

        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        record.setUserId(loginUser.getId());
        record.setApplyTime(new Date());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
        record.setIsBack(false);
        if (memberTransfer == null) {
            memberTransferService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "本人提交校内组织关系互转"));
            memberTransfer = record;
        } else {

            memberTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "本人修改校内组织关系互转"));
        }
        applyApprovalLogService.add(memberTransfer.getId(),
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), memberTransfer.getUserId(),
                userId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交校内组织关系互转申请");

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberTransfer_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberTransfer_back(@CurrentUser SysUserView loginUser, String remark){

        int userId = loginUser.getId();
        memberTransferService.back(userId);
        logger.info(addLog(SystemConstants.LOG_USER, "本人取消校内组织关系互转"));
        return success(FormUtils.SUCCESS);
    }
}
