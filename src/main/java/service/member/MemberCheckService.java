package service.member;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberCheck;
import domain.member.MemberCheckExample;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.party.BranchMemberService;
import service.party.MemberService;
import service.party.PartyMemberService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.helper.PartyHelper;

import java.util.List;

@Service
public class MemberCheckService extends MemberBaseMapper {

    @Autowired
    protected MemberService memberService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected PartyMemberService partyMemberService;
    @Autowired
    protected BranchMemberService branchMemberService;
    @Autowired
    private ApplyApprovalLogService applyApprovalLogService;
    @Autowired
    protected LoginUserService loginUserService;

    // 获取提交的申请（未审批）
    public MemberCheck getApply(int userId) {

        MemberCheckExample example = new MemberCheckExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andStatusNotEqualTo(MemberConstants.MEMBER_CHECK_STATUS_PASS);
        List<MemberCheck> memberChecks = memberCheckMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return memberChecks.size() > 0 ? memberChecks.get(0) : null;
    }

    // 获取未提交的申请
    public MemberCheck getNotApply(int userId) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        MemberCheck memberCheck = new MemberCheck();
        try {
            PropertyUtils.copyProperties(memberCheck, member);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SysUserView uv = sysUserService.findById(userId);
        memberCheck.setAvatar(uv.getAvatar());
        memberCheck.setMobile(uv.getMobile());
        memberCheck.setNativePlace(uv.getNativePlace());
        memberCheck.setPhone(uv.getPhone());
        memberCheck.setEmail(uv.getEmail());

        return memberCheck;
    }

    public boolean idDuplicate(Integer id, int userId) {

        MemberCheckExample example = new MemberCheckExample();
        MemberCheckExample.Criteria criteria = example.createCriteria()
                .andUserIdEqualTo(userId)
                .andStatusNotEqualTo(MemberConstants.MEMBER_CHECK_STATUS_PASS);
        if (id != null) criteria.andIdNotEqualTo(id);

        return memberCheckMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(MemberCheck record) {

        int userId = record.getUserId();
        if (getApply(userId) != null) {
            throw new OpException("重复申请");
        }

        memberCheckMapper.insertSelective(record);

        int loginUserId = ShiroHelper.getCurrentUserId();
        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), userId,
                loginUserId, (userId == loginUserId) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF :
                        OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CHECK,
                MemberConstants.MEMBER_CHECK_STATUS_MAP.get(record.getStatus()),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "提交申请");
    }

    // 批量审批
    @Transactional
    public void approval(Integer[] ids, boolean pass, String reason) {

        for (Integer id : ids) {
            approval(id, pass, reason);
        }
    }

    // 审核
    @Transactional
    public void approval(int id, boolean pass, String reason) {

        MemberCheck record = new MemberCheck();
        record.setId(id);
        record.setStatus(pass ? MemberConstants.MEMBER_CHECK_STATUS_PASS
                : MemberConstants.MEMBER_CHECK_STATUS_BACK);
        record.setReason(reason);

        memberCheckMapper.updateByPrimaryKeySelective(record);

        MemberCheck memberCheck = memberCheckMapper.selectByPrimaryKey(id);
        int userId = memberCheck.getUserId();
        if (pass) {

            Member member = memberMapper.selectByPrimaryKey(userId);
            if (memberCheck.getPoliticalStatus().byteValue() != member.getPoliticalStatus()) {
                // 改变了党籍状态
                memberService.modifyStatus(userId, memberCheck.getPoliticalStatus(), "审批党员信息申请（变更党籍状态）");
            }

            member.setPoliticalStatus(memberCheck.getPoliticalStatus());
            member.setTransferTime(memberCheck.getTransferTime());
            member.setApplyTime(memberCheck.getApplyTime());
            member.setActiveTime(memberCheck.getActiveTime());
            member.setCandidateTime(memberCheck.getCandidateTime());
            member.setSponsor(memberCheck.getSponsor());
            member.setGrowTime(memberCheck.getGrowTime());
            member.setGrowBranch(memberCheck.getGrowBranch());
            member.setPositiveTime(memberCheck.getPositiveTime());
            member.setPositiveBranch(memberCheck.getPositiveBranch());
            member.setPartyPost(memberCheck.getPartyPost());
            member.setPartyReward(memberCheck.getPartyReward());
            member.setOtherReward(memberCheck.getOtherReward());

            memberService.addModify(userId, "审批党员信息申请");

            memberMapper.updateByPrimaryKey(member); // 覆盖更新

            SysUserInfo ui = new SysUserInfo();
            ui.setUserId(userId);
            ui.setAvatar(memberCheck.getAvatar());
            ui.setMobile(memberCheck.getMobile());
            ui.setNativePlace(memberCheck.getNativePlace());
            ui.setPhone(memberCheck.getPhone());
            ui.setEmail(memberCheck.getEmail());
            sysUserService.insertOrUpdateUserInfoSelective(ui);
        }

        int loginUserId = ShiroHelper.getCurrentUserId();
        applyApprovalLogService.add(id,
                record.getPartyId(), record.getBranchId(), userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CHECK,
                MemberConstants.MEMBER_CHECK_STATUS_MAP.get(record.getStatus()),
                pass ? OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_PASS : OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK,
                "审核");
    }

    @Transactional
    public void del(Integer id) {

        MemberCheck record = memberCheckMapper.selectByPrimaryKey(id);
        int userId = record.getUserId();
        Integer partyId = record.getPartyId();
        Integer branchId = record.getBranchId();

        Integer loginUserId = ShiroHelper.getCurrentUserId();
        if (userId != loginUserId
                && !PartyHelper.hasBranchAuth(loginUserId, partyId, branchId))
            throw new UnauthorizedException();

        memberCheckMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {
            del(id);
        }
    }

    @Transactional
    public void updateByPrimaryKey(MemberCheck record) {

        Assert.isTrue(!idDuplicate(record.getId(), record.getUserId()), "duplicate");
        memberCheckMapper.updateByPrimaryKey(record); // 覆盖更新

        int userId = record.getUserId();
        int loginUserId = ShiroHelper.getCurrentUserId();
        applyApprovalLogService.add(record.getId(),
                record.getPartyId(), record.getBranchId(), userId,
                loginUserId, (userId == loginUserId) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF :
                        OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CHECK,
                MemberConstants.MEMBER_CHECK_STATUS_MAP.get(record.getStatus()),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "更新");
    }

    public Object count(byte status) {

        MemberCheckExample example = new MemberCheckExample();
        MemberCheckExample.Criteria criteria = example.createCriteria().andStatusEqualTo(status);
        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        return (int) memberCheckMapper.countByExample(example);
    }
}
