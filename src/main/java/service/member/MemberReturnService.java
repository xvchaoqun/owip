package service.member;

import domain.member.Member;
import domain.member.MemberReturn;
import domain.member.MemberReturnExample;
import domain.party.EnterApply;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import service.party.EnterApplyService;
import service.party.PartyService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MemberReturnService extends BaseMapper {

    @Autowired
    private  MemberService memberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private EnterApplyService enterApplyService;

    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    private VerifyAuth<MemberReturn> checkVerityAuth(int id){
        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberReturn, memberReturn.getPartyId(), memberReturn.getBranchId());
    }

    private VerifyAuth<MemberReturn> checkVerityAuth2(int id){
        MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberReturn, memberReturn.getPartyId());
    }
    
    public int count(Integer partyId, Integer branchId, byte type){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberReturnMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberReturn next(byte type, MemberReturn memberReturn){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberReturn!=null)
            criteria.andUserIdNotEqualTo(memberReturn.getUserId()).andCreateTimeLessThanOrEqualTo(memberReturn.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberReturn> memberApplies = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberReturn last(byte type, MemberReturn memberReturn){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberReturn!=null)
            criteria.andUserIdNotEqualTo(memberReturn.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberReturn.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberReturn> memberApplies = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }
    public boolean idDuplicate(Integer id, Integer userId){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberReturnMapper.countByExample(example) > 0;
    }

    public MemberReturn get(int userId) {

        MemberReturnExample example = new MemberReturnExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberReturn> memberReturns = memberReturnMapper.selectByExample(example);
        if(memberReturns.size()>0) return memberReturns.get(0);

        return null;
    }

    // 党支部审核通过
    @Transactional
    public void checkMember(int userId){

        MemberReturn memberReturn = get(userId);
        if(memberReturn.getStatus()!= SystemConstants.MEMBER_RETURN_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberReturn record = new MemberReturn();
        record.setId(memberReturn.getId());
        record.setStatus(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        //record.setBranchId(memberReturn.getBranchId());
        updateByPrimaryKeySelective(record);
    }
    // 分党委审核通过
    @Transactional
    public void addMember(int userId, byte politicalStatus, boolean isDirect){

        Member _member = memberService.get(userId);
        if(_member!=null){
            throw new RuntimeException("已经是党员");
        }

        MemberReturn memberReturn = get(userId);
        if(isDirect && memberReturn.getStatus()!= SystemConstants.MEMBER_RETURN_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberReturn.getStatus()!= SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberReturn record = new MemberReturn();
        record.setId(memberReturn.getId());
        record.setStatus(SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY);
        //record.setBranchId(memberReturn.getBranchId());
        updateByPrimaryKeySelective(record);

        // 添加党员操作
        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberReturn.getPartyId());
        member.setBranchId(memberReturn.getBranchId());
        member.setPoliticalStatus(politicalStatus);

        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_RETURNED); //  恢复党员
        member.setApplyTime(memberReturn.getApplyTime());
        member.setActiveTime(memberReturn.getActiveTime());
        member.setCandidateTime(memberReturn.getCandidateTime());

        // 对于归国人员是预备党员的，入党时间就是 “提交恢复组织生活申请时间” 向后推一年
        if(politicalStatus == SystemConstants.MEMBER_POLITICAL_STATUS_GROW){
            Date returnApplyTime = memberReturn.getReturnApplyTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(returnApplyTime);
            cal.add(Calendar.YEAR, 1);
            member.setGrowTime(cal.getTime());
        }else {
            member.setGrowTime(memberReturn.getGrowTime());
        }
        member.setPositiveTime(memberReturn.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }

    @Transactional
    public int insertSelective(MemberReturn record){

        return memberReturnMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberReturnMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberReturnExample example = new MemberReturnExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberReturnMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberReturn record){
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_return", "id", record.getId(), record.getPartyId());
        }

        return memberReturnMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByExampleSelective(MemberReturn record, MemberReturnExample example) {

        return memberReturnMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void memberReturn_check(Integer[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberReturn memberReturn = null;
            if(type==1) {
                VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth(id);
                boolean isDirectBranch = verifyAuth.isDirectBranch;
                boolean isPartyAdmin = verifyAuth.isPartyAdmin;
                memberReturn = verifyAuth.entity;

                if (isDirectBranch && isPartyAdmin) { // 直属党支部管理员，不需要通过党支部审核
                    addMember(memberReturn.getUserId(), memberReturn.getPoliticalStatus(), true);
                } else {
                    checkMember(memberReturn.getUserId());
                }
            }

            if(type==2) {
                VerifyAuth<MemberReturn> verifyAuth = checkVerityAuth2(id);
                memberReturn = verifyAuth.entity;

                addMember(memberReturn.getUserId(), memberReturn.getPoliticalStatus(), false);
            }

            int userId = memberReturn.getUserId();
            applyApprovalLogService.add(memberReturn.getId(),
                    memberReturn.getPartyId(), memberReturn.getBranchId(), userId,
                    loginUserId, (type == 1)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_BRANCH:
                            SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, (type == 1)
                            ? "党支部审核" : "分党委审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberReturn_back(Integer[] userIds, byte status, String reason, int loginUserId){

        for (int userId : userIds) {

            MemberReturn memberReturn = memberReturnMapper.selectByPrimaryKey(userId);
            Boolean presentBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, memberReturn.getPartyId(), memberReturn.getBranchId());
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberReturn.getPartyId());

            if(memberReturn.getStatus() >= SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY){
                if(!presentPartyAdmin) throw new UnauthorizedException();
            }
            if(memberReturn.getStatus() >= SystemConstants.MEMBER_RETURN_STATUS_DENY){
                if(!presentPartyAdmin && !presentBranchAdmin) throw new UnauthorizedException();
            }

            back(memberReturn, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberReturn memberReturn, byte status, int loginUserId, String reason){

        byte _status = memberReturn.getStatus();
        if(_status==SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status>_status || status<SystemConstants.MEMBER_RETURN_STATUS_DENY ){
            throw new RuntimeException("参数有误。");
        }

        Integer id = memberReturn.getId();
        Integer userId = memberReturn.getUserId();

        if(status==SystemConstants.MEMBER_RETURN_STATUS_DENY ) { // 后台打回申请，需要重置入口提交状态
            // 状态检查
            EnterApply _enterApply = enterApplyService.getCurrentApply(userId);
            if (_enterApply == null)
                throw new DBErrorException("系统错误");

            EnterApply enterApply = new EnterApply();
            enterApply.setId(_enterApply.getId());
            enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT);
            enterApply.setRemark(reason);
            enterApply.setBackTime(new Date());
            enterApplyMapper.updateByPrimaryKeySelective(enterApply);
        }

        iMemberMapper.memberReturn_back(id, status);

        MemberReturn record = new MemberReturn();
        record.setId(id);
        record.setUserId(userId);
        record.setRemark(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberReturn.getPartyId(), memberReturn.getBranchId(), userId,
                loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN, SystemConstants.MEMBER_RETURN_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
