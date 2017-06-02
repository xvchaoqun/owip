package controller.user.party;

import controller.BaseController;
import domain.member.Member;
import domain.party.Branch;
import domain.party.GraduateAbroad;
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
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserGraduateAbroadController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping("/graduateAbroad")
    public String graduateAbroad(@CurrentUser SysUserView loginUser, ModelMap modelMap) {

        int userId = loginUser.getId();

        GraduateAbroad graduateAbroad = graduateAbroadService.get(userId);

        modelMap.put("userBean", userBeanService.get(userId));
        modelMap.put("student", studentService.get(userId));
        modelMap.put("graduateAbroad", graduateAbroad);

        if(graduateAbroad!=null) {

            Integer partyId = graduateAbroad.getPartyId();
            Integer branchId = graduateAbroad.getBranchId();
            Integer toBranchId = graduateAbroad.getToBranchId();
            Map<Integer, Branch> branchMap = branchService.findAll();
            Map<Integer, Party> partyMap = partyService.findAll();
            if (partyId != null) {
                modelMap.put("party", partyMap.get(partyId));
            }
            if (branchId != null) {
                modelMap.put("branch", branchMap.get(branchId));
            }
            if (toBranchId != null) {
                modelMap.put("toBranch", branchMap.get(toBranchId));
            }
        }

        if(graduateAbroad==null || graduateAbroad.getStatus()== SystemConstants.GRADUATE_ABROAD_STATUS_SELF_BACK
                || graduateAbroad.getStatus()==SystemConstants.GRADUATE_ABROAD_STATUS_BACK)
            return "user/party/graduateAbroad/graduateAbroad_au";

        return "user/party/graduateAbroad/graduateAbroad";
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/graduateAbroad_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_graduateAbroad_au(@CurrentUser SysUserView loginUser,
                                   GraduateAbroad record,
                                   String _startTime, String _endTime,
                                   String _saveStartTime, String _saveEndTime,
                                   String _payTime,HttpServletRequest request) {

        Integer userId = loginUser.getId();
        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if(StringUtils.isNotBlank(_startTime)) {
            record.setStartTime(DateUtils.parseDate(_startTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_endTime)) {
            record.setEndTime(DateUtils.parseDate(_endTime, DateUtils.YYYY_MM_DD));
        }
        if(record.getStartTime()==null || record.getEndTime()==null || record.getStartTime().after(record.getEndTime())){
            throw new RuntimeException("出国起止时间有误");
        }
        if(StringUtils.isNotBlank(_saveStartTime)) {
            record.setSaveStartTime(DateUtils.parseDate(_saveStartTime, DateUtils.YYYY_MM_DD));
        }
        if(StringUtils.isNotBlank(_saveEndTime)) {
            record.setSaveEndTime(DateUtils.parseDate(_saveEndTime, DateUtils.YYYY_MM_DD));
        }
        if(record.getSaveStartTime()==null || record.getSaveEndTime()==null || record.getSaveStartTime().after(record.getSaveEndTime())){
            throw new RuntimeException("申请保留组织关系起止时间有误");
        }

        if(StringUtils.isNotBlank(_payTime)) {
            record.setPayTime(DateUtils.parseDate(_payTime, "yyyy-MM"));
        }

        GraduateAbroad graduateAbroad = graduateAbroadService.get(userId);

        if(graduateAbroad!=null && graduateAbroad.getStatus()!=SystemConstants.GRADUATE_ABROAD_STATUS_SELF_BACK
                && graduateAbroad.getStatus()!=SystemConstants.GRADUATE_ABROAD_STATUS_BACK)
            throw new RuntimeException("不允许修改");

        if(StringUtils.isNotBlank(record.getMobile()) &&
                !FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getMobile())){
            return failed("手机号码有误");
        }
        if(StringUtils.isNotBlank(record.getMobile1()) &&
                !FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getMobile1())){
            return failed("国内第一联系人手机号码有误");
        }
        if(StringUtils.isNotBlank(record.getMobile2())
                && !FormUtils.match(PropertiesUtils.getString("mobile.regex"), record.getMobile2())){
            return failed("国内第二联系人手机号码有误");
        }
        record.setUserId(loginUser.getId());
        record.setCreateTime(new Date());
        record.setStatus(SystemConstants.GRADUATE_ABROAD_STATUS_APPLY);
        record.setIsBack(false);
        if (graduateAbroad == null) {
            graduateAbroadService.insertSelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "提交党员出国境组织关系暂留申请"));
            graduateAbroad = record;
        } else {

            graduateAbroadService.updateByPrimaryKeySelective(record);
            logger.info(addLog(SystemConstants.LOG_USER, "修改党员出国境组织关系暂留申请"));
        }
        applyApprovalLogService.add(graduateAbroad.getId(),
                graduateAbroad.getPartyId(), graduateAbroad.getBranchId(), graduateAbroad.getUserId(),
                userId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD,
                "提交",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交党员出国境组织关系暂留申请");

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(SystemConstants.ROLE_MEMBER)
    @RequestMapping(value = "/graduateAbroad_back", method = RequestMethod.POST)
    @ResponseBody
    public Map graduateAbroad_back(@CurrentUser SysUserView loginUser, String remark){

        int userId = loginUser.getId();
        graduateAbroadService.back(userId);
        logger.info(addLog(SystemConstants.LOG_USER, "取消党员出国境组织关系暂留申请"));
        return success(FormUtils.SUCCESS);
    }
}
