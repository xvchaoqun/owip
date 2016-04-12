package service.party;

import domain.*;
import domain.MemberTransfer;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberTransferService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;

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
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberTransfer> memberTransfers = memberTransferMapper.selectByExample(example);
        if(memberTransfers.size()>0) return memberTransfers.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_SELF_BACK);
        record.setBranchId(memberTransfer.getBranchId());
        memberTransferMapper.updateByPrimaryKeySelective(record);
    }

    // 不通过
    @Transactional
    public void deny(int userId, String reason){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_BACK);
        record.setReason(reason);
        record.setBranchId(memberTransfer.getBranchId());
        memberTransferMapper.updateByPrimaryKeySelective(record);
    }

    // 当前所在分党委审核通过
    @Transactional
    public void check1(int userId){

        MemberTransfer memberTransfer = get(userId);
        if(memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY);
        record.setBranchId(memberTransfer.getBranchId());
        memberTransferMapper.updateByPrimaryKeySelective(record);
    }

    // 转入分党委审核通过
    @Transactional
    public void check2(int userId, boolean isDirect){

        MemberTransfer memberTransfer = get(userId);

        if(isDirect && memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberTransfer.getStatus()!= SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY)
            throw new DBErrorException("状态异常");

        MemberTransfer record = new MemberTransfer();
        record.setId(memberTransfer.getId());
        record.setStatus(SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY);
        record.setBranchId(memberTransfer.getBranchId());
        memberTransferMapper.updateByPrimaryKeySelective(record);

        // 更改该党员的所在党组织
        Member member = memberMapper.selectByPrimaryKey(userId);
        member.setPartyId(memberTransfer.getToPartyId());
        member.setBranchId(memberTransfer.getToBranchId());
        memberMapper.updateByPrimaryKey(member);
    }

    @Transactional
    public int insertSelective(MemberTransfer record){

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
        return memberTransferMapper.updateByPrimaryKeySelective(record);
    }
}
