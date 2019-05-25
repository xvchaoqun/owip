package controller.member.user;

import controller.member.MemberBaseController;
import domain.member.Member;
import domain.member.MemberStay;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FileUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserMemberStayController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequiresRoles(value = {RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_ODADMIN,
            RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping("/memberStay")
    public String memberStay(Integer userId, // 申请人
                             Integer id, // memberStay记录ID, 管理员在后台修改时传入
                             byte type,
                             ModelMap modelMap) {

        if(id!=null){ // 如果是后台修改的情况
            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
            userId = memberStay.getUserId();
            type = memberStay.getType();
        }

        modelMap.put("type", type);

        if(userId==null || !ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN)) {
            // 确认普通用户只能提交自己的申请
            userId = ShiroHelper.getCurrentUserId();
        }

        Member member = memberService.get(userId);
        Integer partyId = member.getPartyId();
        Integer branchId = member.getBranchId();

        Integer loginUserId = ShiroHelper.getCurrentUserId();

        boolean selfSubmit = (userId.intValue() == loginUserId);
        // 非本人提交，要验证权限
        if(!selfSubmit){
            //===========权限
            Subject subject = SecurityUtils.getSubject();
            if (!subject.hasRole(RoleConstants.ROLE_ODADMIN)) { // 支部或分党委管理员都有权限
                boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
                if (!isAdmin && branchId != null) {
                    isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
                }
                if (!isAdmin) throw new UnauthorizedException();
            }
        }

        MemberStay memberStay = memberStayService.get(userId);
        modelMap.put("canSubmit", memberStay==null || memberStay.getType()==type
                || (memberStay.getStatus()<=MemberConstants.MEMBER_STAY_STATUS_BACK));
        if(memberStay!=null){
            byte hasSubmitType = memberStay.getType();
            modelMap.put("hasSubmitType", hasSubmitType);

            if(hasSubmitType != type){
                memberStay = null;
            }
        }

        modelMap.put("userBean", userBeanService.get(userId));
        modelMap.put("student", studentInfoService.get(userId));
        modelMap.put("memberStay", memberStay);

        modelMap.put("countryList", countryService.getCountryList());

        if(!selfSubmit){ // 管理员可在任意状态下修改
            return "member/user/memberStay/memberStay_au";
        }

        if(memberStay==null || memberStay.getStatus()== MemberConstants.MEMBER_STAY_STATUS_SELF_BACK
                || memberStay.getStatus()==MemberConstants.MEMBER_STAY_STATUS_BACK)
            return "member/user/memberStay/memberStay_au";

        return "member/user/memberStay/memberStay";
    }

    @RequiresRoles(value = {RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_ODADMIN,
            RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/memberStay_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_au(Integer userId, // 申请人
                                   MemberStay record,
                                    MultipartFile _letter,
                                   HttpServletRequest request) {

        if(userId==null || !ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN)) {
            // 确认普通用户只能提交自己的申请
            userId = ShiroHelper.getCurrentUserId();
        }

        // 如果是管理员修改则以record.id为准
        if(ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ODADMIN,
                RoleConstants.ROLE_PARTYADMIN, RoleConstants.ROLE_BRANCHADMIN)){
            if(record.getId()!=null){
                MemberStay memberStay = memberStayMapper.selectByPrimaryKey(record.getId());
                userId = memberStay.getUserId();
            }
        }


        Member member = memberService.get(userId);
        Integer partyId = member.getPartyId();
        Integer branchId = member.getBranchId();

        Integer loginUserId = ShiroHelper.getCurrentUserId();

        boolean selfSubmit = (userId.intValue() == loginUserId);
        // 非本人提交，要验证权限
        if(!selfSubmit){
            //===========权限
            Subject subject = SecurityUtils.getSubject();
            if (!subject.hasRole(RoleConstants.ROLE_ODADMIN)) { // 支部或分党委管理员都有权限
                boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);
                if (!isAdmin && branchId != null) {
                    isAdmin = branchMemberService.isPresentAdmin(loginUserId, partyId, branchId);
                }
                if (!isAdmin) throw new UnauthorizedException();
            }
        }

        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if(StringUtils.isNotBlank(record.getMobile()) &&
                !CmTag.validMobile(record.getMobile())){
            return failed("手机号码有误");
        }

        if(record.getType() == MemberConstants.MEMBER_STAY_TYPE_ABROAD) {
            if (record.getStartTime() == null || record.getEndTime() == null
                    || record.getStartTime().after(record.getEndTime())) {
               return failed("出国起止时间有误");
            }
            if(StringUtils.isNotBlank(record.getMobile1()) &&
                    !CmTag.validMobile(record.getMobile1())){
                return failed("国内第一联系人手机号码有误");
            }
            if(StringUtils.isNotBlank(record.getMobile2())
                    && !CmTag.validMobile(record.getMobile2())){
                return failed("国内第二联系人手机号码有误");
            }
        }else{
            if(StringUtils.isNotBlank(record.getMobile1()) &&
                    !CmTag.validMobile(record.getMobile1())){
                return failed("联系人手机号码有误");
            }
        }


        if(record.getSaveStartTime()==null || record.getSaveEndTime()==null
                || record.getSaveStartTime().after(record.getSaveEndTime())){
           return failed("申请保留组织关系起止时间有误");
        }

        if (_letter != null && !_letter.isEmpty()) {

            String OriginalFileName = _letter.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "member_stay" + FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMM")
                    + FILE_SEPARATOR + fileName + FileUtils.getExtention(OriginalFileName);

            FileUtils.copyFile(_letter, new File(springProps.uploadPath + realPath));

            record.setLetter(realPath);
        }

        MemberStay memberStay = memberStayService.get(userId);

        // 不允许本人修改
        if(selfSubmit && memberStay!=null && memberStay.getStatus()!=MemberConstants.MEMBER_STAY_STATUS_SELF_BACK
                && memberStay.getStatus()!=MemberConstants.MEMBER_STAY_STATUS_BACK)
           return failed("不允许修改");

        if(memberStay!=null){
            // 上一次提交了不同类型，则应清除
            byte hasSubmitType = memberStay.getType();
            if(hasSubmitType != record.getType())
                memberStay = null;
        }


        record.setUserId(userId);
        record.setCreateTime(new Date());
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_APPLY);
        record.setIsBack(false);
        if (memberStay == null) {
            memberStayService.insertSelective(record);
            logger.info(addLog(LogConstants.LOG_USER, "提交组织关系暂留申请"));
            memberStay = record;
        } else {

            if(!selfSubmit) {
                record.setType(null); // 管理员不能修改类型和状态等
                record.setStatus(null);
                record.setIsBack(null);
                record.setCreateTime(null);
            }
            memberStayService.updateByPrimaryKeySelective(record);
            logger.info(addLog(LogConstants.LOG_USER, "修改提交组织关系暂留申请"));
        }
        applyApprovalLogService.add(memberStay.getId(),
                memberStay.getPartyId(), memberStay.getBranchId(), memberStay.getUserId(),
                loginUserId, selfSubmit? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF
                        :OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY,
                (memberStay == null)?"提交":"修改提交",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "组织关系暂留申请");

        return success(FormUtils.SUCCESS);
    }

    @RequiresRoles(RoleConstants.ROLE_MEMBER)
    @RequestMapping(value = "/memberStay_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberStay_back(@CurrentUser SysUserView loginUser, String remark){

        int userId = loginUser.getId();
        memberStayService.back(userId);
        logger.info(addLog(LogConstants.LOG_USER, "取消组织关系暂留申请"));
        return success(FormUtils.SUCCESS);
    }
}
