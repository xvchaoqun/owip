package service.member;

import domain.member.*;
import domain.party.EnterApply;
import domain.sys.SysUserView;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import service.party.EnterApplyService;
import service.party.PartyService;
import shiro.ShiroHelper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

@Service
public class MemberApplyService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    /*@Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int insertSelective(MemberApply record) {
        return memberApplyMapper.insertSelective(record);
    }*/

    /**
     * 已经是预备党员[状态：正常]的情况下，修改相关的入党申请记录
     *
     * 1、添加预备党员，需要加入入党申请（预备党员阶段）;
     * 2、入党申请已经存在，则修改为预备党员阶段
     * @param userId
     */
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void addOrChangeToGrowApply(int userId){
        Member member = memberService.get(userId);
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if(member!=null && member.getStatus()==SystemConstants.MEMBER_STATUS_NORMAL
                && member.getPoliticalStatus()==SystemConstants.MEMBER_POLITICAL_STATUS_GROW){
            Date now = new Date();
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setType((member.getType()==SystemConstants.MEMBER_TYPE_TEACHER ?
                    SystemConstants.APPLY_TYPE_TEACHER : SystemConstants.APPLY_TYPE_STU));
            record.setPartyId(member.getPartyId());
            record.setBranchId(member.getBranchId());
            record.setApplyTime(member.getApplyTime()==null?now:member.getApplyTime());
            record.setActiveTime(member.getActiveTime());
            record.setCandidateTime(member.getCandidateTime());
            record.setGrowTime(member.getGrowTime());
            record.setGrowStatus(SystemConstants.APPLY_STATUS_UNCHECKED);
            record.setStage(SystemConstants.APPLY_STAGE_GROW);
            if(memberApply==null) { // 还没有入党申请

                record.setRemark("预备党员信息添加后同步");
                record.setFillTime(now);
                record.setCreateTime(now);
                memberApplyMapper.insertSelective(record);
            }else{

                record.setRemark("预备党员信息同步");
                commonMapper.excuteSql("update ow_member set positive_time=null where user_id=" + userId);
                // 考虑更新为直属党支部的情况
                commonMapper.excuteSql("update ow_member_apply set branch_id=null, positive_status=null, positive_time=null where user_id=" + userId);
                memberApplyMapper.updateByPrimaryKeySelective(record);
            }

            applyApprovalLogService.add(userId,
                    member.getPartyId(), member.getBranchId(), userId,
                    currentUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    SystemConstants.APPLY_STAGE_MAP.get(SystemConstants.APPLY_STAGE_GROW),
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "后台添加或修改为预备党员阶段");
        }
    }

    // status=-1代表 isNULL
    public int count(Integer partyId, Integer branchId, Byte  type, Byte stage, Byte status){

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andMemberStatusEqualTo(0);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(stage!=null) {
            criteria.andStageEqualTo(stage);
            if (status != null) {
                switch (stage){
                    case SystemConstants.APPLY_STAGE_ACTIVE:
                        if(status==-1) criteria.andCandidateStatusIsNull();
                        else criteria.andCandidateStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_CANDIDATE:
                        if(status==-1) criteria.andPlanStatusIsNull();
                        else criteria.andPlanStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_PLAN:
                        /*if(status==-1) criteria.andDrawStatusIsNull();
                        else criteria.andDrawStatusEqualTo(status);*/
                        criteria.andDrawStatusIsNull();
                        break;
                    case SystemConstants.APPLY_STAGE_DRAW:
                        if(status==-1) criteria.andGrowStatusIsNull();
                        else criteria.andGrowStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_GROW:
                        if(status==-1) criteria.andPositiveStatusIsNull();
                        else criteria.andPositiveStatusEqualTo(status);
                        break;
                }
            }
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberApplyViewMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberApply next(Byte type, Byte stage,Byte status, MemberApply memberApply){

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andMemberStatusEqualTo(0);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(stage!=null) {
            criteria.andStageEqualTo(stage);
            if (status != null) {
                switch (stage){
                    case SystemConstants.APPLY_STAGE_ACTIVE:
                        if(status==-1) criteria.andCandidateStatusIsNull();
                        else criteria.andCandidateStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_CANDIDATE:
                        if(status==-1) criteria.andPlanStatusIsNull();
                        else criteria.andPlanStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_PLAN:
                        /*if(status==-1) criteria.andDrawStatusIsNull();
                        else criteria.andDrawStatusEqualTo(status);*/
                        criteria.andDrawStatusIsNull();
                        break;
                    case SystemConstants.APPLY_STAGE_DRAW:
                        if(status==-1) criteria.andGrowStatusIsNull();
                        else criteria.andGrowStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_GROW:
                        if(status==-1) criteria.andPositiveStatusIsNull();
                        else criteria.andPositiveStatusEqualTo(status);
                        break;
                }
            }
        }
        if(memberApply!=null)
            criteria.andUserIdNotEqualTo(memberApply.getUserId()).andCreateTimeLessThanOrEqualTo(memberApply.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberApplyView> memberApplies = memberApplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (memberApplies.size()==0)?null:get(memberApplies.get(0).getUserId());
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberApply last(Byte type, Byte stage,Byte status, MemberApply memberApply){

        MemberApplyViewExample example = new MemberApplyViewExample();
        MemberApplyViewExample.Criteria criteria = example.createCriteria().andMemberStatusEqualTo(0);

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        if(stage!=null) {
            criteria.andStageEqualTo(stage);
            if (status != null) {
                switch (stage){
                    case SystemConstants.APPLY_STAGE_ACTIVE:
                        if(status==-1) criteria.andCandidateStatusIsNull();
                        else criteria.andCandidateStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_CANDIDATE:
                        if(status==-1) criteria.andPlanStatusIsNull();
                        else criteria.andPlanStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_PLAN:
                        if(status==-1) criteria.andDrawStatusIsNull();
                        else criteria.andDrawStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_DRAW:
                        if(status==-1) criteria.andGrowStatusIsNull();
                        else criteria.andGrowStatusEqualTo(status);
                        break;
                    case SystemConstants.APPLY_STAGE_GROW:
                        if(status==-1) criteria.andPositiveStatusIsNull();
                        else criteria.andPositiveStatusEqualTo(status);
                        break;
                }
            }
        }

        if(memberApply!=null)
            criteria.andUserIdNotEqualTo(memberApply.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberApply.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberApplyView> memberApplies = memberApplyViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:get(memberApplies.get(0).getUserId());
    }

    @Cacheable(value = "MemberApply", key = "#userId")
    public MemberApply get(int userId) {

        return memberApplyMapper.selectByPrimaryKey(userId);
    }

    // 修改党员所属党组织时，入党申请信息保持同步。如果该党员是预备党员，则相应的要修改入党申请里的预备党员所属党组织，目的是为了预备党员正常转正；
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void updateWhenModifyMember(int userId, Integer partyId, Integer branchId) {

        MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(_memberApply!=null && _memberApply.getStage()>=SystemConstants.APPLY_STAGE_GROW && partyId!=null){

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setPartyId(partyId);
            record.setBranchId(branchId);

            memberApplyMapper.updateByPrimaryKeySelective(record);

            if(partyId!=null && branchId==null){
                // 修改为直属党支部
                Assert.isTrue(partyService.isDirectBranch(partyId), "not direct branch");
                iMemberMapper.updateToDirectBranch("ow_member_apply", "user_id", userId, partyId);
            }

            applyApprovalLogService.add(_memberApply.getUserId(),
                    _memberApply.getPartyId(), _memberApply.getBranchId(), _memberApply.getUserId(),
                    ShiroHelper.getCurrentUserId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    "更新",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "修改党员所属党组织关系，同时修改入党申请的所属组织关系");
        }
    }

    // 修改预备党员为正式党员之后，需要相应的修改入党申请信息（如果存在的话）
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void modifyMemberToPositiveStatus(int userId) {

        Member member = memberMapper.selectByPrimaryKey(userId);
        if(member.getPoliticalStatus()==SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE) {
            MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if (_memberApply != null) {

                MemberApply record = new MemberApply();
                record.setUserId(userId);
                record.setPositiveTime(member.getPositiveTime());
                record.setStage(SystemConstants.APPLY_STAGE_POSITIVE);
                memberApplyMapper.updateByPrimaryKeySelective(record);

                applyApprovalLogService.add(_memberApply.getUserId(),
                        _memberApply.getPartyId(), _memberApply.getBranchId(), _memberApply.getUserId(),
                        ShiroHelper.getCurrentUserId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        "更新",
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "修改党员为正式党员");
            }
        }
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public int updateByExampleSelective(int userId, MemberApply record, MemberApplyExample example) {

        Member member = memberService.get(userId);
        // 修改入党申请人员的所在分党委和支部，如果是在预备或正式党员中修改，则相应的要修改党员信息
        if(record.getPartyId()!=null){

            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if(member!=null && memberApply!=null) {

                if(record.getBranchId()==null){
                    // 修改为直属党支部
                    Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
                    iMemberMapper.updateToDirectBranch("ow_member_apply", "user_id", userId, record.getPartyId());
                }

                if (memberApply.getStage()==SystemConstants.APPLY_STAGE_GROW
                        || memberApply.getStage()==SystemConstants.APPLY_STAGE_POSITIVE) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setPartyId(record.getPartyId());
                    _member.setBranchId(record.getBranchId());
                    memberService.updateByPrimaryKeySelective(_member, "入党申请中修改所属党组织");
                }
            }
        }

        if(record.getApplyTime()!=null){ // 如果修改了提交书面申请书时间，相应的党员信息的也要修改
            if(member!=null) {
                if (member.getApplyTime()==null || !member.getApplyTime().equals(record.getApplyTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setApplyTime(record.getApplyTime());
                    memberService.updateByPrimaryKeySelective(_member, "入党申请中提交或修改提交书面申请书时间");
                }
            }
        }

        if(record.getActiveTime()!=null){ // 如果修改了确定为入党积极分子时间，相应的党员信息的也要修改
            if(member!=null) {
                if (member.getActiveTime()==null || !member.getActiveTime().equals(record.getActiveTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setActiveTime(record.getActiveTime());
                    memberService.updateByPrimaryKeySelective(_member, "入党申请中提交或修改确定为入党积极分子时间");
                }
            }
        }
        if(record.getCandidateTime()!=null){ // 如果修改了确定为发展对象时间，相应的党员信息的也要修改
            if(member!=null) {
                if (member.getCandidateTime()==null || !member.getCandidateTime().equals(record.getCandidateTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setCandidateTime(record.getCandidateTime());
                    memberService.updateByPrimaryKeySelective(_member, "入党申请中提交或修改确定为发展对象时间");
                }
            }
        }

        if(record.getGrowTime()!=null){ // 如果修改了入党时间，相应的党员信息的入党时间也要修改
            if(member!=null) {
                if (member.getGrowTime()==null || !member.getGrowTime().equals(record.getGrowTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setGrowTime(record.getGrowTime());
                    memberService.updateByPrimaryKeySelective(_member, "入党申请中提交或修改入党时间");
                }
            }
        }
        if(record.getPositiveTime()!=null){ // 如果修改了转正时间
            if(member!=null && member.getPoliticalStatus()==SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE) {
                if (member.getPositiveTime()==null || !member.getPositiveTime().equals(record.getPositiveTime())) {
                    Member _member = new Member();
                    _member.setUserId(userId);
                    _member.setPositiveTime(record.getPositiveTime());
                    memberService.updateByPrimaryKeySelective(_member, "入党申请中提交或修改转正时间");
                }
            }
        }

        return memberApplyMapper.updateByExampleSelective(record, example);
    }

    // 分党委审核预备党员信息，跳过下一步的组织部审核
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void applyPositiveCheckByParty(int userId, MemberApply record, MemberApplyExample example){

        if(memberApplyMapper.updateByExampleSelective(record, example)>0){
            memberPositive(userId);
        }
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void denyWhenDeleteMember(int userId){
        MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(_memberApply!=null && _memberApply.getStage()!=SystemConstants.APPLY_STAGE_DENY) {
            // 状态检查
            EnterApply _enterApply = enterApplyService.getCurrentApply(userId);
            if (_enterApply != null) {
                EnterApply enterApply = new EnterApply();
                enterApply.setId(_enterApply.getId());
                enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT);
                enterApply.setRemark("系统打回");
                enterApply.setBackTime(new Date());
                enterApplyMapper.updateByPrimaryKeySelective(enterApply);
            }

            MemberApply record = new MemberApply();
            record.setStage(SystemConstants.APPLY_STAGE_DENY);
            record.setPassTime(new Date());// 用"通过时间"记录处理时间
            record.setRemark("系统打回");
            MemberApplyExample example = new MemberApplyExample();
            example.createCriteria().andUserIdEqualTo(userId);
            Assert.isTrue(memberApplyMapper.updateByExampleSelective(record, example) > 0, "db update failed");

            applyApprovalLogService.add(_memberApply.getUserId(),
                    _memberApply.getPartyId(), _memberApply.getBranchId(), _memberApply.getUserId(),
                    ShiroHelper.getCurrentUserId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                    "撤回",
                    SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                    "删除党员时，同时打回入党申请");
        }
    }

    // 成为正式党员
    @Transactional
    public void memberPositive(int userId){

        MemberApply memberApply = get(userId);
        if(memberApply==null) throw new DBErrorException("系统错误");

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_POSITIVE);
        record.setPositiveStatus(SystemConstants.APPLY_STATUS_OD_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_GROW)
                .andPositiveStatusEqualTo(SystemConstants.APPLY_STATUS_CHECKED);

        // 1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new DBErrorException("系统错误");

        //Member member = memberMapper.selectByPrimaryKey(userId);
        Member _record = new Member();
        _record.setUserId(userId);
        _record.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE);
        _record.setPositiveTime(memberApply.getPositiveTime());
        //_record.setBranchId(member.getBranchId());
        // 2. 更新党员政治面貌
        if(memberService.updateByPrimaryKeySelective(_record) == 0)
            throw new DBErrorException("系统错误");
    }

    // 成为预备党员 (组织部审核之后，直属党支部提交发展时间)
    @Transactional
    public void memberGrowByDirectParty(int userId, Date growTime) {

        SysUserView sysUser = sysUserService.findById(userId);
        MemberApply memberApply = get(userId);
        if(sysUser==null || memberApply==null) throw new DBErrorException("系统错误");

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_GROW);
        record.setGrowStatus(SystemConstants.APPLY_STATUS_CHECKED);
        record.setGrowTime(growTime);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_DRAW)
                .andGrowStatusEqualTo(SystemConstants.APPLY_STATUS_OD_CHECKED);

        //1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new DBErrorException("需要组织部审核之后，才可以发展为预备党员");

        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberApply.getPartyId());
        member.setBranchId(memberApply.getBranchId());
        member.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_GROW); // 预备党员

        Assert.isTrue(memberApply.getType() == SystemConstants.APPLY_TYPE_STU
                || memberApply.getType() == SystemConstants.APPLY_TYPE_TEACHER, "wrong apply type");

        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setGrowTime(growTime);

        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }

    // 成为预备党员 (支部提交之后，分党委审核)
    @Transactional
    public void memberGrowByParty(int userId) {

        SysUserView sysUser = sysUserService.findById(userId);
        MemberApply memberApply = get(userId);
        if(sysUser==null || memberApply==null) throw new DBErrorException("系统错误");

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_GROW);
        record.setGrowStatus(SystemConstants.APPLY_STATUS_CHECKED);

        MemberApplyExample example = new MemberApplyExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStageEqualTo(SystemConstants.APPLY_STAGE_DRAW)
                .andGrowStatusEqualTo(SystemConstants.APPLY_STATUS_UNCHECKED);

        //1. 更新申请状态
        if (updateByExampleSelective(userId, record, example) == 0)
            throw new DBErrorException("需要党支部提交发展时间之后，才可以审核");

        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberApply.getPartyId());
        member.setBranchId(memberApply.getBranchId());
        member.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_GROW); // 预备党员

        Assert.isTrue(memberApply.getType() == SystemConstants.APPLY_TYPE_STU
                || memberApply.getType() == SystemConstants.APPLY_TYPE_TEACHER, "wrong apply type");

        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setGrowTime(memberApply.getGrowTime());

        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public void memberApply_back(int userId, byte stage){

        switch (stage){
            case SystemConstants.APPLY_STAGE_GROW: // 正式党员打回至预备党员
                if(iMemberMapper.memberApplyBackToGrow(userId)==1) {

                    commonMapper.excuteSql("update ow_member set positive_time=null where user_id="+userId);
                    Member record = new Member();
                    record.setUserId(userId);
                    record.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_GROW);
                    memberService.updateByPrimaryKeySelective(record, "在入党申请中，由正式党员打回至预备党员");
                }
                break;
            case SystemConstants.APPLY_STAGE_PLAN:  // 当前状态为领取志愿书之前(_stage<= SystemConstants.APPLY_STAGE_DRAW)
                iMemberMapper.memberApplyBackToPlan(userId);
                break;
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                iMemberMapper.memberApplyBackToCandidate(userId);
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
                iMemberMapper.memberApplyBackToActive(userId);
                break;
            case SystemConstants.APPLY_STAGE_INIT:
                iMemberMapper.memberApplyBackToInit(userId);
                break;
        }
    }


}
