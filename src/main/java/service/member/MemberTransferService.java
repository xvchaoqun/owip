package service.member;

import controller.global.OpException;
import domain.member.Member;
import domain.member.MemberTransfer;
import domain.member.MemberTransferExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.LoginUserService;
import service.party.MemberService;
import service.party.PartyMemberService;
import service.party.PartyService;
import service.sys.SysApprovalLogService;
import shiro.ShiroHelper;
import sys.constants.MemberConstants;
import sys.constants.OwConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.helper.PartyHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static sys.constants.SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED;

@Service
public class MemberTransferService extends MemberBaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private MemberOpService memberOpService;
    @Autowired
    private PartyMemberService partyMemberService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    
    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    private VerifyAuth<MemberTransfer> checkVerityAuth(int id){
        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberTransfer, memberTransfer.getPartyId());
    }

    private VerifyAuth<MemberTransfer> checkVerityAuth2(int id){
        MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberTransfer, memberTransfer.getToPartyId());
    }

    public int count(Integer partyId, Integer branchId, byte type){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //转出分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
        } else if(type==2){ //转入分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return (int) memberTransferMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberTransfer next(byte type, MemberTransfer memberTransfer){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //转出分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
        } else if(type==2){ //转入分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }

        if(memberTransfer!=null)
            criteria.andUserIdNotEqualTo(memberTransfer.getUserId()).andApplyTimeLessThanOrEqualTo(memberTransfer.getApplyTime());
        example.setOrderByClause("apply_time desc");

        List<MemberTransfer> memberApplies = memberTransferMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberTransfer last(byte type, MemberTransfer memberTransfer){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //转出分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
        } else if(type==2){ //转入分党委审核
            criteria.andStatusEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }else{
            throw new OpException("审核类型错误");
        }

        if(memberTransfer!=null)
            criteria.andUserIdNotEqualTo(memberTransfer.getUserId()).andApplyTimeGreaterThanOrEqualTo(memberTransfer.getApplyTime());
        example.setOrderByClause("apply_time asc");

        List<MemberTransfer> memberApplies = memberTransferMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }
    
    public boolean idDuplicate(Integer id, Integer userId){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId)
                        .andStatusNotEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberTransferMapper.countByExample(example) > 0;
    }

    public MemberTransfer get(int userId) {

        MemberTransferExample example = new MemberTransferExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andStatusNotEqualTo(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
        if(memberTransfers.size()>0) return memberTransfers.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= MemberConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new OpException("审批已经开始，不可以撤回");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setUserId(userId);
        record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_SELF_BACK);
        //record.setBranchId(memberTransfer.getBranchId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(memberTransfer.getId(),
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), memberTransfer.getUserId(),
                ShiroHelper.getCurrentUserId(), OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                "撤回",
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回校内组织关系互转申请");
    }

   /* // 不通过
    @Transactional
    public void deny(int userId, String reason){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= MemberConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new OpException("状态异常");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_BACK);
        record.setReason(reason);
        record.setUserId(userId);
        //record.setBranchId(memberTransfer.getBranchId());
        updateByPrimaryKeySelective(record);
    }*/

    // 当前所在分党委审核通过
    @Transactional
    public void check1(int userId){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= MemberConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new OpException("转出分党委已经审核，请不要重复审核。");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setUserId(userId);

        boolean samePartyTransfer = memberTransfer.getPartyId().intValue()==memberTransfer.getToPartyId();
        if(samePartyTransfer){
            record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        }else {
            record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }
        //record.setBranchId(memberTransfer.getBranchId());
        updateByPrimaryKeySelective(record);

        if(samePartyTransfer){
            doTransfer(userId, memberTransfer.getToPartyId(), memberTransfer.getToBranchId());
        }
    }

    // 转入分党委审核通过
    @Transactional
    public void check2(int userId){

        MemberTransfer memberTransfer = get(userId);

        /*if(isDirect && memberTransfer.getStatus()!= MemberConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new OpException("状态异常");*/
        if(memberTransfer.getStatus()!= MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY)
            throw new OpException("转出分党委审核通过之后，才可以进行转入分党委审核。");

        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setUserId(userId);
        record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        //record.setBranchId(memberTransfer.getBranchId());
        updateByPrimaryKeySelective(record);

        doTransfer(userId, memberTransfer.getToPartyId(), memberTransfer.getToBranchId());
    }

    private void doTransfer(int userId, Integer toPartyId, Integer toBranchId){

        // 更改该党员的所在党组织
        Member member = memberMapper.selectByPrimaryKey(userId);
        member.setPartyId(toPartyId);
        member.setBranchId(toBranchId);
        member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
        memberMapper.updateByPrimaryKeySelective(member);

        if(toPartyId!=null && toBranchId==null){

            // 修改为直属党支部
            if(!partyService.isDirectBranch(toPartyId)){
                throw new OpException("转入党委有误，非直属党支部。");
            }

            iMemberMapper.updateToDirectBranch("ow_member", "user_id", userId, toPartyId);
        }
    }

    @Transactional
    public int insertSelective(MemberTransfer record){

        memberOpService.checkOpAuth(record.getUserId());

        return memberTransferMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberTransferMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberTransferExample example = new MemberTransferExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberTransferMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberTransfer record){

        Assert.isTrue(record.getUserId()!=null, "userId is null");

        int opAuth = memberOpService.findOpAuth(record.getUserId());
        if(opAuth==1){
            throw new OpException("已经申请了组织关系转出");
        }

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()), "not direct branch");
            iMemberMapper.updateToDirectBranch("ow_member_transfer", "id", record.getId(), record.getPartyId());
        }

        if(record.getToPartyId()!=null && record.getToBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getToPartyId()), "not direct branch");
            commonMapper.excuteSql("update ow_member_transfer set to_party_id="+record.getToPartyId()
                    +" , to_branch_id=null where id=" + record.getId());
        }

        return memberTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberTransfer_check(Integer[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberTransfer memberTransfer = null;
            if(type==1) { // 1:转出分党委审核 2：转入分党委审核
                VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth(id);
                memberTransfer = verifyAuth.entity;

                if(memberTransfer.getStatus()>=MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY){
                    throw new OpException("转出分党委已审批，请勿重复操作（{0}）。", memberTransfer.getUser().getRealname());
                }

                check1(memberTransfer.getUserId());
            }else if(type==2) {
                VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth2(id);
                memberTransfer = verifyAuth.entity;

                check2(memberTransfer.getUserId());
            }
            int userId = memberTransfer.getUserId();
            applyApprovalLogService.add(memberTransfer.getId(),
                    memberTransfer.getPartyId(), memberTransfer.getBranchId(), userId,
                    loginUserId,  (type == 1)?OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY:
                            OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY,
                    OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, (type == 1)
                            ? "转出分党委审核" : "转入分党委审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberTransfer_back(Integer[] userIds, byte status, String reason, int loginUserId){

        boolean notAdmin = (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL));

        for (int userId : userIds) {

            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(userId);

            if(notAdmin) {
                if (memberTransfer.getStatus() >= MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY) {
                    if (!PartyHelper.hasPartyAuth(loginUserId, memberTransfer.getToPartyId()))
                        throw new UnauthorizedException();
                }
                if (memberTransfer.getStatus() >= MemberConstants.MEMBER_TRANSFER_STATUS_BACK) {
                    if (!PartyHelper.hasPartyAuth(loginUserId, memberTransfer.getToPartyId())
                            && !PartyHelper.hasPartyAuth(loginUserId, memberTransfer.getPartyId()))
                        throw new UnauthorizedException();
                }
            }

            back(memberTransfer, status, loginUserId, reason);
        }
    }

    // 单条记录退回至某一状态
    private  void back(MemberTransfer memberTransfer, byte status, int loginUserId, String reason){

        byte _status = memberTransfer.getStatus();
        if(_status==MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY){
            throw new OpException("审核流程已经完成，不可以退回。");
        }
        if(status > _status || status<MemberConstants.MEMBER_TRANSFER_STATUS_BACK ){
            throw new OpException("参数有误。");
        }

        Integer id = memberTransfer.getId();
        Integer userId = memberTransfer.getUserId();
        iMemberMapper.memberTransfer_back(id, status);

        MemberTransfer record = new MemberTransfer();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), userId,
                loginUserId, OwConstants.OW_APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, MemberConstants.MEMBER_TRANSFER_STATUS_MAP.get(status),
                OwConstants.OW_APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }

    /*
    * 导入时，如果通过，将党员库党员状态改为1，未通过不修改；转出分党委审批通过的数据，状态为1
    * */
    @Transactional
    public int batchImport(List<MemberTransfer> records) {

        int addCount = 0;
        for (MemberTransfer record : records) {

            Integer userId = record.getUserId();
            record.setIsBack(false);
            MemberTransferExample example = new MemberTransferExample();
            example.createCriteria().andUserIdEqualTo(userId).andStatusLessThan(MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
            List<MemberTransfer> memberTransferList = memberTransferMapper.selectByExample(example);
            if (memberTransferList == null || memberTransferList.size() == 0){

                record.setApplyTime(new Date());
                authAndStatus(record);
                memberTransferMapper.insert(record);
                addCount++;
            }else {

                MemberTransfer memberTransfer = memberTransferList.get(0);
                Integer id = memberTransfer.getId();
                record.setId(id);
                if (memberTransfer.getStatus()<MemberConstants.MEMBER_TRANSFER_STATUS_APPLY){
                    record.setApplyTime(new Date());
                }
                authAndStatus(record);
                memberTransferMapper.updateByPrimaryKeySelective(record);
            }

            // 导入时，转入申请通过的话，需要判断修改党员库中党员的状态
            if (record.getStatus() == MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY){
                Member member = memberService.get(userId);
                if (member.getStatus() != MemberConstants.MEMBER_STATUS_NORMAL){
                    member.setStatus(MemberConstants.MEMBER_STATUS_NORMAL);
                    memberMapper.updateByPrimaryKeySelective(member);

                    sysApprovalLogService.add(member.getUserId(), ShiroHelper.getCurrentUserId(),
                            SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                            SystemConstants.SYS_APPROVAL_LOG_PM,
                            "转为正常党员",SYS_APPROVAL_LOG_STATUS_NONEED,
                            "校内组织关系转接导入改变党员状态");
                }
            }
        }
        return addCount;
    }

    /*
    * 先判断权限：党建管理员导入，直接通过；分党委管理员导入，到转入党委审批阶段；其他导入，到申请阶段
    * 修改党员状态
    * */
    public void authAndStatus(MemberTransfer record){

        Byte status = null;
        if (!ShiroHelper.isPermitted(RoleConstants.PERMISSION_PARTYVIEWALL)) {

            Integer partyId = record.getPartyId();
            Integer loginUserId = ShiroHelper.getCurrentUserId();

            boolean isAdmin = partyMemberService.isPresentAdmin(loginUserId, partyId);

            boolean isToAdmin = partyMemberService.isPresentAdmin(loginUserId, record.getToPartyId());

            if(!isAdmin && !isToAdmin) {
                record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_APPLY);
            }else {
                record.setStatus(MemberConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
                record.setFromHandleTime(new Date());
            }
        }else {
            status = MemberConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY;
        }
        record.setStatus(status);

    }
}
