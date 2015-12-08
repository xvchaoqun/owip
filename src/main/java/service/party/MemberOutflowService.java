package service.party;

import domain.MemberOutflow;
import domain.MemberOutflowExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberOutflowService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer userId){


        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberOutflowMapper.countByExample(example) > 0;
    }

    public MemberOutflow get(int userId) {

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberOutflow> memberOutflows = memberOutflowMapper.selectByExample(example);
        if(memberOutflows.size()>0) return memberOutflows.get(0);

        return null;
    }

    // 本人撤回
    @Transactional
    public void back(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_SELF_BACK);

        memberOutflowMapper.updateByPrimaryKeySelective(record);
    }

    // 不通过
    @Transactional
    public void deny(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_DENY);

        memberOutflowMapper.updateByPrimaryKeySelective(record);
    }

    // 党支部审核通过
    @Transactional
    public void check1(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);

        memberOutflowMapper.updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void check2(int userId, boolean isDirect){

        MemberOutflow memberOutflow = get(userId);

        if(isDirect && memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY);
        memberOutflowMapper.updateByPrimaryKeySelective(record);
    }
    @Transactional
    public int insertSelective(MemberOutflow record){

        return memberOutflowMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberOutflowMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberOutflowExample example = new MemberOutflowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberOutflowMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberOutflow record){
        return memberOutflowMapper.updateByPrimaryKeySelective(record);
    }
}
