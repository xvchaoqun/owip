package controller.member.user;

import controller.member.MemberBaseController;
import domain.member.Member;
import domain.member.MemberStay;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserMemberStayController extends MemberBaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/memberStay")
    public String memberStay(Integer userId, // 申请人
                             Integer id, // memberStay记录ID, 管理员在后台修改时传入
                             byte type,
                             // 在已完成的审批的情况下，是否新提交申请
                             @RequestParam(required = false, defaultValue = "0") boolean isNew,
                             ModelMap modelMap) {

        if(id!=null){ // 如果是后台修改的情况
            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
            userId = memberStay.getUserId();
            type = memberStay.getType();
        }

        modelMap.put("type", type);

        if(userId==null) {
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
            if (!PartyHelper.hasBranchAuth(loginUserId, partyId, branchId))
                throw new UnauthorizedException();
        }

        MemberStay memberStay = memberStayService.get(userId, type);
        modelMap.put("canSubmit", memberStay==null || memberStay.getType()==type
                || (memberStay.getStatus()<=MemberConstants.MEMBER_STAY_STATUS_BACK));
        if(memberStay!=null){
            byte hasSubmitType = memberStay.getType();
            modelMap.put("hasSubmitType", hasSubmitType);

            if(hasSubmitType != type){
                memberStay = null;
            }

            // 已完成审批，需要新提交申请
            if(memberStay.getStatus()== MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY && isNew){
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

    @RequestMapping(value = "/memberStay_au", method = RequestMethod.POST)
    @ResponseBody
    public Map do_memberStay_au(Integer userId, // 申请人
                                   MemberStay record,
                                    MultipartFile _letter,
                                   HttpServletRequest request) throws IOException, InterruptedException {

        if(userId==null) {
            // 确认普通用户只能提交自己的申请
            userId = ShiroHelper.getCurrentUserId();
        }

        if(record.getId()!=null){
            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(record.getId());
            userId = memberStay.getUserId();
        }

        Member member = memberService.get(userId);
        Integer partyId = member.getPartyId();
        Integer branchId = member.getBranchId();

        Integer loginUserId = ShiroHelper.getCurrentUserId();

        boolean selfSubmit = (userId.intValue() == loginUserId);
        // 非本人提交，要验证权限
        if(!selfSubmit){
            //===========权限
            if (!PartyHelper.hasBranchAuth(loginUserId, partyId, branchId))
                throw new UnauthorizedException();
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

            /*String OriginalFileName = _letter.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String realPath = FILE_SEPARATOR
                    + "member_stay" + FILE_SEPARATOR + DateUtils.formatDate(new Date(), "yyyyMM")
                    + FILE_SEPARATOR + fileName + FileUtils.getExtention(OriginalFileName);

            FileUtils.copyFile(_letter, new File(springProps.uploadPath + realPath));*/

            String savePath = upload(_letter, "member_stay");
            record.setLetter(savePath);
        }

        MemberStay memberStay = memberStayService.get(userId, record.getType());

        // 不允许本人修改
        if(selfSubmit && memberStay!=null && memberStay.getStatus()!=MemberConstants.MEMBER_STAY_STATUS_SELF_BACK
                && memberStay.getStatus()!=MemberConstants.MEMBER_STAY_STATUS_BACK) {
            if(record.getId()==null && memberStay.getStatus()==MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY) {
                // 新提交申请
                memberStay = null;
            }else{
                return failed("不允许修改");
            }
        }

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
                if(memberStay.getStatus()<=MemberConstants.MEMBER_STAY_STATUS_BACK){
                    record.setStatus(MemberConstants.MEMBER_STAY_STATUS_APPLY); // 修改并提交
                }
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

    @RequestMapping(value = "/memberStay_back", method = RequestMethod.POST)
    @ResponseBody
    public Map memberStay_back(int id,  String remark){

        memberStayService.back(id);
        logger.info(addLog(LogConstants.LOG_USER, "取消组织关系暂留申请"));
        return success(FormUtils.SUCCESS);
    }
}
