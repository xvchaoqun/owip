package service.party;

import domain.MemberOutflow;
import domain.MemberOutflowExample;
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
public class MemberOutflowService extends BaseMapper {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PartyService partyService;

    public int count(Integer partyId, Integer branchId, byte type){

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }
        if(partyId!=null) criteria.andPartyIdEqualTo(partyId);
        if(branchId!=null) criteria.andBranchIdEqualTo(branchId);

        return memberOutflowMapper.countByExample(example);
    }

    // 上一个 （查找比当前记录的“创建时间”  小  的记录中的  最大  的“创建时间”的记录）
    public MemberOutflow next(byte type, MemberOutflow memberOutflow){

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberOutflow!=null)
            criteria.andUserIdNotEqualTo(memberOutflow.getUserId()).andCreateTimeLessThanOrEqualTo(memberOutflow.getCreateTime());
        example.setOrderByClause("create_time desc");

        List<MemberOutflow> memberApplies = memberOutflowMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

    // 下一个（查找比当前记录的“创建时间” 大  的记录中的  最小  的“创建时间”的记录）
    public MemberOutflow last(byte type, MemberOutflow memberOutflow){

        MemberOutflowExample example = new MemberOutflowExample();
        MemberOutflowExample.Criteria criteria = example.createCriteria();

        criteria.addPermits(loginUserService.adminPartyIdList(), loginUserService.adminBranchIdList());

        if(type==1){ //支部审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY);
        } else if(type==2){ //分党委审核
            criteria.andStatusEqualTo(SystemConstants.MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY);
        }else{
            throw new RuntimeException("审核类型错误");
        }

        if(memberOutflow!=null)
            criteria.andUserIdNotEqualTo(memberOutflow.getUserId()).andCreateTimeGreaterThanOrEqualTo(memberOutflow.getCreateTime());
        example.setOrderByClause("create_time asc");

        List<MemberOutflow> memberApplies = memberOutflowMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        return (memberApplies.size()==0)?null:memberApplies.get(0);
    }

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
        if(memberOutflow.getStatus()!= SystemConstants.MEMBER_OUTFLOW_STATUS_APPLY) // 只有申请状态可以撤回
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_BACK);
        //record.setBranchId(memberOutflow.getBranchId());
        updateByPrimaryKeySelective(record);
    }

    // 不通过
    @Transactional
    public void deny(int userId){

        MemberOutflow memberOutflow = get(userId);
        if(memberOutflow.getStatus()== SystemConstants.MEMBER_OUTFLOW_STATUS_PARTY_VERIFY) // 终审操作前，可以不通过（即打回）
            throw new DBErrorException("状态异常");
        MemberOutflow record = new MemberOutflow();
        record.setId(memberOutflow.getId());
        record.setStatus(SystemConstants.MEMBER_OUTFLOW_STATUS_BACK);
        //record.setBranchId(memberOutflow.getBranchId());
        updateByPrimaryKeySelective(record);
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
        //record.setBranchId(memberOutflow.getBranchId());
        updateByPrimaryKeySelective(record);
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
        //record.setBranchId(memberOutflow.getBranchId());

        updateByPrimaryKeySelective(record);
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
        if(record.getPartyId()!=null && record.getBranchId()==null){
            // 修改为直属党支部
            Assert.isTrue(partyService.isDirectBranch(record.getPartyId()));
            updateMapper.updateToDirectBranch("ow_member_outflow", "id", record.getId(), record.getPartyId());
        }

        return memberOutflowMapper.updateByPrimaryKeySelective(record);
    }
}
