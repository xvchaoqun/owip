package service.party;

import domain.Member;
import domain.MemberOutflow;
import domain.MemberOutflowExample;
import domain.SysUser;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MemberOutflowService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    @Autowired
    protected MemberService memberService;
    @Autowired
    protected BranchService branchService;
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    private MemberOpService memberOpService;

    private VerifyAuth<MemberOutflow> checkVerityAuth(int id){
        MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberOutflow, memberOutflow.getPartyId(), memberOutflow.getBranchId());
    }

    private VerifyAuth<MemberOutflow> checkVerityAuth2(int id){
        MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberOutflow, memberOutflow.getPartyId());
    }
    
    public int count(Integer partyId, Integer branchId, byte type, byte cls){

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
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

        return memberOutflowMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberOutflow next(MemberOutflow memberOutflow, byte type, byte cls){

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if(memberOutflow!=null)
            criteria.andUserIdNotEqualTo(memberOutflow.getUserId()).andCreateTimeLessThanOrEqualTo(memberOutflow.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberOutflow> memberApplies = memberOutflowMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberOutflow last(MemberOutflow memberOutflow, byte type, byte cls){

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(cls==1){// 支部审核（新申请)
            criteria.andIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andIsBackEqualTo(true);
        }

        if(memberOutflow!=null)
            criteria.andUserIdNotEqualTo(memberOutflow.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberOutflow.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberOutflow> memberApplies = memberOutflowMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    public boolean idDuplicate(Integer id, Integer userId){
        
        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberOutflowMapper.countByExample(example) > 0;
    }

    public MemberOutflow get(int userId) {

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberOutflow> memberOutflows = memberOutflowMapper.selectByExample(example);
        if(memberOutflows.size()>0) return memberOutflows.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY) // 只有申请状态可以撤回
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK);
        record.setUserId(userId);
        record.setIsBack(false);
        //record.setBranchId(memberOutflow.getBranchId());
        updateByPrimaryKeySelective(record);

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        applyApprovalLogService.add(memberOutflow.getId(),
                memberOutflow.getPartyId(), memberOutflow.getBranchId(), memberOutflow.getUserId(),
                shiroUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW,
                "撤回",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回流出党员申请");
    }

    // 不通过
    @Transactional
    public void deny(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()== SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY) // 终审操作前，可以不通过（即打回）
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_BACK);
        //record.setBranchId(memberOutflow.getBranchId());
        record.setUserId(userId);
        updateByPrimaryKeySelective(record);
    }

    // 党支部审核通过
    @Transactional
    public void check1(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        record.setUserId(userId);
        //record.setBranchId(memberOutflow.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void check2(int userId, boolean isDirect){

        MemberOutflow memberOutflow = get(userId);

        if(isDirect && memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY);
        //record.setBranchId(memberOutflow.getBranchId());
        record.setUserId(userId);
        updateByPrimaryKeySelective(record);
    }
    @Transactional
    public int add(MemberOutflow record){

        memberOpService.checkOpAuth(record.getUserId());

        record.setHasPapers((record.getHasPapers() == null) ? false : record.getHasPapers());

        int userId = record.getUserId();

        Member member = memberService.get(userId);
        record.setPartyId(member.getPartyId());
        record.setBranchId(member.getBranchId());

        if(record.getPartyId()!=null) {
            record.setPartyName(partyService.findAll().get(record.getPartyId()).getName());
        }
        if(record.getBranchId()!=null) {
            record.setBranchName(branchService.findAll().get(record.getBranchId()).getName());
        }

        SysUser sysUser = sysUserService.findById(userId);
        if(sysUser.getType()==SystemConstants.USER_TYPE_JZG)
            record.setType(SystemConstants.MEMBER_TYPE_TEACHER);
        else
            record.setType(SystemConstants.MEMBER_TYPE_STUDENT);

        record.setCreateTime(new Date());

        return memberOutflowMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberOutflowMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberOutflowExample example = new MemberOutflowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberOutflowMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberOutflow record){

        memberOpService.checkOpAuth(record.getUserId());

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_outflow", "id", record.getId(), record.getPartyId());
        }

        return memberOutflowMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberOutflow_check(int[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(id);
            int userId = memberOutflow.getUserId();
            if(type==1) {
                VerifyAuth<MemberOutflow> verifyAuth = checkVerityAuth(id);
                memberOutflow = verifyAuth.entity;
                if (verifyAuth.isDirectBranch && verifyAuth.isPartyAdmin) { // 直属党支部管理员，不需要通过党支部审核
                    check2(userId, true);
                } else {
                    check1(userId);
                }
            }
            if(type==2) {
                VerifyAuth<MemberOutflow> verifyAuth = checkVerityAuth2(id);
                memberOutflow = verifyAuth.entity;
                check2(userId, false);
            }

            applyApprovalLogService.add(memberOutflow.getId(),
                    memberOutflow.getPartyId(), memberOutflow.getBranchId(), userId,
                    loginUserId, (type == 1)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_BRANCH:
                            SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_PARTY,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW, (type==1)?"支部审核":"分党委审核", (byte)1, null);
        }
    }

    @Transactional
    public void memberOutflow_back(int[] userIds, byte status, String reason, int loginUserId){

        for (int userId : userIds) {

            MemberOutflow memberOutflow = memberOutflowMapper.selectByPrimaryKey(userId);
            Boolean presentBranchAdmin = CmTag.isPresentBranchAdmin(loginUserId, memberOutflow.getBranchId());
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberOutflow.getPartyId());

            if(status >= SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY){ // 支部审核通过后，只有分党委才有打回的权限
                if(!presentPartyAdmin) throw new UnauthorizedException();
            }
            if(status >= SystemConstants.MEMBER_OUTFLOW_STATUS_BACK){ // 在支部审核完成之前，党支部或分党委都可以打回
                if(!presentPartyAdmin && !presentBranchAdmin) throw new UnauthorizedException();
            }

            back(memberOutflow, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberOutflow memberOutflow, byte status, int loginUserId, String reason){

        byte _status = memberOutflow.getStatus();
        if(_status==SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status>_status || status<SystemConstants.MEMBER_OUTFLOW_STATUS_BACK ){
            throw new RuntimeException("参数有误。");
        }

        Integer userId = memberOutflow.getUserId();
        Integer id = memberOutflow.getId();
        updateMapper.memberOutflow_back(id, status);

        MemberOutflow record = new MemberOutflow();
        record.setId(id);
        record.setUserId(userId);
        record.setRemark(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberOutflow.getPartyId(), memberOutflow.getBranchId(), userId,
                loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW, SystemConstants.MEMBER_OUTFLOW_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
