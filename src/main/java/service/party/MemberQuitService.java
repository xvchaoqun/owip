package service.party;

import domain.Member;
import domain.MemberQuit;
import domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.DBErrorException;
import service.sys.SysUserService;
import sys.constants.SystemConstants;

@Service
public class MemberQuitService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;

    @Transactional
    public void quit(MemberQuit record){

        if(memberQuitMapper.insertSelective(record)==0)
            throw new DBErrorException("出党操作失败");


        Integer userId = record.getUserId();
        Member member = new Member();
        member.setUserId(userId);
        member.setStatus(SystemConstants.MEMBER_STATUS_QUIT);
        memberMapper.updateByPrimaryKeySelective(member);

        SysUser sysUser = sysUserService.findById(userId);
        // 更新系统角色  党员->访客
        sysUserService.changeRole(userId, SystemConstants.ROLE_MEMBER,
                SystemConstants.ROLE_GUEST, sysUser.getUsername());
    }
    @Transactional
    public void del(Integer userId){

        memberQuitMapper.deleteByPrimaryKey(userId);
    }


    @Transactional
    public int updateByPrimaryKeySelective(MemberQuit record){
        return memberQuitMapper.updateByPrimaryKeySelective(record);
    }
}
