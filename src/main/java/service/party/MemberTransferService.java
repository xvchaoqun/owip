package service.party;

import domain.Member;
import domain.MemberTransfer;
import domain.MemberTransfer;
import domain.MemberTransferExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberTransferService extends BaseMapper {

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
