package service;

import domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.sys.SysUserService;
import sys.utils.JSONUtils;

/**
 * Created by fafa on 2016/1/18.
 */
@Service
public class TestServcie {
    @Autowired
    private SysUserService sysUserService;

    public void toMember(int userId){
        SysUser sysUser1 = sysUserService.findById(userId);
        System.out.println("sysUser1=" + JSONUtils.toString(sysUser1));
        sysUserService.changeRoleGuestToMember(userId, sysUser1.getUsername(), sysUser1.getCode());
        SysUser sysUser2 = sysUserService.findById(userId);
        System.out.println("sysUser2=" + JSONUtils.toString(sysUser2));
    }

    public void toGuest(int userId){
        SysUser sysUser1 = sysUserService.findById(userId);
        System.out.println("sysUser1=" + JSONUtils.toString(sysUser1));
        sysUserService.changeRoleMemberToGuest(userId, sysUser1.getUsername(), sysUser1.getCode());
        SysUser sysUser2 = sysUserService.findById(userId);
        System.out.println("sysUser2=" + JSONUtils.toString(sysUser2));
    }
}
