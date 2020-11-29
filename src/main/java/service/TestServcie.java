package service;

import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import service.member.EnterApplyService;
import service.sys.SysUserService;
import sys.constants.RoleConstants;
import sys.utils.JSONUtils;

/**
 * Created by fafa on 2016/1/18.
 */
//@Service
public class TestServcie {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EnterApplyService enterApplyService;
    @Autowired

    public void toMember(int userId){
        SysUserView sysUser1 = sysUserService.findById(userId);
        System.out.println("sysUser1=" + JSONUtils.toString(sysUser1));
        sysUserService.changeRoleGuestToMember(userId);
        SysUserView sysUser2 = sysUserService.findById(userId);
        System.out.println("sysUser2=" + JSONUtils.toString(sysUser2));
    }

    public void toGuest(int userId){
        SysUserView sysUser1 = sysUserService.findById(userId);
        System.out.println("sysUser1=" + JSONUtils.toString(sysUser1));
        // 更新系统角色  党员->访客
        sysUserService.changeRole(userId, RoleConstants.ROLE_MEMBER, RoleConstants.ROLE_GUEST);
        SysUserView sysUser2 = sysUserService.findById(userId);
        System.out.println("sysUser2=" + JSONUtils.toString(sysUser2));
    }

    public void uploadPhoto(MultipartFile _photo, String folder){



    }
}
