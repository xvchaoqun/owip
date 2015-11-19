package controller;

import domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.CurrentUser;
import shiro.SaltPassword;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by fafa on 2015/11/18.
 */
@Controller
public class ProfileController extends BaseController {

    @RequiresPermissions("setting:view")
    @RequestMapping("/setting")
    public String setting() {

        return "index";
    }

    @RequiresPermissions("setting:view")
    @RequestMapping("/setting_page")
    public String setting_page(ModelMap modelMap) {

        return "profile/setting";
    }

    @RequiresPermissions("profile:view")
    @RequestMapping("/profile")
    public String profile() {

        return "index";
    }

    @RequiresPermissions("profile:view")
    @RequestMapping("/profile_page")
    public String profile_page(ModelMap modelMap) {

        return "profile/profile";
    }

    @RequiresPermissions("password:modify")
    @RequestMapping("/password")
    public String password() {

        return "index";
    }

    @RequiresPermissions("password:modify")
    @RequestMapping("/password_page")
    public String password_page(ModelMap modelMap) {

        return "profile/password";
    }

    @RequiresPermissions("password:modify")
    @RequestMapping(value="/password", method= RequestMethod.POST)
    @ResponseBody
    public Map password(@CurrentUser SysUser sysUser, String oldPassword, String password, HttpServletRequest request) {


        String passwordHash = passwordHelper.encryptBySalt(oldPassword, sysUser.getSalt());
        if(!StringUtils.equalsIgnoreCase(sysUser.getPasswd(), passwordHash)){
            return failed("原密码错误");
        }

        SysUser _sysUser = new SysUser();
        _sysUser.setId(sysUser.getId());
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(password);
        _sysUser.setSalt(encrypt.getSalt());
        _sysUser.setPasswd(encrypt.getPassword());
        sysUserService.updateByPrimaryKeySelective(_sysUser, sysUser.getUsername() );

        return success(FormUtils.SUCCESS);
    }
}
