package service.member;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberQuit;
import domain.member.MemberQuitExample;
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

import java.util.List;

@Service
public class MemberQuitService extends MemberBaseMapper {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PartyService partyService;

    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    @Autowired
    private LoginUserService loginUserService;
    private VerifyAuth<MemberQuit> checkVerityAuth(int id){
        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberQuit, memberQuit.getPartyId(), memberQuit.getBranchId());
    }

    private VerifyAuth<MemberQuit> checkVerityAuth2(int id){
        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberQuit, memberQuit.getPartyId());
    }

    public int count(Integer partyId, Integer branchId, byte type){

        MemberQuitExample example = new MemberQuitExample();
        MemberQuitExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY);
        } else if(type==3){ //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberQuitMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberQuit next(byte type, MemberQuit memberQuit){

        MemberQuitExample example = new MemberQuitExample();
        MemberQuitExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY);
        } else if(type==3){ //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }

        if(memberQuit!=null)
            criteria.andUserIdNotEqualTo(memberQuit.getUserId()).andCreateTimeLessThanOrEqualTo(memberQuit.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberQuit> memberApplies = memberQuitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberQuit last(byte type, MemberQuit memberQuit){

        MemberQuitExample example = new MemberQuitExample();
        MemberQuitExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY);
        } else if(type==3){ //组织部审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }

        if(memberQuit!=null)
            criteria.andUserIdNotEqualTo(memberQuit.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberQuit.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberQuit> memberApplies = memberQuitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 不通过（打回上一级）
    @Transactional
    public void deny(int[] userIds, byte type, String reason, int loginUserId){

        for (int userId : userIds) {
            byte status = -1;
            MemberQuit memberQuit = null;
            if (type == 1) {// 支部打回
                VerifyAuth<MemberQuit> verifyAuth = checkVerityAuth(userId);
                memberQuit = verifyAuth.entity;
                status = MemberConstants.MEMBER_QUIT_STATUS_BACK;
            }
            if (type == 2) {// 分党委打回
                VerifyAuth<MemberQuit> verifyAuth = checkVerityAuth2(userId);
                memberQuit = verifyAuth.entity;
                status = MemberConstants.MEMBER_QUIT_STATUS_APPLY;
            }
            if (type == 3) { // 组织部打回
                SecurityUtils.getSubject().checkPermission(SystemConstants.PERMISSION_PARTYVIEWALL);
                memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
                status = MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY;
            }

            back(memberQuit, status, loginUserId, reason);
        }
    }

    // 支部审核通过
    @Transactional
    public void check1(int userId){

        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
        if(memberQuit.getStatus()!= MemberConstants.MEMBER_QUIT_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberQuit record = new MemberQuit();
        record.setUserId(memberQuit.getUserId());
        record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);
    }

    // 直属党支部审核通过
    @Transactional
    public void checkByDirectBranch(int userId){

        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
        if(memberQuit.getStatus()!= MemberConstants.MEMBER_QUIT_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberQuit record = new MemberQuit();
        record.setUserId(memberQuit.getUserId());
        record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void check2(int userId){

        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
        if(memberQuit.getStatus()!= MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY)
            throw new OpException("状态异常");
        MemberQuit record = new MemberQuit();
        record.setUserId(memberQuit.getUserId());
        record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);
    }
    // 组织部审核通过
    @Transactional
    public void check3(int userId){

        MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
        if(memberQuit.getStatus()!= MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY)
            throw new OpException("状态异常");

        MemberQuit record = new MemberQuit();
        record.setUserId(memberQuit.getUserId());
        record.setStatus(MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY);
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        quit(userId, MemberConstants.MEMBER_STATUS_TRANSFER);
    }
    
    @Transactional
    public void insertSelective(MemberQuit record){

        memberQuitMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer userId){

        memberQuitMapper.deleteByPrimaryKey(userId);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberQuit record){
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_quit", "user_id", record.getUserId(), record.getPartyId());
        }

        return memberQuitMapper.updateByPrimaryKeySelective(record);
    }


    @Transactional
    public void memberQuit_check(Integer[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberQuit memberQuit = null;
            if(type==1) {
                VerifyAuth<MemberQuit> verifyAuth = checkVerityAuth(id);
                memberQuit = verifyAuth.entity;

                if (verifyAuth.isDirectBranch) {
                    checkByDirectBranch(memberQuit.getUserId());
                } else {
                    check1(memberQuit.getUserId());
                }
            }
            if(type==2) {
                VerifyAuth<MemberQuit> verifyAuth = checkVerityAuth2(id);
                memberQuit = verifyAuth.entity;

                check2(memberQuit.getUserId());
            }
            if(type==3) {
                SecurityUtils.getSubject().checkPermission(SystemConstants.PERMISSION_PARTYVIEWALL);
                memberQuit = memberQuitMapper.selectByPrimaryKey(id);
                check3(memberQuit.getUserId());
            }

            int userId = memberQuit.getUserId();
            applyApprovalLogService.add(memberQuit.getUserId(),
                    memberQuit.getPartyId(), memberQuit.getBranchId(), userId,
                    loginUserId, (type == 1)? OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_BRANCH:
                            (type == 2)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_PARTY:OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, (type == 1)
                            ? "支部审核" : (type == 2)
                            ? "分党委审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberQuit_back(Integer[] userIds, byte status, String reason, int loginUserId){

        boolean odAdmin = ShiroHelper.isPermitted(SystemConstants.PERMISSION_PARTYVIEWALL);
        for (int userId : userIds) {

            MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);
            Boolean presentPartyAdmin = PartyHelper.isPresentPartyAdmin(loginUserId, memberQuit.getPartyId());
            Boolean presentBranchAdmin = PartyHelper.isPresentBranchAdmin(loginUserId, memberQuit.getPartyId(), memberQuit.getBranchId());

            if(memberQuit.getStatus() >= MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY){
                if(!odAdmin) throw new UnauthorizedException();
            }
            if(memberQuit.getStatus() >= MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY){
                if(!odAdmin && !presentPartyAdmin) throw new UnauthorizedException();
            }
            if(memberQuit.getStatus() >= MemberConstants.MEMBER_QUIT_STATUS_BACK){
                if(!odAdmin && !presentPartyAdmin && !presentBranchAdmin) throw new UnauthorizedException();
            }

            back(memberQuit, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberQuit memberQuit, byte status, int loginUserId, String reason){

        byte _status = memberQuit.getStatus();
        if(_status==MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY){
            throw new OpException("党员已经出党，不可以打回。");
        }
        if(status>_status || status<MemberConstants.MEMBER_QUIT_STATUS_BACK ){
            throw new OpException("参数有误。");
        }

        Integer userId = memberQuit.getUserId();
        iMemberMapper.memberQuit_back(userId, status);

        MemberQuit record = new MemberQuit();
        record.setUserId(memberQuit.getUserId());
        record.setRemark(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(userId,
                memberQuit.getPartyId(), memberQuit.getBranchId(), userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_QUIT, MemberConstants.MEMBER_QUIT_STATUS_MAP.get(status),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }

    /**
     * 党员出党
     *
     * @param userId
     * @param status MemberConstants.MEMBER_STATUS_QUIT
     *               MemberConstants.MEMBER_STATUS_RETIRE
     */
    @Transactional
    public void quit(int userId, byte status) {

        //Member member = memberMapper.selectByPrimaryKey(userId);
        Member record = new Member();
        record.setUserId(userId);
        record.setStatus(status);
        //record.setBranchId(member.getBranchId());
        memberService.updateByPrimaryKeySelective(record);

        // 更新系统角色  党员->访客
        enterApplyService.changeRoleMemberToGuest(userId);
    }
}
