package service.member;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberStay;
import domain.member.MemberStayExample;
import domain.party.Branch;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.party.BranchService;
import service.party.PartyService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberStayService extends MemberBaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private MemberOpService memberOpService;

    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    private VerifyAuth<MemberStay> checkVerityAuth(int id) {
        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberStay, memberStay.getPartyId(), memberStay.getBranchId());
    }

    private VerifyAuth<MemberStay> checkVerityAuth2(int id) {
        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberStay, memberStay.getPartyId());
    }

    public long count(Integer partyId, Integer branchId, byte checkType, Byte type, Byte cls) {

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (checkType == 1) { //支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_APPLY);
        } else if (checkType == 2) { //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY);
        } else if (checkType == 3) { //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        } else {
            throw new OpException("审核类型错误");
        }

        /* 没有返回修改标签
        if (cls != null) {
            if (cls == 1 || cls == 2 || cls == 3) {// 支部审核（新申请）/ 分党委审核（新申请) / 组织部审核（新申请)
                criteria.andIsBackNotEqualTo(true);
            } else if (cls == 11 || cls == 21 || cls == 31) {// 支部审核（返回修改）/ 分党委审核（返回修改) / 组织部审核（返回修改)
                criteria.andIsBackEqualTo(true);
            }
        }*/

        if (partyId != null) criteria.andPartyIdEqualTo(partyId);
        if (branchId != null) criteria.andBranchIdEqualTo(branchId);

        return memberStayMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberStay next(byte type, byte checkType, MemberStay memberStay, byte cls) {

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (checkType == 1) { //支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_APPLY);
        } else if (checkType == 2) { //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY);
        } else if (checkType == 3) { //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        } else {
            throw new OpException("审核类型错误");
        }

        if (cls == 1 || cls == 2 || cls == 3) {// 支部审核（新申请）/ 分党委审核（新申请) / 组织部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        } else if (cls == 11 || cls == 21 || cls == 31) {// 支部审核（返回修改）/ 分党委审核（返回修改) / 组织部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if (memberStay != null) {
            // 确认类型
            type = memberStay.getType();
            criteria.andUserIdNotEqualTo(memberStay.getUserId()).andCreateTimeLessThanOrEqualTo(memberStay.getCreateTime());
        }
        criteria.andTypeEqualTo(type);

        example.setOrderByClause("create_time desc");

        List<MemberStay> records = memberStayMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (records.size() == 0) ? null : records.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberStay last(byte type, byte checkType, MemberStay memberStay, byte cls) {

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if (checkType == 1) { //支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_APPLY);
        } else if (checkType == 2) { //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY);
        } else if (checkType == 3) { //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        } else {
            throw new OpException("审核类型错误");
        }

        if (cls == 1 || cls == 2 || cls == 3) {// 支部审核（新申请）/ 分党委审核（新申请) / 组织部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        } else if (cls == 11 || cls == 21 || cls == 31) {// 支部审核（返回修改）/ 分党委审核（返回修改) / 组织部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if (memberStay != null) {
            // 确认类型
            type = memberStay.getType();
            criteria.andUserIdNotEqualTo(memberStay.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberStay.getCreateTime());
        }
        criteria.andTypeEqualTo(type);
        example.setOrderByClause("create_time asc");

        List<MemberStay> memberApplies = memberStayMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size() == 0) ? null : memberApplies.get(0);
    }

    public boolean idDuplicate(Integer id, Integer userId) {

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria =
                example.createCriteria().andUserIdEqualTo(userId)
                        .andStatusNotEqualTo(MemberConstants.MEMBER_STAY_STATUS_ARCHIVE);

        if (id != null) criteria.andIdNotEqualTo(id);

        return memberStayMapper.countByExample(example) > 0;
    }

    // 读取记录（未归档记录仅有一条）
    public MemberStay get(int userId, byte type) {

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(type)
        .andStatusNotEqualTo(MemberConstants.MEMBER_STAY_STATUS_ARCHIVE);

        List<MemberStay> memberStays = memberStayMapper.selectByExample(example);
        if (memberStays.size() > 0) return memberStays.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int id) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        if (memberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_APPLY)
            throw new OpException("状态异常");
        if(memberStay.getUserId().intValue()!=ShiroHelper.getCurrentUserId()){
            throw new UnauthorizedException();
        }
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_SELF_BACK);
        record.setUserId(memberStay.getUserId());
        //record.setBranchId(memberStay.getBranchId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(memberStay.getId(),
                memberStay.getPartyId(), memberStay.getBranchId(), memberStay.getUserId(),
                ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY,
                "撤回",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回暂留申请");
    }


    // 支部审核通过
    @Transactional
    public void check1(int id) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        if (memberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setUserId(memberStay.getUserId());
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY);
        record.setIsBack(false);
        record.setBranchCheckTime(new Date());
        updateByPrimaryKeySelective(record);
    }

    // 直属党支部审核通过
    @Transactional
    public void checkByDirectBranch(int id) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        if (memberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setUserId(memberStay.getUserId());
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        record.setIsBack(false);
        record.setPartyCheckTime(new Date());
        updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void check2(int id, Integer branchId, Integer orgBranchAdminId, String orgBranchAdminPhone) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        if (memberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY)
            throw new OpException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setUserId(memberStay.getUserId());
        record.setToBranchId(branchId);
        record.setOrgBranchAdminId(orgBranchAdminId);
        record.setOrgBranchAdminPhone(orgBranchAdminPhone);
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        record.setIsBack(false);
        record.setPartyCheckTime(new Date());
        updateByPrimaryKeySelective(record);

        // 支部转移
        if (branchId!=null && memberStay.getBranchId() != branchId) {
            Map<Integer, Branch> branchMap = branchService.findAll();
            Branch branch = branchMap.get(branchId);
            if (branch == null || branch.getPartyId().intValue() != memberStay.getPartyId()) {
                throw new OpException("转移支部不存在");
            }
            Member member = new Member();
            member.setUserId(memberStay.getUserId());
            member.setBranchId(branchId);
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }

    // 组织部审核通过
    @Transactional
    public void check3(int id) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);

        if (memberStay.getStatus() != MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
            throw new OpException("状态异常");

        int userId = memberStay.getUserId();
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY);
        record.setUserId(userId);
        record.setIsBack(false);
        record.setCheckTime(new Date());
        updateByPrimaryKeySelective(record);

        if(CmTag.getBoolProperty("hasMemberStayStatus")) {
            // 转移至暂留党员库
            Member member = new Member();
            member.setUserId(userId);
            member.setStatus(MemberConstants.MEMBER_STATUS_STAY);
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }

    @Transactional
    public int insertSelective(MemberStay record) {

        int userId = record.getUserId();
        byte type = record.getType();
        MemberStay memberStay = get(userId, type);
        if (memberStay != null) { // 提交过申请
            if(memberStay.getStatus()==MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY){
                // 归档之前已完成的记录（如果存在的话），保证当前只有一条记录处于未归档状态
                MemberStay _record = new MemberStay();
                _record.setStatus(MemberConstants.MEMBER_STAY_STATUS_ARCHIVE);
                MemberStayExample example = new MemberStayExample();
                example.createCriteria()
                        .andUserIdEqualTo(userId)
                        .andTypeEqualTo(type)
                        .andStatusEqualTo(MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY);
                memberStayMapper.updateByExampleSelective(_record, example);
            }else {
                record.setId(memberStay.getId());// 保留原ID
                memberStayMapper.deleteByPrimaryKey(memberStay.getId());
            }
        }

        Integer year = DateUtils.getYear(new Date());
        String maxCode = StringUtils.trimToNull(iMemberMapper.getMemberStayMaxCode(year));
        int nextCode = (maxCode == null ? 0 : Integer.valueOf(maxCode.substring(4))) + 1;
        if (nextCode > 9999) {
            throw new OpException("系统错误code=9999，请联系管理员");
        }
        record.setCode(year + "" + String.format("%04d", nextCode));

        record.setIsBack(false);
        memberOpService.checkOpAuth(userId);

        record.setPrintCount(0);
        record.setCreateTime(new Date());
        return memberStayMapper.insertSelective(record);
    }

    /*@Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberStayMapper.deleteByExample(example);
    }*/

    @Transactional
    public int updateByPrimaryKeySelective(MemberStay record) {

        memberOpService.checkOpAuth(record.getUserId());

        if (record.getPartyId() != null && record.getBranchId() == null) {
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_stay", "id", record.getId(), record.getPartyId());
        }

        record.setType(null);
        record.setCreateTime(new Date());
        return memberStayMapper.updateByPrimaryKeySelective(record);
    }

    // 修改暂留党支部信息
    @Transactional
    public void trasferAu(MemberStay record) {

        checkVerityAuth2(record.getId());

        memberStayMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberStay_check(Integer[] ids, byte type,
                                 Integer branchId, Integer orgBranchAdminId, String orgBranchAdminPhone,
                                 int loginUserId) {

        for (int id : ids) {
            MemberStay memberStay = null;
            if (type == 1) {
                VerifyAuth<MemberStay> verifyAuth = checkVerityAuth(id);
                memberStay = verifyAuth.entity;
                boolean isDirectBranch = verifyAuth.isDirectBranch;

                if (isDirectBranch) {
                    checkByDirectBranch(memberStay.getId());
                } else {
                    check1(memberStay.getId());
                }
            } else if (type == 2) {
                VerifyAuth<MemberStay> verifyAuth = checkVerityAuth2(id);
                memberStay = verifyAuth.entity;

                check2(memberStay.getId(), branchId, orgBranchAdminId, orgBranchAdminPhone);
            } else if (type == 3) {
                ShiroHelper.checkPermission(RoleConstants.PERMISSION_PARTYVIEWALL);
                memberStay = memberStayMapper.selectByPrimaryKey(id);
                check3(memberStay.getId());
            } else {
                throw new UnauthorizedException();
            }

            int userId = memberStay.getUserId();
            applyApprovalLogService.add(memberStay.getId(),
                    memberStay.getPartyId(), memberStay.getBranchId(), userId,
                    loginUserId, (type == 1) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH :
                            (type == 2) ? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY : OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, (type == 1)
                            ? "支部审核" : (type == 2)
                            ? "基层党组织审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberStay_back(Integer[] ids, byte status, String reason, int loginUserId) {

        boolean odAdmin = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
        for (int id : ids) {

            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
            if (memberStay.getStatus() >= MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY) {
                if (!odAdmin) throw new UnauthorizedException();
            }
            if (memberStay.getStatus() >= MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY) {
                if (!PartyHelper.hasPartyAuth(loginUserId, memberStay.getPartyId()))
                    throw new UnauthorizedException();
            }
            if (memberStay.getStatus() >= MemberConstants.MEMBER_STAY_STATUS_BACK) {
                if (!PartyHelper.hasBranchAuth(loginUserId, memberStay.getPartyId(), memberStay.getBranchId()))
                    throw new UnauthorizedException();
            }

            if (partyService.isDirectBranch(memberStay.getPartyId())
                    && status == MemberConstants.MEMBER_STAY_STATUS_BRANCH_VERIFY) { // 分党委退回至直属党支部，需要直接退回支部审核模块
                status = MemberConstants.MEMBER_STAY_STATUS_APPLY;
            }

            byte _status = memberStay.getStatus();
            if (_status == MemberConstants.MEMBER_STAY_STATUS_OW_VERIFY
                    ||_status == MemberConstants.MEMBER_STAY_STATUS_ARCHIVE) {
                throw new OpException("审核流程已经完成，不可以退回。");
            }
            if (status > _status || status < MemberConstants.MEMBER_STAY_STATUS_BACK) {
                throw new OpException("参数有误。");
            }

            Integer userId = memberStay.getUserId();
            iMemberMapper.memberStay_back(id, status);

            MemberStay record = new MemberStay();
            record.setId(id);
            record.setUserId(userId);
            record.setReason(reason);
            record.setIsBack(true);
            updateByPrimaryKeySelective(record);

            applyApprovalLogService.add(id,
                    memberStay.getPartyId(), memberStay.getBranchId(), userId,
                    loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, MemberConstants.MEMBER_STAY_STATUS_MAP.get(status),
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK, reason);
        }
    }

    @Transactional
    public void abolish(Integer id, String remark) {

        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        int userId = memberStay.getUserId();
        MemberStay record = new MemberStay();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(remark);
        record.setStatus(MemberConstants.MEMBER_STAY_STATUS_BACK);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        // 撤销操作 转移至党员库
        Member member = new Member();
        member.setUserId(userId);
        member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
        memberMapper.updateByPrimaryKeySelective(member);

        applyApprovalLogService.add(memberStay.getId(),
                memberStay.getPartyId(), memberStay.getBranchId(), memberStay.getUserId(),
                ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY,
                "撤销",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                remark);
    }
}
