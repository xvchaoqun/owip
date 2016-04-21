package service.party;

import domain.Member;
import domain.MemberReturn;
import domain.MemberReturnExample;
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
import java.util.Date;
import java.util.List;

@Service
public class MemberReturnService extends BaseMapper {

    @Autowired
    private  MemberService memberService;
    @Autowired
    private PartyService partyService;
    @Autowired
    private LoginUserService loginUserService;

    public int count(Integer partyId, Integer branchId, byte type){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberReturnMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberReturn next(byte type, MemberReturn memberReturn){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberReturn!=null)
            criteria.andUserIdNotEqualTo(memberReturn.getUserId()).andCreateTimeLessThanOrEqualTo(memberReturn.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberReturn> memberApplies = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberReturn last(byte type, MemberReturn memberReturn){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberReturn!=null)
            criteria.andUserIdNotEqualTo(memberReturn.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberReturn.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberReturn> memberApplies = memberReturnMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }
    public boolean idDuplicate(Integer id, Integer userId){

        MemberReturnExample example = new MemberReturnExample();
        MemberReturnExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberReturnMapper.countByExample(example) > 0;
    }

    public MemberReturn get(int userId) {

        MemberReturnExample example = new MemberReturnExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<MemberReturn> memberReturns = memberReturnMapper.selectByExample(example);
        if(memberReturns.size()>0) return memberReturns.get(0);

        return null;
    }

    // 党支部审核通过
    @Transactional
    public void checkMember(int userId){

        MemberReturn memberReturn = get(userId);
        if(memberReturn.getStatus()!= SystemConstants.MEMBER_RETURN_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberReturn record = new MemberReturn();
        record.setId(memberReturn.getId());
        record.setStatus(SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY);
        //record.setBranchId(memberReturn.getBranchId());
        updateByPrimaryKeySelective(record);
    }
    // 分党委审核通过
    @Transactional
    public void addMember(int userId, byte politicalStatus, boolean isDirect){

        Member _member = memberService.get(userId);
        if(_member!=null){
            throw new RuntimeException("已经是党员");
        }

        MemberReturn memberReturn = get(userId);
        if(isDirect && memberReturn.getStatus()!= SystemConstants.MEMBER_RETURN_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberReturn.getStatus()!= SystemConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberReturn record = new MemberReturn();
        record.setId(memberReturn.getId());
        record.setStatus(SystemConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY);
        //record.setBranchId(memberReturn.getBranchId());
        updateByPrimaryKeySelective(record);

        // 添加党员操作
        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberReturn.getPartyId());
        member.setBranchId(memberReturn.getBranchId());
        member.setPoliticalStatus(politicalStatus);

        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_RETURNED); //  恢复党员
        member.setApplyTime(memberReturn.getApplyTime());
        member.setActiveTime(memberReturn.getActiveTime());
        member.setCandidateTime(memberReturn.getCandidateTime());
        member.setGrowTime(memberReturn.getGrowTime());
        member.setPositiveTime(memberReturn.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }

    @Transactional
    public int insertSelective(MemberReturn record){

        return memberReturnMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberReturnMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberReturnExample example = new MemberReturnExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberReturnMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberReturn record){
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_return", "id", record.getId(), record.getPartyId());
        }

        return memberReturnMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByExampleSelective(MemberReturn record, MemberReturnExample example) {

        return memberReturnMapper.updateByExampleSelective(record, example);
    }
}
