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
public class UserMemberTransferController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles("member")
    @RequestMapping("/memberTransfer")
    public String memberTransfer(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        modelMap.put("sysUser", loginUser);

        MemberTransfer memberTransfer = memberTransferService.get(loginUser.getId());
        modelMap.put("memberTransfer", memberTransfer);

        modelMap.put("locationMap", locationService.codeMap());
        modelMap.put("jobMap", metaTypeService.metaTypes("mc_job"));
        modelMap.put("flowDirectionMap", metaTypeService.metaTypes("mc_flow_direction"));
        Map<Integer, Branch> branchMap = branchService.findAll();
        Map<Integer, Party> partyMap = partyService.findAll();
        modelMap.put("branchMap", branchMap);
        modelMap.put("partyMap", partyMap);
        if(memberTransfer!=null) {
            if (memberTransfer.getToPartyId() != null) {
                modelMap.put("toParty", partyMap.get(memberTransfer.getToPartyId()));
            }
            if (memberTransfer.getToBranchId() != null) {
                modelMap.put("toBranch", branchMap.get(memberTransfer.getToBranchId()));
            }
            if (memberTransfer.getFromPartyId() != null) {
                modelMap.put("fromParty", partyMap.get(memberTransfer.getFromPartyId()));
            }
            if (memberTransfer.getFromBranchId() != null) {
                modelMap.put("fromBranch", branchMap.get(memberTransfer.getFromBranchId()));
            }
        }

        if(memberTransfer==null || memberTransfer.getStatus()== SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK
                || memberTransfer.getStatus()==SystemConstants.MEMBER_TRANSFER_STATUS_BACK)
            return "user/memberTransfer/memberTransfer_au";

        return "user/memberTransfer/memberTransfer";
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberTransfer_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberTransfer_au(@CurrentUser SysUser loginUser,
                                   MemberTransfer record, String _payTime, String _fromHandleTime,  HttpServletRequest request) {

        if(StringUtils.isNotBlank(_payTime)){
            record.setPayTime(DateUtils.parseDate(_payTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_fromHandleTime)){
            record.setFromHandleTime(DateUtils.parseDate(_fromHandleTime, DateUtils.YYYY_MM_DD));
        }

        MemberTransfer memberTransfer = memberTransferService.get(loginUser.getId());

        if(memberTransfer!=null && memberTransfer.getStatus()!=SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK
                && memberTransfer.getStatus()!=SystemConstants.MEMBER_TRANSFER_STATUS_BACK)
            throw new RuntimeException("不允许修改");

        if(record.getFromPartyId().byteValue() == record.getToPartyId()){
            return failed("转入不能是当前所在分党委");
        }

        record.setUserId(loginUser.getId());
        record.setCode(loginUser.getCode());
        record.setApplyTime(new Date());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);

        if(loginUser.getType()==SystemConstants.USER_TYPE_JZG)
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER);
        else
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT);

        if (memberTransfer == null) {
            memberTransferService.insertSelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "本人提交流出党员申请：%s", record.getId()));
        } else {

            memberTransferService.updateByPrimaryKeySelective(record);
            logger.info(addLog(request, SystemConstants.LOG_OW, "本人提交修改流出党员申请：%s", record.getId()));
        }

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles("member")
    @RequestMapping(value = "/memberTransfer_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberTransfer_back(@CurrentUser SysUser loginUser, String remark){

        int userId = loginUser.getId();
        memberTransferService.back(userId);

        return success(FormUtils.SUCCESS);
    }
}
