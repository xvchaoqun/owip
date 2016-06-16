package service.party;

import domain.*;
import domain.MemberStay;
import domain.MemberStayExample;
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
public class MemberStayService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private MemberOpService memberOpService;

    @Autowired
    protected ApplyApprovalLogService applyApprovalLogService;
    private VerifyAuth<MemberStay> checkVerityAuth(int id){
        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth(memberStay, memberStay.getPartyId(), memberStay.getBranchId());
    }

    private VerifyAuth<MemberStay> checkVerityAuth2(int id){
        MemberStay memberStay = memberStayMapper.selectByPrimaryKey(id);
        return super.checkVerityAuth2(memberStay, memberStay.getPartyId());
    }
    
    public int count(Integer partyId, Integer branchId, byte type){

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_APPLY);
        } else if(type==2){ //组织部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberStayMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberStay next(byte type, MemberStay memberStay){

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_APPLY);
        } else if(type==2){ //组织部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberStay!=null)
            criteria.andUserIdNotEqualTo(memberStay.getUserId()).andApplyTimeLessThanOrEqualTo(memberStay.getApplyTime());
        example.setOrderByClause("apply_time desc");

        List<MemberStay> records = memberStayMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (records.size()==0)?null:records.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberStay last(byte type, MemberStay memberStay){

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_APPLY);
        } else if(type==2){ //组织部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberStay!=null)
            criteria.andUserIdNotEqualTo(memberStay.getUserId()).andApplyTimeGreaterThanOrEqualTo(memberStay.getApplyTime());
        example.setOrderByClause("apply_time asc");

        List<MemberStay> memberApplies = memberStayMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }
    
    public boolean idDuplicate(Integer id, Integer userId){

        MemberStayExample example = new MemberStayExample();
        MemberStayExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberStayMapper.countByExample(example) > 0;
    }

    public MemberStay get(int userId) {

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberStay> memberStays = memberStayMapper.selectByExample(example);
        if(memberStays.size()>0) return memberStays.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberStay memberStay = get(userId);
        if(memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_SELF_BACK);
        record.setUserId(memberStay.getUserId());
        //record.setBranchId(memberStay.getBranchId());
        record.setIsBack(false);
        updateByPrimaryKeySelective(record);

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        applyApprovalLogService.add(memberStay.getId(),
                memberStay.getPartyId(), memberStay.getBranchId(), memberStay.getUserId(),
                shiroUser.getId(), SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY,
                "撤回",
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_NONEED,
                "撤回暂留申请");
    }

    // 不通过
    @Transactional
    public void deny(int userId, String reason){

        MemberStay memberStay = get(userId);
        if(memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_BACK);
        record.setReason(reason);
        record.setUserId(memberStay.getUserId());
        //record.setBranchId(memberStay.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 党总支、直属党支部审核通过
    @Transactional
    public void check1(int userId){

        MemberStay memberStay = get(userId);
        if(memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setUserId(memberStay.getUserId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY);
        //record.setBranchId(memberStay.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void checkByParty(int userId, boolean isDirect){

        check1(userId);
        check2(userId, isDirect);
    }

    // 组织部审核通过
    @Transactional
    public void check2(int userId, boolean isDirect){

        MemberStay memberStay = get(userId);

        if(isDirect && memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberStay.getStatus()!= SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY)
            throw new DBErrorException("状态异常");

        MemberStay record = new MemberStay();
        record.setId(memberStay.getId());
        record.setStatus(SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY);
        record.setUserId(memberStay.getUserId());
        //record.setBranchId(memberStay.getBranchId());
        updateByPrimaryKeySelective(record);
    }
    @Transactional
    public int insertSelective(MemberStay record){

        memberOpService.checkOpAuth(record.getUserId());

        return memberStayMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberStayMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberStayExample example = new MemberStayExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberStayMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberStay record){

        memberOpService.checkOpAuth(record.getUserId());

        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_stay", "id", record.getId(), record.getPartyId());
        }

        return memberStayMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void memberStay_check(Integer[] ids, byte type, int loginUserId){

        for (int id : ids) {
            MemberStay memberStay = null;
            if(type==1) {
                VerifyAuth<MemberStay> verifyAuth = checkVerityAuth2(id);
                memberStay = verifyAuth.entity;
                boolean isParty = verifyAuth.isParty;

                if (isParty) { // 分党委审核，需要跳过下一步的组织部审核
                    checkByParty(memberStay.getUserId(), false);
                } else {
                    check1(memberStay.getUserId());
                }
            }
            if(type==2) {
                SecurityUtils.getSubject().checkRole("odAdmin");

                memberStay = memberStayMapper.selectByPrimaryKey(id);

                check2(memberStay.getUserId(), false);
            }
            int userId = memberStay.getUserId();
            applyApprovalLogService.add(memberStay.getId(),
                    memberStay.getPartyId(), memberStay.getBranchId(), userId,
                    loginUserId, (type == 1)?SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_PARTY:
                            SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_OW,
                    SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, (type == 1)
                            ? "分党委审核" : "组织部审核", (byte) 1, null);
        }
    }

    @Transactional
    public void memberStay_back(Integer[] userIds, byte status, String reason, int loginUserId){

        boolean odAdmin = SecurityUtils.getSubject().hasRole("odAdmin");
        for (int userId : userIds) {

            MemberStay memberStay = memberStayMapper.selectByPrimaryKey(userId);
            Boolean presentPartyAdmin = CmTag.isPresentPartyAdmin(loginUserId, memberStay.getPartyId());

            if(status >= SystemConstants.MEMBER_STAY_STATUS_PARTY_VERIFY){
                if(!odAdmin) throw new UnauthorizedException();
            }
            if(status >= SystemConstants.MEMBER_STAY_STATUS_BACK){
                if(!odAdmin && !presentPartyAdmin) throw new UnauthorizedException();
            }

            back(memberStay, status, loginUserId, reason);
        }
    }

    // 单条记录打回至某一状态
    private  void back(MemberStay memberStay, byte status, int loginUserId, String reason){

        byte _status = memberStay.getStatus();
        if(_status==SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY){
            throw new RuntimeException("审核流程已经完成，不可以打回。");
        }
        if(status > _status || status<SystemConstants.MEMBER_STAY_STATUS_BACK ){
            throw new RuntimeException("参数有误。");
        }
        Integer id = memberStay.getId();
        Integer userId = memberStay.getUserId();
        updateMapper.memberStay_back(id, status);

        MemberStay record = new MemberStay();
        record.setId(id);
        record.setUserId(userId);
        record.setReason(reason);
        record.setIsBack(true);
        updateByPrimaryKeySelective(record);

        applyApprovalLogService.add(id,
                memberStay.getPartyId(), memberStay.getBranchId(), userId,
                loginUserId, SystemConstants.APPLY_APPROVAL_LOG_USER_TYPE_ADMIN,
                SystemConstants.APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY, SystemConstants.MEMBER_STAY_STATUS_MAP.get(status),
                SystemConstants.APPLY_APPROVAL_LOG_STATUS_BACK, reason);
    }
}
