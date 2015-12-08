package service.party;

import domain.Member;
import domain.MemberReturn;
import domain.MemberReturnExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MemberReturnService extends BaseMapper {

    @Autowired
    private  MemberService memberService;
    @Autowired
    private SysUserService sysUserService;

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

        memberReturnMapper.updateByPrimaryKeySelective(record);
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
        memberReturnMapper.updateByPrimaryKeySelective(record);

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
        return memberReturnMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByExampleSelective(MemberReturn record, MemberReturnExample example) {

        return memberReturnMapper.updateByExampleSelective(record, example);
    }
}
