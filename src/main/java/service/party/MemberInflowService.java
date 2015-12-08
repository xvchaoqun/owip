package service.party;

import domain.MemberInflow;
import domain.MemberInflowExample;
import domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberInflowService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    public boolean idDuplicate(Integer id, Integer userId){


        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return memberInflowMapper.countByExample(example) > 0;
    }

    public MemberInflow get(int userId) {

        MemberInflowExample example = new MemberInflowExample();
        MemberInflowExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<MemberInflow> memberReturns = memberInflowMapper.selectByExample(example);
        if(memberReturns.size()>0) return memberReturns.get(0);

        return null;
    }

    // 党支部审核通过
    @Transactional
    public void checkMember(int userId){

        MemberInflow memberInflow = get(userId);
        if(memberInflow.getInflowStatus()!= SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setInflowStatus(SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY);

        memberInflowMapper.updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void addMember(int userId, boolean isDirect){

        SysUser sysUser = sysUserService.findById(userId);
        MemberInflow memberInflow = get(userId);

        if(isDirect && memberInflow.getInflowStatus()!= SystemConstants.MEMBER_INFLOW_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        if(!isDirect && memberInflow.getInflowStatus()!= SystemConstants.MEMBER_INFLOW_STATUS_BRANCH_VERIFY)
            throw new DBErrorException("状态异常");

        MemberInflow record = new MemberInflow();
        record.setId(memberInflow.getId());
        record.setInflowStatus(SystemConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY);
        memberInflowMapper.updateByPrimaryKeySelective(record);

        // 更新系统角色  访客->流入党员
        sysUserService.changeRole(sysUser.getId(), SystemConstants.ROLE_GUEST,
                SystemConstants.ROLE_INFLOWMEMBER, sysUser.getUsername());

    }

    @Transactional
    public int insertSelective(MemberInflow record){

        return memberInflowMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        memberInflowMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MemberInflowExample example = new MemberInflowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        memberInflowMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(MemberInflow record){
        return memberInflowMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public int updateByExampleSelective(MemberInflow record, MemberInflowExample example){
        return memberInflowMapper.updateByExampleSelective(record, example);
    }

}
