package service.member;

import controller.global.OpException;
import domain.cadre.Cadre;
import domain.cadre.CadreParty;
import domain.member.Member;
import domain.member.MemberApply;
import domain.member.MemberQuit;
import domain.member.MemberQuitExample;
import domain.pmd.PmdMemberPayViewExample;
import domain.sys.SysUserView;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.pmd.PmdMemberPayViewMapper;
import service.LoginUserService;
import service.cadre.CadrePartyService;
import service.party.MemberService;
import service.party.PartyService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CadreConstants;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.helper.PartyHelper;
import sys.tags.CmTag;

import java.util.List;

@Service
public class MemberQuitService extends MemberBaseMapper {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadrePartyService cadrePartyService;

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
        }else if(type==4){ //完成审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return (int) memberQuitMapper.countByExample(example);
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

    // 不通过（退回上一级）
    @Transactional
    public void deny(int[] userIds, byte type, String reason, int loginUserId){

        for (int userId : userIds) {
            byte status = -1;
            MemberQuit memberQuit = null;
            if (type == 1) {// 支部退回
                VerifyAuth<MemberQuit> verifyAuth = checkVerityAuth(userId);
                memberQuit = verifyAuth.entity;
                status = MemberConstants.MEMBER_QUIT_STATUS_BACK;
            }
            if (type == 2) {// 分党委退回
                VerifyAuth<MemberQuit> verifyAuth = checkVerityAuth2(userId);
                memberQuit = verifyAuth.entity;
                status = MemberConstants.MEMBER_QUIT_STATUS_APPLY;
            }
            if (type == 3) { // 组织部退回
                ShiroHelper.checkPermission(RoleConstants.PERMISSION_PARTYVIEWALL);
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

        quit(userId, MemberConstants.MEMBER_STATUS_QUIT);
    }
    
    @Transactional
    public void insertSelective(MemberQuit record){

        memberQuitMapper.insertSelective(record);
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
                ShiroHelper.checkPermission(RoleConstants.PERMISSION_PARTYVIEWALL);
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
                            ? "基层党组织审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberQuit_back(Integer[] userIds, byte status, String reason, int loginUserId){

        boolean odAdmin = ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL);
        for (int userId : userIds) {

            MemberQuit memberQuit = memberQuitMapper.selectByPrimaryKey(userId);

            if(memberQuit.getStatus() >= MemberConstants.MEMBER_QUIT_STATUS_PARTY_VERIFY){
                if(!odAdmin) throw new UnauthorizedException();
            }
            if(memberQuit.getStatus() >= MemberConstants.MEMBER_QUIT_STATUS_BRANCH_VERIFY){
                if(!PartyHelper.hasPartyAuth(loginUserId, memberQuit.getPartyId()))
                    throw new UnauthorizedException();
            }
            if(memberQuit.getStatus() >= MemberConstants.MEMBER_QUIT_STATUS_BACK){
                if(!PartyHelper.hasBranchAuth(loginUserId, memberQuit.getPartyId(), memberQuit.getBranchId()))
                    throw new UnauthorizedException();
            }

            back(memberQuit, status, loginUserId, reason);
        }
    }

    // 单条记录退回至某一状态
    private  void back(MemberQuit memberQuit, byte status, int loginUserId, String reason){

        byte _status = memberQuit.getStatus();
        if(_status==MemberConstants.MEMBER_QUIT_STATUS_OW_VERIFY){
            throw new OpException("党员已经出党，不可以退回。");
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
     *               MemberConstants.MEMBER_STATUS_OUT
     */
    @Transactional
    public void quit(int userId, byte status) {

        /*commonMapper.excuteSql("update ow_member set party_id=null, branch_id=null, status="
                + status +" where user_id=" + userId);*/
        commonMapper.excuteSql("update ow_member set status=" + status +" where user_id=" + userId);

        Member member = memberService.get(userId);
        // 存在未缴纳党费时，不允许转出或减员
        checkPmdStatus(userId);

        Cadre cadre = CmTag.getCadre(userId);
        if(cadre!=null){
            // 干部转出时，自动加入“特殊党员干部库”中
            CadreParty cadreParty = cadrePartyService.getOwOrFirstDp(userId, CadreConstants.CADRE_PARTY_TYPE_OW);
            if(cadreParty==null){
                cadreParty = new CadreParty();
                cadreParty.setUserId(userId);
                cadreParty.setType(CadreConstants.CADRE_PARTY_TYPE_OW);
                cadreParty.setGrowTime(member.getGrowTime());
            }

            cadreParty.setRemark("组织关系已转出");

            cadrePartyService.addOrUpdateCadreParty(cadreParty);
        }

        // 将党员发展申请（预备党员阶段）转入已移除申请
        MemberApply memberApply = memberApplyMapper.selectByPrimaryKey(userId);
        if(memberApply!=null && memberApply.getStage()==OwConstants.OW_APPLY_STAGE_GROW){

            MemberApply record = new MemberApply();
            record.setUserId(userId);
            record.setIsRemove(true);
            memberApplyMapper.updateByPrimaryKeySelective(record);

            applyApprovalLogService.add(userId,
                    memberApply.getPartyId(), memberApply.getBranchId(), userId,
                    ShiroHelper.getCurrentUserId(),  OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY, "移除",
                    OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED, "组织关系变更");
        }

        // 更新系统角色  党员->访客
        sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);
    }

    // 检验是否存在未缴纳的党费记录
    public void checkPmdStatus(int userId){

        PmdMemberPayViewMapper pmdMemberPayViewMapper = CmTag.getBean(PmdMemberPayViewMapper.class);
        if(pmdMemberPayViewMapper!=null){

            PmdMemberPayViewExample example = new PmdMemberPayViewExample();
            example.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andHasPayEqualTo(false);

            long count = pmdMemberPayViewMapper.countByExample(example);
            if(count > 0){
                SysUserView uv = CmTag.getUserById(userId);
                throw new OpException("{0}存在{1}条未缴纳党费记录，请缴纳后再办理其他业务。", uv.getRealname(), count);
            }
        }
    }

     // 撤销已减员记录
    @Transactional
    public void memberQuit_abolish(Integer[] userIds){

        for (int userId : userIds) {

            Member record = new Member();
            record.setUserId(userId);
            record.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
            //record.setBranchId(member.getBranchId());
            memberService.updateByPrimaryKeySelective(record);

            // 删除减员记录
            memberQuitMapper.deleteByPrimaryKey(userId);

            // 更新系统角色  访客->党员
            sysUserService.changeRole(userId, RoleConstants.ROLE_GUEST, RoleConstants.ROLE_MEMBER);

            memberService.addModify(userId, "撤销减员");
        }
    }
}
