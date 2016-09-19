package service.party;

import domain.member.Member;
import domain.member.MemberTransfer;
import domain.member.MemberTransferExample;
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
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberTransferService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private MemberOpService memberOpService;
    
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
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
        } else if(type==2){ //转入分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberTransferMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberTransfer next(byte type, MemberTransfer memberTransfer){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //转出分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
        } else if(type==2){ //转入分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
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
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_APPLY);
        } else if(type==2){ //转入分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberTransfer!=null)
            criteria.andUserIdNotEqualTo(memberTransfer.getUserId()).andApplyTimeGreaterThanOrEqualTo(memberTransfer.getApplyTime());
        example.setOrderByClause("apply_time asc");

        List<MemberTransfer> memberApplies = memberTransferMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }
    
    public boolean idDuplicate(Integer id, Integer userId){

        MemberTransferExample example = new MemberTransferExample();
        MemberTransferExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberTransferMapper.countByExample(example) > 0;
    }

    public MemberTransfer get(int userId) {

        MemberTransferExample example = new MemberTransferExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusNotEqualTo(SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
        if(memberTransfers.size()>0) return memberTransfers.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("审批已经开始，不可以撤回");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setUserId(userId);
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK);
        //record.setBranchId(memberTransfer.getBranchId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        applyApprovalLogService.add(memberTransfer.getId(),
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), memberTransfer.getUserId(),
                shiroUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER,
                "撤回",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回校内组织关系互转申请");
    }

   /* // 不通过
    @Transactional
    public void deny(int userId, String reason){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_BACK);
        record.setReason(reason);
        record.setUserId(userId);
        //record.setBranchId(memberTransfer.getBranchId());
        updateByPrimaryKeySelective(record);
    }*/

    // 当前所在分党委审核通过
    @Transactional
    public void check1(int userId){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常，该记录不是申请状态");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setUserId(userId);
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        //record.setBranchId(memberTransfer.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 转入分党委审核通过
    @Transactional
    public void check2(int userId){

        MemberTransfer memberTransfer = get(userId);

        /*if(isDirect && memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常");*/
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY)
            throw new DBErrorException("只有转出分党委审核通过的记录才可以进行转入分党委审核");

        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setUserId(userId);
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        //record.setBranchId(memberTransfer.getBranchId());
        updateByPrimaryKeySelective(record);

        // 更改该党员的所在党组织
        Member member = memberMapper.selectByPrimaryKey(userId);
        member.setPartyId(memberTransfer.getToPartyId());
        member.setBranchId(memberTransfer.getToBranchId());
        memberMapper.updateByPrimaryKey(member);
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

        Assert.isTrue(record.getUserId()!=null);

        int opAuth = memberOpService.findOpAuth(record.getUserId());
        if(opAuth==1){
            throw new RuntimeException("已经申请了组织关系转出");
        }

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_transfer", "id", record.getId(), record.getPartyId());
        }

        return memberTransferMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberTransfer_check(Integer[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberTransfer memberTransfer = null;
            if(type==1) {
                VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth(id);
                memberTransfer = verifyAuth.entity;

                check1(memberTransfer.getUserId());
            }
            if(type==2) {
                VerifyAuth<MemberTransfer> verifyAuth = checkVerityAuth2(id);
                memberTransfer = verifyAuth.entity;

                check2(memberTransfer.getUserId());
            }
            int userId = memberTransfer.getUserId();
            applyApprovalLogService.add(memberTransfer.getId(),
                    memberTransfer.getPartyId(), memberTransfer.getBranchId(), userId,
                    loginUserId,  (type == 1)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_OUT_PARTY:
                            SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_IN_PARTY,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, (type == 1)
                            ? "转出分党委审核" : "转入分党委审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberTransfer_back(Integer[] userIds, byte status, String reason, int loginUserId){

        for (int userId : userIds) {

            MemberTransfer memberTransfer = memberTransferMapper.selectByPrimaryKey(userId);
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberTransfer.getPartyId());
            Boolean presentToPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberTransfer.getToPartyId());

            if(memberTransfer.getStatus() >= SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY){
                if(!presentToPartyAdmin) throw new UnauthorizedException();
            }
            if(memberTransfer.getStatus() >= SystemConstants.MEMBER_TRANSFER_STATUS_BACK){
                if(!presentToPartyAdmin && !presentPartyAdmin) throw new UnauthorizedException();
            }

            back(memberTransfer, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberTransfer memberTransfer, byte status, int loginUserId, String reason){

        byte _status = memberTransfer.getStatus();
        if(_status==SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status > _status || status<SystemConstants.MEMBER_TRANSFER_STATUS_BACK ){
            throw new RuntimeException("参数有误。");
        }

        Integer id = memberTransfer.getId();
        Integer userId = memberTransfer.getUserId();
        updateMapper.memberTransfer_back(id, status);

        MemberTransfer record = new MemberTransfer();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberTransfer.getPartyId(), memberTransfer.getBranchId(), userId,
                loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_TRANSFER, SystemConstants.MEMBER_TRANSFER_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
