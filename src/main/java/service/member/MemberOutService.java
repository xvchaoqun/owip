package service.member;

import controller.global.OpException;
import domain.member.MemberOut;
import domain.member.MemberOutExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.party.MemberService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberOutService extends MemberBaseMapper {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberQuitService memberQuitService;
    @Autowired
    private MemberOpService memberOpService;

    @Autowired
    private PartyService partyService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    @Autowired
    private LoginUserService loginUserService;

    public MemberOut findByUserId(Integer userId) {

        MemberOutExample example = new MemberOutExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusIn(Arrays.asList(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY,
                MemberConstants.MEMBER_OUT_STATUS_ARCHIVE));
        List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);

        return memberOuts.size() > 0 ? memberOuts.get(0) : null;
    }

    private VerifyAuth<MemberOut> checkVerityAuth(int id) {
        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberOut, memberOut.getPartyId(), memberOut.getBranchId());
    }

    private VerifyAuth<MemberOut> checkVerityAuth2(int id) {
        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberOut, memberOut.getPartyId());
    }

    public int count(Integer partyId, Integer branchId, byte type, Byte cls) {

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (type == 1) { //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_APPLY);
        } else if (type == 2) { //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        } else {
            throw new OpException("审核类型错误");
        }
        if (cls != null) {
            if (cls == 1 || cls == 6) {// 分党委审核（新申请) / 组织部审核（新申请)
                criteria.andIsBackNotEqualTo(true);
            } else if (cls == 4 || cls == 7) {// 分党委审核（返回修改) / 组织部审核（返回修改)
                criteria.andIsBackEqualTo(true);
            }
        }
        if (partyId != null) criteria.andPartyIdEqualTo(partyId);
        if (branchId != null) criteria.andBranchIdEqualTo(branchId);

        return (int) memberOutMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberOut next(MemberOut memberOut, byte type, byte cls) {

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (type == 1) { //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_APPLY);
        } else if (type == 2) { //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        } else {
            throw new OpException("审核类型错误");
        }
        if (cls == 1 || cls == 6) {// 分党委审核（新申请) / 组织部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        } else if (cls == 4 || cls == 7) {// 分党委审核（返回修改) / 组织部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if (memberOut != null)
            criteria.andUserIdNotEqualTo(memberOut.getUserId()).andApplyTimeLessThanOrEqualTo(memberOut.getApplyTime());
        example.setOrderByClause("apply_time desc");

        List<MemberOut> memberApplies = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size() == 0) ? null : memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberOut last(MemberOut memberOut, byte type, byte cls) {

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (type == 1) { //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_APPLY);
        } else if (type == 2) { //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        } else {
            throw new OpException("审核类型错误");
        }
        if (cls == 1 || cls == 6) {// 分党委审核（新申请) / 组织部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        } else if (cls == 4 || cls == 7) {// 分党委审核（返回修改) / 组织部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if (memberOut != null)
            criteria.andUserIdNotEqualTo(memberOut.getUserId()).andApplyTimeGreaterThanOrEqualTo(memberOut.getApplyTime());
        example.setOrderByClause("apply_time asc");

        List<MemberOut> memberApplies = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size() == 0) ? null : memberApplies.get(0);
    }

   /* public boolean idDuplicate(Integer id, Integer userId) {

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if (id != null) criteria.andIdNotEqualTo(id);

        return memberOutMapper.countByExample(example) > 0;
    }*/

    // 上一次转出记录（已完成或者还在申请过程中）
    public MemberOut getLatest(int userId) {

        MemberOutExample example = new MemberOutExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("apply_time desc");
        List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);
        if (memberOuts.size() > 0) return memberOuts.get(0);

        return null;
    }

    // 已转出记录（历史记录）
    public List<MemberOut> memberOutList(int userId) {

        MemberOutExample example = new MemberOutExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);
        example.setOrderByClause("apply_time desc");

        return memberOutMapper.selectByExample(example);
    }

    // 本人撤回
    @Transactional
    public void back(int userId) {

        MemberOut memberOut = getLatest(userId);
        if (memberOut == null || memberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setUserId(userId);
        record.setStatus(MemberConstants.MEMBER_OUT_STATUS_SELF_BACK);
        //record.setBranchId(memberOut.getBranchId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(memberOut.getId(),
                memberOut.getPartyId(), memberOut.getBranchId(), memberOut.getUserId(),
                ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT,
                "撤回",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回组织关系转出申请");
    }

    // 不通过
    @Transactional
    public void deny(int userId, String reason) {

        MemberOut memberOut = getLatest(userId);
        if (memberOut == null || memberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setStatus(MemberConstants.MEMBER_OUT_STATUS_BACK);
        record.setReason(reason);
        record.setUserId(userId);
        //record.setBranchId(memberOut.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 分党委、党总支、直属党支部 通过
    @Transactional
    public void check1(int id) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        if (memberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_APPLY)
            throw new OpException("状态异常");
        int userId = memberOut.getUserId();
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setUserId(userId);

        boolean memberOutNeedOwCheck = CmTag.getBoolProperty("memberOutNeedOwCheck");
        if (memberOutNeedOwCheck) {
            record.setStatus(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        } else {
            record.setStatus(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);
        }
        updateByPrimaryKeySelective(record);
        if (!memberOutNeedOwCheck) {
            memberQuitService.quit(userId, MemberConstants.MEMBER_STATUS_TRANSFER);
        }
    }

    @Transactional
    public void check2(int id) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        Integer userId = memberOut.getUserId();

        if (memberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY) // 组织部审核通过
            throw new OpException("状态异常");

        MemberOut record = new MemberOut();
        record.setId(id);
        record.setUserId(userId);
        record.setStatus(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);
        //record.setBranchId(memberOut.getBranchId());
        updateByPrimaryKeySelective(record);

        memberQuitService.quit(userId, MemberConstants.MEMBER_STATUS_TRANSFER);
    }

    //撤销已完成转出审批的记录
    @Transactional
    public void abolish(int id, String remark, byte type) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
        if (memberOut.getStatus() == MemberConstants.MEMBER_OUT_STATUS_ARCHIVE) {
            throw new OpException("已归档记录无须撤销。",
                    MemberConstants.MEMBER_OUT_STATUS_MAP.get(memberOut.getStatus()));
        } else if (memberOut.getStatus() != MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY) {
            throw new OpException("存在未转出审批记录【状态：{0}】，无法撤销",
                    MemberConstants.MEMBER_OUT_STATUS_MAP.get(memberOut.getStatus()));
        }

        Integer userId = memberOut.getUserId();
        MemberOut record = new MemberOut();
        record.setId(id);
        record.setUserId(userId);
        record.setReason("组织部退回");
        record.setStatus(MemberConstants.MEMBER_OUT_STATUS_ABOLISH);
        updateByPrimaryKeySelective(record);

        memberService.reback(userId, memberOut.getPartyId(), memberOut.getBranchId());

        applyApprovalLogService.add(memberOut.getId(),
                memberOut.getPartyId(), memberOut.getBranchId(), userId,
                ShiroHelper.getCurrentUserId(), (type == 1) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY :
                        OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, "撤销已完成的审批", (byte) 1, StringUtils.defaultIfBlank(remark, "组织部退回已完成的审批"));
    }

    // 归档之前已完成转出的记录（如果存在），用于：1、转出时保证当前只有一条记录处于未归档状态 2、再次转入时归档转出记录
    public void archive(int userId){

        MemberOut _record = new MemberOut();
        _record.setStatus(MemberConstants.MEMBER_OUT_STATUS_ARCHIVE);
        MemberOutExample example = new MemberOutExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andStatusEqualTo(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);

        memberOutMapper.updateByExampleSelective(_record, example);
    }


    @Transactional
    public void insertOrUpdateSelective(MemberOut record) {

        if (PartyHelper.hasPartyAuth(ShiroHelper.getCurrentUserId(), record.getPartyId())) {

            boolean memberOutNeedOwCheck = CmTag.getBoolProperty("memberOutNeedOwCheck");
            if (memberOutNeedOwCheck) {
                record.setStatus(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
            } else {
                record.setStatus(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);
            }
        } else {

            record.setStatus(MemberConstants.MEMBER_OUT_STATUS_APPLY);
        }

        int userId = record.getUserId();
        record.setIsBack(false);
        record.setIsModify(false);
        record.setPrintCount(0);
        memberOpService.checkOpAuth(userId);
        if (record.getId() == null) {

            archive(userId);

            memberOutMapper.insertSelective(record);
        } else {
            memberOutMapper.updateByPrimaryKeySelective(record);
        }

        if (record.getStatus() != null && record.getStatus() == MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY) {
            // memberOutNeedOwCheck = false的情况
            memberQuitService.quit(userId, MemberConstants.MEMBER_STATUS_TRANSFER);
        }
    }

    @Transactional
    public void del(Integer id) {

        memberOutMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        MemberOutExample example = new MemberOutExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberOutMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(MemberOut record) {

        Assert.isTrue(record.getUserId() != null, "userId is null");

        int opAuth = memberOpService.findOpAuth(record.getUserId());
        if (opAuth == 2) {
            throw new OpException("已经申请了校内组织关系转接");
        }

        if (record.getPartyId() != null && record.getBranchId() == null) {
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_out", "id", record.getId(), record.getPartyId());
        }

        memberOutMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberOut_check(Integer[] ids, byte type, int loginUserId) {

        for (int id : ids) {
            MemberOut memberOut = null;
            if (type == 1) {
                VerifyAuth<MemberOut> verifyAuth = checkVerityAuth2(id);
                memberOut = verifyAuth.entity;

                check1(memberOut.getId());
            }
            if (type == 2) {
                ShiroHelper.checkPermission(SystemConstants.PERMISSION_PARTYVIEWALL);

                memberOut = memberOutMapper.selectByPrimaryKey(id);
                check2(memberOut.getId());
            }

            int userId = memberOut.getUserId();
            applyApprovalLogService.add(memberOut.getId(),
                    memberOut.getPartyId(), memberOut.getBranchId(), userId,
                    loginUserId, (type == 1) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY :
                            OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, (type == 1)
                            ? "分党委审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberOut_back(Integer[] userIds, byte status, String reason, int loginUserId) {

        boolean odAdmin = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        for (int userId : userIds) {

            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(userId);
            if (memberOut.getStatus() >= MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY) {
                if (!odAdmin) throw new UnauthorizedException();
            }
            if (memberOut.getStatus() >= MemberConstants.MEMBER_OUT_STATUS_BACK) {
                if (!PartyHelper.hasPartyAuth(loginUserId, memberOut.getPartyId())) throw new UnauthorizedException();
            }

            back(memberOut, status, loginUserId, reason);
        }
    }

    // 单条记录退回至某一状态
    private void back(MemberOut memberOut, byte status, int loginUserId, String reason) {

        byte _status = memberOut.getStatus();
        if (_status == MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY) {
            throw new OpException("审核流程已经完成，不可以退回。");
        }
        if (status > _status || status < MemberConstants.MEMBER_OUT_STATUS_BACK) {
            throw new OpException("参数有误。");
        }

        Integer id = memberOut.getId();
        Integer userId = memberOut.getUserId();
        iMemberMapper.memberOut_back(id, status);

        MemberOut record = new MemberOut();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberOut.getPartyId(), memberOut.getBranchId(), userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, MemberConstants.MEMBER_OUT_STATUS_MAP.get(status),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }

    // 批量导入，直接转出
    @Transactional
    public int batchImport(List<MemberOut> records) {

        boolean memberOutNeedOwCheck = CmTag.getBoolProperty("memberOutNeedOwCheck");
        int addCount = 0;
        for (MemberOut record : records) {

            int userId = record.getUserId();
            MemberOut memberOut = getLatest(userId);
            if (memberOut != null
                    && memberOut.getStatus() < MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY) {
                record.setId(memberOut.getId());
            }

            if (memberOutNeedOwCheck) {
                record.setStatus(MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
            } else {
                record.setStatus(MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY);
            }

            if (record.getId() == null) {

                archive(userId);

                memberOutMapper.insertSelective(record);
                addCount++;
            } else {

                memberOutMapper.updateByPrimaryKeySelective(record);
            }

            if (!memberOutNeedOwCheck) {
                // 直接转出
                memberQuitService.quit(userId, MemberConstants.MEMBER_STATUS_TRANSFER);
            }

            applyApprovalLogService.add(record.getId(),
                    record.getPartyId(), record.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, "批量导入",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, null);
        }

        return addCount;
    }

    @Transactional
    public void updateSelfPrint(Integer[] ids, boolean isSelfPrint) {

        int currentUserId = ShiroHelper.getCurrentUserId();
        for (Integer id : ids) {

            MemberOut memberOut = memberOutMapper.selectByPrimaryKey(id);
            // 判断一下权限
            if (!PartyHelper.hasBranchAuth(currentUserId,
                    memberOut.getPartyId(), memberOut.getBranchId())) {
                throw new UnauthorizedException();
            }

            MemberOut record = new MemberOut();
            record.setId(id);
            record.setIsSelfPrint(isSelfPrint);
            memberOutMapper.updateByPrimaryKeySelective(record);

            applyApprovalLogService.add(memberOut.getId(),
                    memberOut.getPartyId(), memberOut.getBranchId(), memberOut.getUserId(),
                    currentUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT, "自动打印",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, isSelfPrint?"开启":"关闭");
        }
    }

    @Transactional
    public void updateSelfPrintApi(MemberOut record) {

        MemberOut memberOut = memberOutMapper.selectByPrimaryKey(record.getId());
        memberOutMapper.updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(memberOut.getId(),
                memberOut.getPartyId(), memberOut.getBranchId(), memberOut.getUserId(),
                null, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT,
                "自动打印",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, "完成");
    }
}
