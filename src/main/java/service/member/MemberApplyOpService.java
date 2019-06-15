package service.member;

import controller.global.OpException;
import domain.member.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.party.MemberService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/4/23.
 */
@Service
public class MemberApplyOpService extends MemberBaseMapper {

    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    private MemberApplyService memberApplyService;
    @Autowired
    private ApplyOpenTimeService applyOpenTimeService;
    @Autowired
    private ApplyApprovalLogService applyApprovalLogService;
    @Autowired
    private ApplySnService applySnService;

    private VerifyAuth<MemberApply> checkVerityAuth(int userId){
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth(memberApply, memberApply.getPartyId(), memberApply.getBranchId());
    }

    private VerifyAuth<MemberApply> checkVerityAuth2(int userId){
        MemberApply memberApply = memberApplyService.get(userId);
        return super.checkVerityAuth2(memberApply, memberApply.getPartyId());
    }

    // 申请：拒绝申请
    @Transactional
    public void apply_deny(Integer[] userIds, String remark) {

        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            if (memberApply != null && memberApply.getStage() != OwConstants.OW_APPLY_STAGE_DENY) {
                enterApplyService.applyBack(userId, remark, OwConstants.OW_ENTER_APPLY_STATUS_ADMIN_ABORT);
                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_INIT),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_DENY, "入党申请未通过");
            }
        }
    }
    // 申请：通过申请
    @Transactional
    public void apply_pass(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            MemberApply record = new MemberApply();
            record.setStage(OwConstants.OW_APPLY_STAGE_PASS);
            record.setPassTime(new Date());
            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_INIT);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {
                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_INIT),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS, "通过入党申请");
            }
        }
    }
    // 申请：确定为入党积极分子
    @Transactional
    public void apply_active(Integer[] userIds, Date activeTime, int loginUserId){

        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            MemberApply record = new MemberApply();
            record.setStage(OwConstants.OW_APPLY_STAGE_ACTIVE);
            record.setActiveTime(activeTime);
            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_PASS);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_ACTIVE),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS, "成为积极分子");
            }
        }
    }

    // 积极分子：提交 确定为发展对象
    @Transactional
    public void apply_candidate(Integer[] userIds, String _candidateTime,
                                String _candidateTrainStartTime, String _candidateTrainEndTime, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;

            DateTime dt = new DateTime(memberApply.getActiveTime());
            DateTime afterActiveTimeOneYear = dt.plusYears(1);
            if (afterActiveTimeOneYear.isAfterNow()) {
                throw new OpException("确定为入党积极分子满一年之后才能被确定为发展对象。");
            }

            Date candidateTime = DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD);
            if(candidateTime.before(afterActiveTimeOneYear.toDate())){
                throw new OpException("确定为发展对象时间应该在确定为入党积极分子满一年之后");
            }

            Date candidateTrainStartTime = DateUtils.parseDate(_candidateTrainStartTime, DateUtils.YYYY_MM_DD);
            if(candidateTrainStartTime!=null && candidateTrainStartTime.before(memberApply.getActiveTime())){
                throw new OpException("培训起始时间应该在确定为入党积极分子之后");
            }

            Date candidateTrainEndTime = DateUtils.parseDate(_candidateTrainEndTime, DateUtils.YYYY_MM_DD);
            if(candidateTrainEndTime!=null && candidateTrainEndTime.before(candidateTrainStartTime)){
                throw new OpException("培训结束时间应该在培训起始时间之后");
            }

            MemberApply record = new MemberApply();
            record.setCandidateTime(DateUtils.parseDate(_candidateTime, DateUtils.YYYY_MM_DD));
            record.setCandidateTrainStartTime(DateUtils.parseDate(_candidateTrainStartTime, DateUtils.YYYY_MM_DD));
            record.setCandidateTrainEndTime(DateUtils.parseDate(_candidateTrainEndTime, DateUtils.YYYY_MM_DD));

            if(directParty && partyAdmin){ // 直属党支部管理员，不需要通过审核，直接确定发展对象
                record.setStage(OwConstants.OW_APPLY_STAGE_CANDIDATE);
                record.setCandidateStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
            } else {
                record.setCandidateStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
            }

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_ACTIVE);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  (directParty && partyAdmin)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_CANDIDATE),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        (directParty && partyAdmin)?"确定为发展对象，直属党支部提交":"确定为发展对象，党支部提交");
            }
        }
    }

    // 积极分子：审核 确定为发展对象
    @Transactional
    public void apply_candidate_check(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;

            MemberApply record = new MemberApply();
            record.setStage(OwConstants.OW_APPLY_STAGE_CANDIDATE);
            record.setCandidateStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_ACTIVE)
                    .andCandidateStatusEqualTo(OwConstants.OW_APPLY_STATUS_UNCHECKED);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_CANDIDATE),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS, "确定为发展对象，已审核");
            }
        }
    }

    // 发展对象：提交 列入发展计划
    @Transactional
    public void apply_plan(Integer[] userIds, String _planTime, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;
            int partyId = memberApply.getPartyId();

            if(!applyOpenTimeService.isOpen(partyId, OwConstants.OW_APPLY_STAGE_PLAN)){
                throw new OpException("不在开放时间范围");
            }
            Date planTime = DateUtils.parseDate(_planTime, DateUtils.YYYY_MM_DD);
            if(planTime.before(memberApply.getCandidateTime())){
                throw new OpException("列入发展计划时间应该在确定为发展对象之后");
            }

            MemberApply record = new MemberApply();
            if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过审核
                record.setStage(OwConstants.OW_APPLY_STAGE_PLAN);
                record.setPlanStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
            }else{
                record.setPlanStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
            }
            record.setPlanTime(planTime);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_CANDIDATE);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, (directParty && partyAdmin)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_PLAN),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        (directParty && partyAdmin)?"列入发展计划，直属党支部提交":"列入发展计划，党支部提交");
            }
        }
    }

    // 发展对象：审核 列入发展计划
    @Transactional
    public void apply_plan_check(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;
            Integer partyId = memberApply.getPartyId();

            if(!applyOpenTimeService.isOpen(partyId, OwConstants.OW_APPLY_STAGE_PLAN)){
                throw new OpException("不在开放时间范围");
            }
            MemberApply record = new MemberApply();
            record.setStage(OwConstants.OW_APPLY_STAGE_PLAN);
            record.setPlanStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_CANDIDATE)
                    .andPlanStatusEqualTo(OwConstants.OW_APPLY_STATUS_UNCHECKED);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_PLAN),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "发展对象，已审核列入发展计划");
            }
        }
    }

    // 列入发展计划：提交 领取志愿书
    /*@Transactional
    public void apply_draw(int[] userIds, String _drawTime, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;

            Date drawTime = DateUtils.parseDate(_drawTime, DateUtils.YYYY_MM_DD);
            if(drawTime.before(memberApply.getPlanTime())){
                throw new OpException("领取志愿书时间应该在列入发展计划之后");
            }

            MemberApply record = new MemberApply();
            if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过审核
                record.setStage(OwConstants.OW_APPLY_STAGE_DRAW);
                record.setDrawStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
            }else {
                record.setDrawStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
            }
            record.setDrawTime(drawTime);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_PLAN);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, (directParty && partyAdmin)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_DRAW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        (directParty && partyAdmin)?"领取志愿书，直属党支部提交":"领取志愿书，党支部提交");
            }
        }
    }*/

    // 列入发展计划：审核 领取志愿书
    /*@Transactional
    public void apply_draw_check(int[] userIds, int loginUserId){

        for (int userId : userIds) {

            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;

            MemberApply record = new MemberApply();
            record.setStage(OwConstants.OW_APPLY_STAGE_DRAW);
            record.setDrawStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_PLAN)
                    .andDrawStatusEqualTo(OwConstants.OW_APPLY_STATUS_UNCHECKED);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_DRAW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "领取志愿书，已审核");
            }
        }
    }*/

    // 分党委直接提交，不需要审核 -- 20160608 修改by 邹老师
    // 列入发展计划：提交 领取志愿书
    @Transactional
    public void apply_draw(Integer[] userIds, String _drawTime, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;

            Date drawTime = DateUtils.parseDate(_drawTime, DateUtils.YYYY_MM_DD);
            if(drawTime.before(memberApply.getPlanTime())){
                throw new OpException("领取志愿书时间应该在列入发展计划之后");
            }

            MemberApply record = new MemberApply();

            record.setStage(OwConstants.OW_APPLY_STAGE_DRAW);
            record.setDrawStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

            record.setDrawTime(drawTime);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_PLAN);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_DRAW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "领取志愿书，分党委提交");
            }
        }
    }



    /*// 领取志愿书：审核 预备党员  【领取志愿书是需要组织部审批的，这样我们能够控制他们领取志愿书的人和数量的对应】
    @Transactional
    public void apply_grow_check(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {

            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;

            if(memberApply.getStage()!=OwConstants.OW_APPLY_STAGE_DRAW
                    || memberApply.getGrowStatus()==null
                    || memberApply.getGrowStatus()!= OwConstants.OW_APPLY_STATUS_UNCHECKED){
                throw new OpException("还没有提交发展时间。");
            }

            //boolean isParty = verifyAuth.isParty;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;

            MemberApply record = new MemberApply();
            record.setGrowStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_DRAW)
                    .andGrowStatusEqualTo(OwConstants.OW_APPLY_STATUS_UNCHECKED);

            *//*if(isParty){ // 分党委审核，需要跳过下一步的组织部审核
                memberApplyService.applyGrowCheckByParty(userId, record, example);
                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "预备党员，分党委审核");
            }else *//*if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  (directParty && partyAdmin)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "预备党员，分党委审核");
            }
        }
    }*/

    // 领取志愿书：组织部管理员审核
    @Transactional
    public void apply_grow_od_check(Integer[] userIds,
                                    int startSnId,
                                    Integer endSnId,
                                    int loginUserId){

        ApplySn startApplySn = applySnMapper.selectByPrimaryKey(startSnId);
        List<ApplySn> applySns = new ArrayList<>();
        if(endSnId!=null){
            ApplySn endApplySn = applySnMapper.selectByPrimaryKey(endSnId);

            ApplySnExample example = new ApplySnExample();
            example.createCriteria()
                    .andYearEqualTo(DateUtils.getCurrentYear())
                    .andSnGreaterThanOrEqualTo(startApplySn.getSn())
                    .andSnLessThanOrEqualTo(endApplySn.getSn())
                    .andIsUsedEqualTo(false)
                    .andIsAbolishedEqualTo(false);
            example.setOrderByClause("sn asc");
            applySns = applySnMapper.selectByExample(example);
        }else{
            applySns.add(startApplySn);
        }
        if(applySns.size()==0 || applySns.size()!=userIds.length){
            throw new OpException("待分配编码的数量[{0}个]和选择的人数[{1}人]不符。", applySns.size(), userIds.length);
        }

        for (int i = 0; i < userIds.length; i++) {

            int userId = userIds[i];
            ApplySn applySn = applySns.get(i);

            MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if(_memberApply.getStage()!=OwConstants.OW_APPLY_STAGE_DRAW){
                throw new OpException("状态异常，还没到领取志愿书阶段。");
            }

            MemberApply record = new MemberApply();
            record.setGrowStatus(OwConstants.OW_APPLY_STATUS_OD_CHECKED);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_DRAW);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                // 分配志愿书编码
                applySnService.assign(userId, applySn);

                MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "领取志愿书，组织部审核");
            }
        }
    }

    // 领取志愿书：2、支部提交
    @Transactional
    public void apply_grow(Integer[] userIds, String _growTime, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply _memberApply = verifyAuth.entity;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;

            String realname = _memberApply.getUser().getRealname();
            if(_memberApply.getGrowStatus()!=null &&
                    _memberApply.getGrowStatus()==OwConstants.OW_APPLY_STATUS_UNCHECKED){
                throw new OpException("{0}已提交，请勿重复操作。", realname);
            }
            if(_memberApply.getGrowStatus()==null ||
                    _memberApply.getGrowStatus()!=OwConstants.OW_APPLY_STATUS_OD_CHECKED){
                throw new OpException("{0}待组织部审核之后，才能提交。", realname);
            }

            if(_memberApply.getStage()!=OwConstants.OW_APPLY_STAGE_DRAW){
                throw new OpException("{0}状态异常，还没到领取志愿书阶段。", realname);
            }

            Date growTime = DateUtils.parseDate(_growTime, DateUtils.YYYY_MM_DD);
            if(_memberApply.getDrawTime()==null){
                throw new OpException("{0}领取志愿书时间为空", realname);
            }
            if(growTime.before(_memberApply.getDrawTime())){
                throw new OpException("{0}发展时间应该在领取志愿书之后", realname);
            }

            if(directParty && partyAdmin){

                memberApplyService.memberGrowByDirectParty(userId, growTime);
                applyApprovalLogService.add(userId,
                        _memberApply.getPartyId(), _memberApply.getBranchId(), userId,
                        loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "领取志愿书，直属党支部提交");
            }else {
                MemberApply record = new MemberApply();
                record.setGrowStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
                record.setGrowTime(growTime);

                MemberApplyExample example = new MemberApplyExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andStageEqualTo(OwConstants.OW_APPLY_STAGE_DRAW)
                        .andGrowStatusEqualTo(OwConstants.OW_APPLY_STATUS_OD_CHECKED);

                if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                    MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
                    applyApprovalLogService.add(userId,
                            memberApply.getPartyId(), memberApply.getBranchId(), userId,
                            loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                            OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                            OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                            OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                            "领取志愿书，支部提交");
                }
            }
        }
    }

    // 领取志愿书：3、分党委审核
    @Transactional
    public void apply_grow_check(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {
            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;

            memberApplyService.memberGrowByParty(userId);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_GROW),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                    "领取志愿书，分党委审核");
        }
    }

    // 预备党员：提交 正式党员
    @Transactional
    public void apply_positive(Integer[] userIds, String _positiveTime, int loginUserId){

        for (int userId : userIds) {

            Member member = memberService.get(userId);
            if(member.getStatus()!= MemberConstants.MEMBER_STATUS_NORMAL){
                SysUserView uv = sysUserService.findById(userId);
                throw new OpException(uv.getRealname()+"组织关系已经转出");
            }

            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth(userId);
            MemberApply memberApply = verifyAuth.entity;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;

            Date positiveTime = DateUtils.parseDate(_positiveTime, DateUtils.YYYY_MM_DD);
            if(memberApply.getGrowTime()!=null) { // 后台添加的党员，入党时间可能为空
                if (positiveTime.before(memberApply.getGrowTime())) {
                    throw new OpException("转正时间应该在发展之后");
                }
            }

            MemberApply record = new MemberApply();
            if(directParty && partyAdmin) { // 直属党支部管理员，不需要通过分党委审核
                record.setPositiveStatus(OwConstants.OW_APPLY_STATUS_CHECKED);
            }else {
                record.setPositiveStatus(OwConstants.OW_APPLY_STATUS_UNCHECKED);
            }
            record.setPositiveTime(positiveTime);

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_GROW);

            if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  (directParty && partyAdmin)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_POSITIVE),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        (directParty && partyAdmin)?"正式党员，直属党支部提交":"正式党员，党支部提交");

            }
        }
    }

    // 预备党员：审核 正式党员
    @Transactional
    public void apply_positive_check(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {

            Member member = memberService.get(userId);
            if(member.getStatus()!=MemberConstants.MEMBER_STATUS_NORMAL){
                SysUserView uv = sysUserService.findById(userId);
                throw new OpException(uv.getRealname()+"组织关系已经转出");
            }

            VerifyAuth<MemberApply> verifyAuth = checkVerityAuth2(userId);
            MemberApply memberApply = verifyAuth.entity;
            boolean isParty = verifyAuth.isParty;
            boolean partyAdmin = verifyAuth.isPartyAdmin;
            boolean directParty = verifyAuth.isDirectBranch;
            MemberApply record = new MemberApply();
            record.setPositiveStatus(OwConstants.OW_APPLY_STATUS_CHECKED);

            if(memberApply.getPositiveStatus()==null ||
                    memberApply.getPositiveStatus()!=OwConstants.OW_APPLY_STATUS_UNCHECKED){
                throw new OpException("党支部管理员还未提交转正时间");
            }

            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andStageEqualTo(OwConstants.OW_APPLY_STAGE_GROW)
                    .andPositiveStatusEqualTo(OwConstants.OW_APPLY_STATUS_UNCHECKED);

            if(isParty){ // 分党委审核，需要跳过下一步的组织部审核
                memberApplyService.applyPositiveCheckByParty(userId, record, example);

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_POSITIVE),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "正式党员，分党委审核");
            }else if (memberApplyService.updateByExampleSelective(userId, record, example) > 0) {

                applyApprovalLogService.add(userId,
                        memberApply.getPartyId(), memberApply.getBranchId(), userId,
                        loginUserId, (directParty && partyAdmin)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                                OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH,
                        OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_POSITIVE),
                        OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                        "正式党员，党总支直属党支部审核");
            }
        }
    }

    // 预备党员：组织部管理员审核 正式党员
    @Transactional
    public void apply_positive_check2(Integer[] userIds, int loginUserId){

        for (int userId : userIds) {

            Member member = memberService.get(userId);
            if(member.getStatus()!=MemberConstants.MEMBER_STATUS_NORMAL){
                SysUserView uv = sysUserService.findById(userId);
                throw new OpException(uv.getRealname()+"组织关系已经转出");
            }

            memberApplyService.memberPositive(userId);

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_POSITIVE),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS,
                    "正式党员，组织部审核");
        }
    }

    @Transactional
    public void memberApply_back(Integer[] userIds,
                                 byte stage, String reason, int loginUserId,
                                 // 如果已领志愿书编码，需要确认打回后编码如何处理（作废或重新使用）
                                 Boolean applySnReuse){

        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            Boolean presentPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, memberApply.getPartyId());
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) && !presentPartyAdmin) {
                throw new UnauthorizedException();
            }

            byte _stage = memberApply.getStage();
            if(stage>_stage || stage<OwConstants.OW_APPLY_STAGE_INIT || stage==OwConstants.OW_APPLY_STAGE_PASS){
                throw new OpException("打回状态有误。");
            }
            String applySnOp = "";
            // 打回领取志愿书或领取志愿书之前，需要清除志愿书编码
            if(_stage>=OwConstants.OW_APPLY_STAGE_DRAW && stage <= OwConstants.OW_APPLY_STAGE_DRAW){

                applySnService.clearAssign(userId, BooleanUtils.isTrue(applySnReuse));
                if(StringUtils.isNotBlank(memberApply.getApplySn())) {
                    applySnOp = "(" + (BooleanUtils.isTrue(applySnReuse) ? "重新使用编码" : "作废编码") + ")";
                }
            }

            memberApplyService.memberApply_back(userId, stage);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, OwConstants.OW_APPLY_STAGE_MAP.get(stage),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK,
                    reason + applySnOp);
        }
    }

    //移除记录（只允许移除未发展的）
    @Transactional
    public void memberApply_remove(Integer[] userIds, boolean isRemove, String reason,
                                   // 如果已领志愿书编码，需要确认打回后编码如何处理（作废或重新使用）
                                 Boolean applySnReuse) {

        int loginUserId = ShiroHelper.getCurrentUserId();
        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            Boolean presentPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, memberApply.getPartyId());
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) && !presentPartyAdmin) {
                throw new UnauthorizedException();
            }
            byte stage = memberApply.getStage();
            if(stage>=OwConstants.OW_APPLY_STAGE_GROW ){
                throw new OpException("已是党员，不可移除。");
            }

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setIsRemove(isRemove);
            memberApplyMapper.updateByPrimaryKeySelective(record);

             String applySnOp = "";
            // 清除已使用的志愿书编码，如果有的话
            applySnService.clearAssign(userId, applySnReuse);
            if(StringUtils.isNotBlank(memberApply.getApplySn())) {
                applySnOp = "(" + (BooleanUtils.isTrue(applySnReuse) ? "重新使用编码" : "作废编码") + ")";
            }

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, isRemove?"移除":"撤销移除",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, reason + applySnOp);
        }
    }

    //提交未通过的申请
    @Transactional
    public void batchApply(Integer[] userIds) {

        int loginUserId = ShiroHelper.getCurrentUserId();
        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            Boolean presentPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, memberApply.getPartyId());
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) && !presentPartyAdmin) {
                throw new UnauthorizedException();
            }
            byte stage = memberApply.getStage();
            if(stage!=OwConstants.OW_APPLY_STAGE_DENY ){
                throw new OpException("{0}已进入发展流程，不可重复提交申请。", memberApply.getUser().getRealname());
            }

            memberApply.setCreateTime(new Date());
            memberApply.setStage(OwConstants.OW_APPLY_STAGE_INIT);
            enterApplyService.memberApply(memberApply);

            applyApprovalLogService.add(userId,
                memberApply.getPartyId(), memberApply.getBranchId(), userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                OwConstants.OW_APPLY_STAGE_MAP.get(OwConstants.OW_APPLY_STAGE_INIT),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交入党申请（管理员）");
        }
    }

    //删除记录（只允许删除未通过的）
    @Transactional
    public void batchDel(Integer[] userIds) {

        int loginUserId = ShiroHelper.getCurrentUserId();
        for (int userId : userIds) {
            MemberApply memberApply = memberApplyService.get(userId);
            Boolean presentPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, memberApply.getPartyId());
            if (!ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL) && !presentPartyAdmin) {
                throw new UnauthorizedException();
            }
            byte stage = memberApply.getStage();
            if(stage!=OwConstants.OW_APPLY_STAGE_DENY ){
                throw new OpException("{0}已进入发展流程，不可删除。", memberApply.getUser().getRealname());
            }

            memberApplyService.del(userId);

            applyApprovalLogService.add(null,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    loginUserId,  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "删除",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);
        }
    }
}
