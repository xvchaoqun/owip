package service.party;

import domain.MemberOut;
import domain.MemberOutExample;
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
public class MemberOutService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    
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

        memberOutMapper.updateByPrimaryKeySelective(record);
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

        memberOutMapper.updateByPrimaryKeySelective(record);
    }

    // 分党委审核通过
    @Transactional
    public void check1(int userId){

        MemberOut memberOut = get(userId);
        if(memberOut.getStatus()!= SystemConstants.MEMBER_OUT_STATUS_APPLY)
            throw new DBErrorException("状态异常");
        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        record.setStatus(SystemConstants.MEMBER_OUT_STATUS_PARTY_VERIFY);

        memberOutMapper.updateByPrimaryKeySelective(record);
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
        memberOutMapper.updateByPrimaryKeySelective(record);
        
        // 更新系统角色  党员->访客
        SysUser sysUser = sysUserService.findById(userId);
        sysUserService.changeRoleMemberToGuest(userId, sysUser.getUsername());
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
        return memberOutMapper.updateByPrimaryKeySelective(record);
    }

}
