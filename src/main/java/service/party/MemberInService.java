package service.party;

import domain.Member;
import domain.MemberIn;
import domain.MemberInExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MemberInService extends BaseMapper {

    @Autowired
    private  MemberService memberService;

    public boolean idDuplicate(Integer id, Integer userId){

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberInMapper.countByExample(example) > 0;
    }

    public MemberIn get(int userId) {

        MemberInExample example = new MemberInExample();
        MemberInExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberIn> memberIns = memberInMapper.selectByExample(example);
        if(memberIns.size()>0) return memberIns.get(0);

        return null;
    }

    // 党支部、直属党支部审核通过
    @Transactional
    public void checkMember(int userId){

        MemberIn memberIn = get(userId);
        if(memberIn.getStatus()!= SystemConstants.MEMBER_IN_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberIn record = new MemberIn();
        record.setId(memberIn.getId());
        record.setStatus(SystemConstants.MEMBER_IN_STATUS_PARTY_VERIFY);

        memberInMapper.updateByPrimaryKeySelective(record);
    }

    // 分党委审核， 不需要下一步组织部审核
    @Transactional
    public void checkByParty(int userId, byte politicalStatus){

        checkMember(userId);
        addMember(userId, politicalStatus);
    }

    // 组织部审核通过
    @Transactional
    public void addMember(int userId, byte politicalStatus){

        Member _member = memberService.get(userId);
        if(_member!=null){
            throw new RuntimeException("已经是党员");
        }

        MemberIn memberIn = get(userId);
        if(memberIn.getStatus()!= SystemConstants.MEMBER_IN_STATUS_PARTY_VERIFY)
            throw new DBErrorException("状态异常");

        MemberIn record = new MemberIn();
        record.setId(memberIn.getId());
        record.setStatus(SystemConstants.MEMBER_IN_STATUS_OW_VERIFY);
        memberInMapper.updateByPrimaryKeySelective(record);

        // 添加党员操作
        Member member = new Member();
        member.setUserId(userId);
        member.setPartyId(memberIn.getPartyId());
        member.setBranchId(memberIn.getBranchId());
        member.setPoliticalStatus(politicalStatus);

        member.setStatus(SystemConstants.MEMBER_STATUS_NORMAL); // 正常党员
        member.setSource(SystemConstants.MEMBER_SOURCE_TRANSFER); // 转入党员
        member.setApplyTime(memberIn.getApplyTime());
        member.setActiveTime(memberIn.getActiveTime());
        member.setCandidateTime(memberIn.getCandidateTime());
        member.setGrowTime(memberIn.getGrowTime());
        member.setPositiveTime(memberIn.getPositiveTime());
        member.setCreateTime(new Date());

        //3. 进入党员库
        memberService.add(member);
    }
    @Transactional
    public int insertSelective(MemberIn record){

        return  memberInMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberInMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberInExample example = new MemberInExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberInMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberIn record){
        return memberInMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByExampleSelective(MemberIn record, MemberInExample example){
        return memberInMapper.updateByExampleSelective(record, example);
    }

}
