package service.party;

import domain.MemberOut;
import domain.MemberOutExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.DBErrorException;
import service.LoginUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberOutService extends BaseMapper {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private LoginUserService loginUserService;

    public int count(Integer partyId, Integer branchId, byte type){

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberOutMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberOut next(byte type, MemberOut memberOut){

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberOut!=null)
            criteria.andUserIdNotEqualTo(memberOut.getUserId()).andApplyTimeLessThanOrEqualTo(memberOut.getApplyTime());
        example.setOrderByClause("apply_time desc");

        List<MemberOut> memberApplies = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberOut last(byte type, MemberOut memberOut){

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberOut!=null)
            criteria.andUserIdNotEqualTo(memberOut.getUserId()).andApplyTimeGreaterThanOrEqualTo(memberOut.getApplyTime());
        example.setOrderByClause("apply_time asc");

        List<MemberOut> memberApplies = memberOutMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    public boolean idDuplicate(Integer id, Integer userId){

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberOutMapper.countByExample(example) > 0;
    }

    public MemberOut get(int userId) {

        MemberOutExample example = new MemberOutExample();
        MemberOutExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);
        if(memberOuts.size()>0) return memberOuts.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberOut memberOut = get(userId);
        if(memberOut.getStatus()!= SystemConstants.MEMBER_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_SELF_BACK);
        //record.setBranchId(memberOut.getBranchId());
        updateByPrimaryKeySelective(record);
    }
    
    // 不通过
    @Transactional
    public void deny(int userId, String reason){

        MemberOut memberOut = get(userId);
        if(memberOut.getStatus()!= SystemConstants.MEMBER_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_BACK);
        record.setReason(reason);
        //record.setBranchId(memberOut.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 党总支、直属党支部审核通过
    @Transactional
    public void check1(int userId){

        MemberOut memberOut = get(userId);
        if(memberOut.getStatus()!= SystemConstants.MEMBER_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);
        //record.setBranchId(memberOut.getBranchId());
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

        MemberOut memberOut = get(userId);

        if(isDirect && memberOut.getStatus()!= SystemConstants.MEMBER_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberOut.getStatus()!= SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY)
            throw new DBErrorException("状态异常");

        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY);
        //record.setBranchId(memberOut.getBranchId());
        updateByPrimaryKeySelective(record);

        memberService.quit(userId, SystemConstants.MEMBER_STATUS_TRANSFER);
    }
    @Transactional
    public int insertSelective(MemberOut record){

        return memberOutMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberOutMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberOutExample example = new MemberOutExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberOutMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberOut record){
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_out", "id", record.getId(), record.getPartyId());
        }

        return memberOutMapper.updateByPrimaryKeySelective(record);
    }

}
