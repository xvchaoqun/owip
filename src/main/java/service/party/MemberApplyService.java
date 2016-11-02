package service.party;

import domain.member.Member;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.sys.SysUser;
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
import service.helper.ShiroSecurityHelper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

@Service
public class MemberApplyService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    /*@Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int insertSelective(MemberApply record) {
        return memberApplyMapper.insertSelective(record);
    }*/

    // 添加预备党员，需要加入入党申请（预备党员阶段）
    public void addGrowApply(int userId){
        Member member = memberService.get(userId);
        if(member!=null && member.getStatus()==SystemConstants.MEMBER_STATUS_NORMAL
                && member.getPoliticalStatus()==SystemConstants.MEMBER_POLITICAL_STATUS_GROW){
            Date now = new Date();
            MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
            if(memberApply==null) { // 还没有入党申请
                MemberApply record = new MemberApply();
                record.setUserId(userId);
                record.setType((member.getType()==SystemConstants.MEMBER_TYPE_TEACHER ?
                        SystemConstants.APPLY_TYPE_TECHER : SystemConstants.APPLY_TYPE_STU));
                record.setPartyId(member.getPartyId());
                record.setBranchId(member.getBranchId());
                record.setApplyTime(now);
                record.setGrowTime(member.getGrowTime());
                record.setRemark("后台添加");
                record.setFillTime(now);
                record.setCreateTime(now);
                record.setStage(SystemConstants.APPLY_STAGE_GROW);

                memberApplyMapper.insertSelective(record);

                Integer currentUserId = ShiroSecurityHelper.getCurrentUserId();
                applyApprovalLogService.add(userId,
                        member.getPartyId(), member.getBranchId(), userId,
                        currentUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        SystemConstants.APPLY_STAGE_MAP.get(SystemConstants.APPLY_STAGE_GROW),
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "后台添加预备党员");
            }
        }
    }

    // status=-1代表 isNULL
    public int count(Integer partyId, Integer branchId, Byte  type, Byte stage, Byte status){

        MemberApplyExample example = new MemberApplyExample();
        MemberApplyExample.Criteria criteria = example.createCriteria();

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

        return memberApplyMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberApply next(Byte type, Byte stage,Byte status, MemberApply memberApply){

        MemberApplyExample example = new MemberApplyExample();
        MemberApplyExample.Criteria criteria = example.createCriteria();

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

        List<MemberApply> memberApplies = memberApplyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberApply last(Byte type, Byte stage,Byte status, MemberApply memberApply){

        MemberApplyExample example = new MemberApplyExample();
        MemberApplyExample.Criteria criteria = example.createCriteria();

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

        List<MemberApply> memberApplies = memberApplyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    @Cacheable(value = "MemberApply", key = "#userId")
    public MemberApply get(int userId) {

        return memberApplyMapper.selectByPrimaryKey(userId);
    }

    /*@Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public int updateByPrimaryKeySelective(MemberApply record) {

        return memberApplyMapper.updateByPrimaryKeySelective(record);
    }*/

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#userId")
    public int updateByExampleSelective(int userId, MemberApply record, MemberApplyExample example) {

        if(record.getGrowTime()!=null){ // 如果修改了入党时间，相应的党员信息的入党时间也要修改
            Member member = memberService.get(userId);
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
            Member member = memberService.get(userId);
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

        Member member = memberMapper.selectByPrimaryKey(userId);
        Member _record = new Member();
        _record.setUserId(userId);
        _record.setPoliticalStatus(SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE);
        _record.setPositiveTime(memberApply.getPositiveTime());
        //_record.setBranchId(member.getBranchId());
        // 2. 更新党员政治面貌
        if(memberService.updateByPrimaryKeySelective(_record) == 0)
            throw new DBErrorException("系统错误");
    }

    // 成为预备党员 (组织部审核之后，分党委提交发展时间)
    @Transactional
    public void memberGrow(int userId, Date growTime) {

        SysUserView sysUser = sysUserService.findById(userId);
        MemberApply memberApply = get(userId);
        if(sysUser==null || memberApply==null) throw new DBErrorException("系统错误");

        MemberApply record = new MemberApply();
        record.setStage(SystemConstants.APPLY_STAGE_GROW);
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
                || memberApply.getType() == SystemConstants.APPLY_TYPE_TECHER);

        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_GROW); // 本校发展党员
        member.setApplyTime(memberApply.getApplyTime());
        member.setActiveTime(memberApply.getActiveTime());
        member.setCandidateTime(memberApply.getCandidateTime());
        member.setGrowTime(memberApply.getGrowTime());
        //member.setPositiveTime(memberApply.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }

    @Transactional
    @CacheEvict(value = "MemberApply", key = "#memberApply.userId")
    public void memberApply_back(MemberApply memberApply, byte stage){

        int userId = memberApply.getUserId();
        switch (stage){
            case SystemConstants.APPLY_STAGE_PLAN:  // 当前状态为领取志愿书之前(_stage<= SystemConstants.APPLY_STAGE_DRAW)
                updateMapper.memberApplyBackToPlan(userId);
                break;
            case SystemConstants.APPLY_STAGE_CANDIDATE:
                updateMapper.memberApplyBackToCandidate(userId);
                break;
            case SystemConstants.APPLY_STAGE_ACTIVE:
                updateMapper.memberApplyBackToActive(userId);
                break;
            case SystemConstants.APPLY_STAGE_INIT:
                updateMapper.memberApplyBackToInit(userId);
                break;
        }
    }


}
