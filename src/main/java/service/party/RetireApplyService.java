package service.party;

import domain.Member;
import domain.RetireApply;
import domain.RetireApplyExample;
import domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2015/11/30.
 */
@Service
public class RetireApplyService extends BaseMapper{
    @Autowired
    private SysUserService sysUserService;

    public int insertSelective(RetireApply record){

        return retireApplyMapper.insertSelective(record);
    }

    public RetireApply get(int userId) {

        RetireApplyExample example = new RetireApplyExample();
        RetireApplyExample.Criteria criteria = example.createCriteria().andUserIdEqualTo(userId);
        List<RetireApply> retireApplys = retireApplyMapper.selectByExample(example);
        if(retireApplys.size()>0) return retireApplys.get(0);

        return null;
    }
    //审核
    @Transactional
    public void verify(int userId, int verifyId){

        RetireApply record = new RetireApply();
        record.setUserId(userId);
        record.setStatus(SystemConstants.RETIRE_APPLY_STATUS_CHECKED);
        record.setVerifyId(verifyId);
        record.setVerifyTime(new Date());
        retireApplyMapper.updateByPrimaryKeySelective(record);

        Member member = new Member();
        member.setUserId(userId);
        member.setStatus(SystemConstants.MEMBER_STATUS_RETIRE);
        memberMapper.updateByPrimaryKeySelective(member);

        // 更新系统角色  党员->访客
        SysUser sysUser = sysUserService.findById(userId);
        sysUserService.changeRoleMemberToGuest(userId, sysUser.getUsername());
    }

}
