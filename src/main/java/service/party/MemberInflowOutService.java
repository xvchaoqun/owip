package service.party;

import domain.MemberInflow;
import domain.MemberInflowExample;
import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.util.List;

@Service
public class MemberInflowOutService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired
    private MemberInflowService memberInflowService;
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
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andOutIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andOutIsBackEqualTo(true);
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
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andOutIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andOutIsBackEqualTo(true);
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
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andOutStatusEqualTo(SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(cls==1){// 支部审核（新申请)
            criteria.andOutIsBackNotEqualTo(true);
        }else if(cls==4){// 支部审核（返回修改)
            criteria.andOutIsBackEqualTo(true);
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

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberInflow memberInflow = get(userId);
        if(memberInflow.getOutStatus()!= SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setOutStatus(SystemConstants.MEMBER_INFLOW_OUT_STATUS_SELF_BACK);
        record.setIsBack(false);
        memberInflowService.updateByPrimaryKeySelective(record);

        // 清空是否打回状态
        updateMapper.resetIsBack("ow_member_inflow", "out_is_back", false, "id", memberInflow.getId());

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        applyApprovalLogService.add(memberInflow.getId(),
                memberInflow.getPartyId(), memberInflow.getBranchId(), memberInflow.getUserId(), shiroUser.getId(),
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT,
                "撤回",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回流入党员转出申请");
    }

    // reset=true 重置状态，  reset=false，只是更新信息
    @Transactional
    public MemberInflow out(int userId, String outUnit, Integer outLocation, String _outTime, boolean reset){
        // 1 要求 党员已经流入
        MemberInflow memberInflow = memberInflowService.get(userId);
        if(memberInflow==null || memberInflow.getInflowStatus()!=SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY){
            throw new RuntimeException("状态异常");
        }

        //
        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setOutUnit(outUnit);
        record.setOutLocation(outLocation);
        record.setUserId(userId);
        if(StringUtils.isNotBlank(_outTime)){
            record.setOutTime(DateUtils.parseDate(_outTime, DateUtils.YYYY_MM_DD));
        }

        // 2 要求 提出申请并未完成审批的记录
        if(memberInflow.getOutStatus()!=null&&memberInflow.getOutStatus()==SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY){
            throw new RuntimeException("状态异常");
        }

        if(reset) {
            // 清空是否打回状态
            updateMapper.resetIsBack("ow_member_inflow", "out_is_back", false, "id", memberInflow.getId() );

            record.setOutStatus(SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY);
            record.setOutIsBack(false);
        }
        memberInflowMapper.updateByPrimaryKeySelective(record);

        return memberInflow;
    }

    // 党支部审核通过
    @Transactional
    public void checkMember(int userId){

        MemberInflow memberInflow = get(userId);
        if(memberInflow.getOutStatus()!= SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setOutStatus(SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY);
        memberInflowService.updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void removeInflowMember(int userId, boolean isDirect){

        SysUser sysUser = sysUserService.findById(userId);
        MemberInflow memberInflow = get(userId);

        if(isDirect && memberInflow.getOutStatus()!= SystemConstants.MEMBER_INFLOW_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberInflow.getOutStatus()!= SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setOutStatus(SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY);

        memberInflowService.updateByPrimaryKeySelective(record);

        // 更新系统角色  流入党员->访客
        sysUserService.changeRole(sysUser.getId(), SystemConstants.ROLE_INFLOWMEMBER,
                SystemConstants.ROLE_GUEST, sysUser.getUsername());
    }


    @Transactional
    public void memberInflowOut_check(int[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberInflow memberInflow = null;
            if (type == 1) {

                VerifyAuth<MemberInflow> verifyAuth = checkVerityAuth(id);
                memberInflow = verifyAuth.entity;

                if (verifyAuth.isDirectBranch && verifyAuth.isPartyAdmin) { // 直属党支部管理员，不需要通过党支部审核
                    removeInflowMember(memberInflow.getUserId(), true);
                } else {
                    checkMember(memberInflow.getUserId());
                }
            }

            if (type == 2) {
                VerifyAuth<MemberInflow> verifyAuth = checkVerityAuth2(id);
                memberInflow = verifyAuth.entity;
                removeInflowMember(memberInflow.getUserId(), false);
            }

            int userId = memberInflow.getUserId();

            applyApprovalLogService.add(memberInflow.getId(),
                    memberInflow.getPartyId(), memberInflow.getBranchId(), userId, loginUserId,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT, (type == 1) ? "支部审核" : "分党委审核", (byte) 1, null);

        }
    }

    @Transactional
    public void memberInflowOut_back(int[] userIds, byte status, String reason, int loginUserId){

        for (int userId : userIds) {

            MemberInflow memberInflow = memberInflowMapper.selectByPrimaryKey(userId);
            Boolean presentBranchAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberInflow.getBranchId());
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberInflow.getPartyId());

            if(status >= SystemConstants.MEMBER_INFLOW_OUT_STATUS_BRANCH_VERIFY){
                if(!presentPartyAdmin) throw new UnauthorizedException();
            }
            if(status >= SystemConstants.MEMBER_INFLOW_OUT_STATUS_BACK){
                if(!presentPartyAdmin && !presentBranchAdmin) throw new UnauthorizedException();
            }

            back(memberInflow, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberInflow memberInflow, byte status, int loginUserId, String reason){

        byte _status = memberInflow.getOutStatus();
        if(_status==SystemConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status>_status || status<SystemConstants.MEMBER_INFLOW_OUT_STATUS_BACK ){
            throw new RuntimeException("参数有误。");
        }

        Integer id = memberInflow.getId();
        Integer userId = memberInflow.getUserId();
        updateMapper.memberInflowOut_back(id, status);

        MemberInflow record = new MemberInflow();
        record.setId(id);
        record.setUserId(userId);
        record.setOutReason(reason);
        record.setOutIsBack(true);
        memberInflowService.updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberInflow.getPartyId(), memberInflow.getBranchId(), userId, loginUserId,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW_OUT, SystemConstants.MEMBER_INFLOW_OUT_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
