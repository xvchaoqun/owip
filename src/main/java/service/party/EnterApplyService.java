package service.party;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberApply;
import domain.member.MemberApplyExample;
import domain.member.MemberIn;
import domain.member.MemberInExample;
import domain.member.MemberInflow;
import domain.member.MemberInflowExample;
import domain.member.MemberReturn;
import domain.member.MemberReturnExample;
import domain.party.EnterApply;
import domain.party.EnterApplyExample;
import domain.sys.SysUserView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.member.ApplyApprovalLogService;
import service.member.MemberApplyService;
import service.member.MemberInService;
import service.member.MemberInflowService;
import service.member.MemberOutService;
import service.member.MemberReturnService;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.constants.MemberConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2015/12/4.
 */
@Service
public class EnterApplyService extends BaseMapper{

    @Autowired
    private MemberApplyService memberApplyService;
    @Autowired
    private MemberReturnService memberReturnService;
    @Autowired
    private MemberInService memberInService;
    @Autowired
    private MemberOutService memberOutService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MemberInflowService memberInflowService;
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;

    @Transactional
    public void changeRoleMemberToGuest(int userId) {

        // 更新系统角色  党员->访客
        sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);
        // 撤回原申请
        EnterApply _enterApply = getCurrentApply(userId);
        if (_enterApply != null) {
            applyBack(userId, null, SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT);
        }
    }

    public List<EnterApply> findApplyList(int userId){

        EnterApplyExample example = new EnterApplyExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("create_time desc");
        return enterApplyMapper.selectByExample(example);
    }

    // 查询当前的有效申请
    public EnterApply getCurrentApply(int userId){

        EnterApplyExample example = new EnterApplyExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(SystemConstants.ENTER_APPLY_STATUS_APPLY);

        List<EnterApply> enterApplies = enterApplyMapper.selectByExample(example);
        if(enterApplies.size()>1)
            throw new DBErrorException("重复申请"); // 当前申请状态每个用户只允许一个，且是最新的一条
        if(enterApplies.size()==1) return enterApplies.get(0);

        return null;
    }

    // 申请入党、流入、留学归国申请权限判断
    public void checkMemberApplyAuth(int userId){
        SysUserView sysUser = sysUserService.findById(userId);
        if(sysUser.getType() == SystemConstants.USER_TYPE_JZG
                || sysUser.getType() == SystemConstants.USER_TYPE_BKS
                || sysUser.getType() == SystemConstants.USER_TYPE_YJS){
            // 只允许教职工、学生申请留学归国入党申请
        }else{
            throw new UnauthorizedException("没有权限");
        }

        // 判断是否是党员
        Member member = memberService.get(userId);
        if(member!=null){
            if(member.getStatus()==MemberConstants.MEMBER_STATUS_QUIT){
                throw new OpException("已出党，不能进行转入操作。");
            }else if(member.getStatus()== MemberConstants.MEMBER_STATUS_NORMAL){
                throw new OpException("已经是党员，不需要进行转入操作。");
            }/*else if(member.getStatus()==MemberConstants.MEMBER_STATUS_TRANSFER){
                throw new OpException("已经转出党员，不能进行转入操作。");
            }else if(member.getStatus()==MemberConstants.MEMBER_STATUS_TRANSFER_TEMP){
                //throw new OpException("您已办理组织关系转出");
                return;// 允许挂职干部转出后用原账号转入
            }*/
        }
    }

    // 申请入党
    @Transactional
    @CacheEvict(value = "MemberApply", key = "#record.userId")
    public synchronized void memberApply(MemberApply record) {
        int userId = record.getUserId();

        checkMemberApplyAuth(userId);

        record.setIsRemove(false);

        if(memberApplyMapper.selectByPrimaryKey(userId)==null) {
            memberApplyMapper.insert(record);
        }else
            memberApplyMapper.updateByPrimaryKey(record);

        EnterApply enterApply = new EnterApply();
        enterApply.setUserId(record.getUserId());
        enterApply.setType(SystemConstants.ENTER_APPLY_TYPE_MEMBERAPPLY);
        enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_APPLY);
        enterApply.setCreateTime(new Date());

        enterApplyMapper.insertSelective(enterApply);

        // 确定申请不重复
        getCurrentApply(userId);
    }

    // 留学归国申请
    @Transactional
    public synchronized void memberReturn(MemberReturn record) {

        int userId = record.getUserId();
        checkMemberApplyAuth(userId);

        record.setCreateTime(new Date());
        record.setStatus(MemberConstants.MEMBER_RETURN_STATUS_APPLY);

        MemberReturn memberReturn = memberReturnService.get(userId);
        if(memberReturn==null) {
            record.setIsBack(false);
            memberReturnMapper.insert(record);
        }else {
            record.setIsBack(false);
            memberReturnMapper.updateByPrimaryKey(record);
        }

        EnterApply enterApply = new EnterApply();
        enterApply.setUserId(record.getUserId());
        enterApply.setType(SystemConstants.ENTER_APPLY_TYPE_RETURN);
        enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_APPLY);
        enterApply.setCreateTime(new Date());

        enterApplyMapper.insertSelective(enterApply);

        // 确定申请不重复
        getCurrentApply(userId);
    }

    // 组织关系转入申请
    @Transactional
    public synchronized void memberIn(MemberIn record) { //

        int userId = record.getUserId();

        checkMemberApplyAuth(userId);

        record.setIsModify(false);
        record.setCreateTime(new Date());
        record.setStatus(MemberConstants.MEMBER_IN_STATUS_APPLY);

        MemberIn memberIn = memberInService.get(userId);
        if(memberIn==null) {
            record.setIsBack(false);
            memberInMapper.insert(record);
        }else {
            record.setIsBack(false);
            memberInMapper.updateByPrimaryKey(record);
        }

        // 20160919出现过重复的记录
        // select * from ow_enter_apply where status=0 and user_id=97799;  2条记录
        EnterApply enterApply = new EnterApply();
        enterApply.setUserId(record.getUserId());
        enterApply.setType(SystemConstants.ENTER_APPLY_TYPE_MEMBERIN);
        enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_APPLY);
        enterApply.setCreateTime(new Date());

        enterApplyMapper.insertSelective(enterApply);

        // 确定申请不重复
        getCurrentApply(userId);
    }

    // 流入党员（不入党员库）
    @Transactional
    public synchronized void memberInflow(MemberInflow record) {

        int userId = record.getUserId();

        // 当前只允许流入一次
        if (memberInflowService.idDuplicate(record.getId(), record.getUserId())) {
            throw new OpException("添加重复");
        }
        MemberInflow memberInflow = memberInflowService.get(userId);
        if(memberInflow!=null
                && memberInflow.getInflowStatus() ==MemberConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY
                && (memberInflow.getOutStatus()==null ||
                memberInflow.getOutStatus()!=MemberConstants.MEMBER_INFLOW_OUT_STATUS_PARTY_VERIFY)){
            throw new OpException("已经是流入党员");
        }

        SysUserView sysUser = sysUserService.findById(userId);
        if(sysUser.getType() == SystemConstants.USER_TYPE_JZG)
            record.setType(MemberConstants.MEMBER_TYPE_TEACHER);
        else if(sysUser.getType() == SystemConstants.USER_TYPE_BKS
                || sysUser.getType() == SystemConstants.USER_TYPE_YJS){
            record.setType(MemberConstants.MEMBER_TYPE_STUDENT);
        }else{
            throw new OpException("您不是教工或学生");
        }
        record.setCreateTime(new Date());
        record.setInflowStatus(MemberConstants.MEMBER_INFLOW_STATUS_APPLY);

        if(memberInflow==null) {
            record.setIsBack(false);
            record.setOutIsBack(false);
            memberInflowMapper.insert(record);
        }else {
            record.setIsBack(false);
            record.setOutIsBack(false);
            memberInflowMapper.updateByPrimaryKey(record);
        }

        EnterApply enterApply = new EnterApply();
        enterApply.setUserId(record.getUserId());
        enterApply.setType(SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW);
        enterApply.setStatus(SystemConstants.ENTER_APPLY_STATUS_APPLY);
        enterApply.setCreateTime(new Date());

        enterApplyMapper.insertSelective(enterApply);

        // 确定申请不重复
        getCurrentApply(userId);
    }

    /*
        本人或管理员撤回,
        status:
        SystemConstants.ENTER_APPLY_STATUS_SELF_ABORT 本人
        SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT 管理员
     */
    @Transactional
    public void applyBack(int userId, String remark, byte status) {

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        byte userType=-1;
        if(status==SystemConstants.ENTER_APPLY_STATUS_SELF_ABORT){
            userType = SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF;
        }
        if(status==SystemConstants.ENTER_APPLY_STATUS_ADMIN_ABORT){
            userType = SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN;
        }

        // 状态检查
        EnterApply _enterApply = getCurrentApply(userId);
        if(_enterApply==null)
            throw new DBErrorException("申请不存在。");

        EnterApply enterApply = new EnterApply();
        enterApply.setId(_enterApply.getId());
        enterApply.setStatus(status);
        enterApply.setRemark(remark);
        enterApply.setBackTime(new Date());
        enterApplyMapper.updateByPrimaryKeySelective(enterApply);

        switch (_enterApply.getType()) {
            case SystemConstants.ENTER_APPLY_TYPE_MEMBERAPPLY: {
                // 状态检查
                MemberApply _memberApply = memberApplyMapper.selectByPrimaryKey(userId);
                if(_memberApply==null)
                    throw new DBErrorException("系统错误");
                if(_memberApply.getStage()!=SystemConstants.APPLY_STAGE_INIT &&
                        _memberApply.getStage() != SystemConstants.APPLY_STAGE_DENY){
                    throw new DBErrorException("申请已进入审核阶段，不允许撤回。");
                }

                MemberApply record = new MemberApply();
                //record.setBranchId(_memberApply.getBranchId());  ?? 注释2016-12-16
                record.setStage(SystemConstants.APPLY_STAGE_DENY);
                record.setPassTime(new Date());// 用"通过时间"记录处理时间
                record.setRemark(remark);
                MemberApplyExample example = new MemberApplyExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andStageEqualTo(SystemConstants.APPLY_STAGE_INIT);
                Assert.isTrue(memberApplyService.updateByExampleSelective(userId, record, example) > 0, "db update failed");

                applyApprovalLogService.add(_memberApply.getUserId(),
                        _memberApply.getPartyId(), _memberApply.getBranchId(), _memberApply.getUserId(),
                        shiroUser.getId(), userType,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY,
                        "撤回",
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "撤回入党申请");
                }
                break;
            case SystemConstants.ENTER_APPLY_TYPE_RETURN: {

                // 状态检查
                MemberReturn _memberReturn = memberReturnService.get(userId);
                if(_memberReturn==null)
                    throw new DBErrorException("系统错误");
                if(_memberReturn.getStatus()!=MemberConstants.MEMBER_RETURN_STATUS_APPLY &&
                        _memberReturn.getStatus() != MemberConstants.MEMBER_RETURN_STATUS_DENY){
                    throw new DBErrorException("申请已进入审核阶段，不允许撤回。");
                }

                MemberReturn record = new MemberReturn();
                //record.setBranchId(_memberReturn.getBranchId());
                record.setStatus(MemberConstants.MEMBER_RETURN_STATUS_DENY);
                record.setRemark(remark);
                MemberReturnExample example = new MemberReturnExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andStatusEqualTo(MemberConstants.MEMBER_RETURN_STATUS_APPLY);
                Assert.isTrue(memberReturnService.updateByExampleSelective(record, example) > 0, "db update failed");

                applyApprovalLogService.add(_memberReturn.getId(),
                        _memberReturn.getPartyId(), _memberReturn.getBranchId(), _memberReturn.getUserId(),
                        shiroUser.getId(), userType,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN,
                        "撤回",
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "撤回留学党员归国申请");
                }
                break;

            case SystemConstants.ENTER_APPLY_TYPE_MEMBERIN: {

                // 状态检查
                MemberIn _memberIn = memberInService.get(userId);
                if(_memberIn==null)
                    throw new DBErrorException("系统错误");
                if(_memberIn.getStatus()!=MemberConstants.MEMBER_IN_STATUS_APPLY &&
                        _memberIn.getStatus() != MemberConstants.MEMBER_IN_STATUS_BACK){
                    throw new DBErrorException("申请已进入审核阶段，不允许撤回。");
                }

                MemberIn record = new MemberIn();
                //record.setBranchId(_memberIn.getBranchId());
                if(status==SystemConstants.ENTER_APPLY_STATUS_SELF_ABORT) // 个人撤回
                    record.setStatus(MemberConstants.MEMBER_IN_STATUS_SELF_BACK);
                else
                    record.setStatus(MemberConstants.MEMBER_IN_STATUS_BACK);
                record.setReason(remark);
                MemberInExample example = new MemberInExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andStatusEqualTo(MemberConstants.MEMBER_IN_STATUS_APPLY);
                Assert.isTrue(memberInService.updateByExampleSelective(record, example) > 0, "db update failed");

                applyApprovalLogService.add(_memberIn.getId(),
                        _memberIn.getPartyId(), _memberIn.getBranchId(), _memberIn.getUserId(),
                        shiroUser.getId(), userType,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_IN,
                        "撤回",
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "撤回组织关系转入申请");
            }
            break;
            case SystemConstants.ENTER_APPLY_TYPE_MEMBERINFLOW: {

                // 状态检查
                MemberInflow _memberInflow = memberInflowService.get(userId);
                if(_memberInflow==null)
                    throw new DBErrorException("系统错误");
                if(_memberInflow.getInflowStatus()!=MemberConstants.MEMBER_INFLOW_STATUS_APPLY &&
                        _memberInflow.getInflowStatus() != MemberConstants.MEMBER_INFLOW_STATUS_BACK){
                    throw new DBErrorException("申请已进入审核阶段，不允许撤回。");
                }

                MemberInflow record = new MemberInflow();
                record.setInflowStatus(MemberConstants.MEMBER_INFLOW_STATUS_BACK);
                //record.setBranchId(_memberInflow.getBranchId());
                record.setReason(remark);
                MemberInflowExample example = new MemberInflowExample();
                example.createCriteria().andUserIdEqualTo(userId)
                        .andInflowStatusEqualTo(MemberConstants.MEMBER_INFLOW_STATUS_APPLY);
                Assert.isTrue(memberInflowService.updateByExampleSelective(record, example) > 0, "db update failed");

                applyApprovalLogService.add(_memberInflow.getId(),
                        _memberInflow.getPartyId(), _memberInflow.getBranchId(), _memberInflow.getUserId(),
                        shiroUser.getId(), userType,
                        SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_INFLOW,
                        "撤回",
                        SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                        "撤回党员流入申请");
            }
            break;
            default:
                throw new OpException("参数错误");
        }

    }
}
