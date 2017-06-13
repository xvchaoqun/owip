package service.member;

import domain.party.EnterApply;
import domain.member.MemberInflow;
import domain.member.MemberInflowExample;
import domain.sys.SysUserView;
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
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MemberInflowService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    private VerifyAuth<MemberInflow> checkVerityAuth(int id){
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberInflow, memberInflow.getPartyId(), memberInflow.getBranchId());
    }

    private VerifyAuth<MemberInflow> checkVerityAuth2(int id){
        MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberInflow, memberInflow.getPartyId());
    }

    public int count(Integer partyId, Integer branchId, byte type, byte cls){

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberInflowMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberInflow next(MemberInflow memberInflow,byte type, byte cls){

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if(memberInflow!=null)
            criteria.andUserIdNotEqualTo(memberInflow.getUserId()).andCreateTimeLessThanOrEqualTo(memberInflow.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberInflow> memberApplies = memberInflowMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberInflow last(MemberInflow memberInflow,byte type, byte cls){

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andInflowStatusEqualTo(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }
        if(memberInflow!=null)
            criteria.andUserIdNotEqualTo(memberInflow.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberInflow.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberInflow> memberApplies = memberInflowMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    public boolean idDuplicate(Integer id, Integer userId){

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);
        return memberInflowMapper.countByExample(example) > 0;
    }

    public MemberInflow get(int userId) {

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberInflow> memberReturns = memberInflowMapper.selectByExample(example);
        if(memberReturns.size()>0) return memberReturns.get(0);

        return null;
    }

    // 党支部审核通过
    @Transactional
    public void checkMember(int userId){

        MemberInflow memberInflow = get(userId);
        if(memberInflow.getInflowStatus()!= SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setInflowStatus(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);
        //record.setBranchId(memberInflow.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void addInflowMember(int userId, boolean isDirect){

        SysUserView sysUser = sysUserService.findById(userId);
        MemberInflow memberInflow = get(userId);

        if(isDirect && memberInflow.getInflowStatus()!= SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberInflow.getInflowStatus()!= SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setInflowStatus(SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY);
        //record.setBranchId(memberInflow.getBranchId());
        updateByPrimaryKeySelective(record);

        EnterApply _enterApply = enterApplyService.getCurrentApply(userId);
        if(_enterApply!=null && _enterApply.getType()==SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW) {
            EnterApply enterApply = new EnterApply();
            enterApply.setId(_enterApply.getId());
            enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_PASS);
            enterApplyMapper.updateByPrimaryKeySelective(enterApply);
        }

        // 更新系统角色  访客->流入党员
        sysUserService.changeRole(sysUser.getId(), SystemConstants.ROLE_GUEST,
                SystemConstants.ROLE_INFLOWMEMBER, sysUser.getUsername(), sysUser.getCode());
    }

    @Transactional
    public int insertSelective(MemberInflow record){

        return memberInflowMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberInflowMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberInflowExample example = new MemberInflowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberInflowMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberInflow record){
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_inflow", "id", record.getId(), record.getPartyId());
        }
        return memberInflowMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByExampleSelective(MemberInflow record, MemberInflowExample example){
        /*if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_inflow", "id", record.getId(), record.getPartyId());
        }*/

        return memberInflowMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void memberInflow_check(Integer[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberInflow memberInflow = null;
            if (type == 1) {

                VerifyAuth<MemberInflow> verifyAuth = checkVerityAuth(id);
                memberInflow = verifyAuth.entity;

                if (verifyAuth.isDirectBranch && verifyAuth.isPartyAdmin) { // 直属党支部管理员，不需要通过党支部审核
                    addInflowMember(memberInflow.getUserId(), true);
                } else {
                    checkMember(memberInflow.getUserId());
                }
            }

            if (type == 2) {
                VerifyAuth<MemberInflow> verifyAuth = checkVerityAuth2(id);
                memberInflow = verifyAuth.entity;
                addInflowMember(memberInflow.getUserId(), false);
            }

            int userId = memberInflow.getUserId();

            applyApprovalLogService.add(memberInflow.getId(),
                    memberInflow.getPartyId(), memberInflow.getBranchId(), userId,
                    loginUserId, (type == 1)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_BRANCH:
                            SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, (type == 1) ? "支部审核" : "分党委审核", (byte) 1, null);

        }
    }

    @Transactional
    public void memberInflow_back(Integer[] userIds, byte status, String reason, int loginUserId){

        for (int userId : userIds) {

            MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(userId);
            Boolean presentBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, memberInflow.getPartyId(), memberInflow.getBranchId());
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberInflow.getPartyId());

            if(memberInflow.getInflowStatus() >= SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY){
                if(!presentPartyAdmin) throw new UnauthorizedException();
            }
            if(memberInflow.getInflowStatus() >= SystemConstants.MEMBER_INFLOW_STATUS_BACK){
                if(!presentPartyAdmin && !presentBranchAdmin) throw new UnauthorizedException();
            }

            back(memberInflow, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberInflow memberInflow, byte status, int loginUserId, String reason){

        byte _status = memberInflow.getInflowStatus();
        if(_status==SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status>_status || status<SystemConstants.MEMBER_INFLOW_STATUS_BACK ){
            throw new RuntimeException("参数有误。");
        }
        Integer id = memberInflow.getId();
        Integer userId = memberInflow.getUserId();

        if(status==SystemConstants.MEMBER_INFLOW_STATUS_BACK ) { // 后台打回申请，需要重置入口提交状态
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

        iMemberMapper.memberInflow_back(id, status);

        MemberInflow record = new MemberInflow();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberInflow.getPartyId(), memberInflow.getBranchId(), userId,
                loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW, SystemConstants.MEMBER_INFLOW_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
