package controller.sys;

import controller.BaseController;
import domain.sys.SysUser;
import domain.sys.SysUserInfo;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.shiro.CurrentUser;
import sys.shiro.SaltPassword;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.FormUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2015/11/18.
 */
@Controller
public class ProfileController extends BaseController {

    @RequiresPermissions("passportApply:*")
    @RequestMapping(value = "/profile_sign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_profile_sign(@CurrentUser SysUserView loginUser, String mobile, String phone,
                                     MultipartFile sign) throws IOException {

        if (StringUtils.isBlank(phone)) {
            return failed("请填写办公电话");
        }

        if (StringUtils.isBlank(mobile) ||
                !CmTag.validMobile(mobile)) {
            return failed("手机号码有误");
        }

        SysUserInfo record = new SysUserInfo();
        record.setUserId(loginUser.getId());
        record.setSign(sysUserService.uploadSign(loginUser.getUserId(), sign));
        record.setMobile(mobile);
        record.setPhone(phone);

        sysUserService.insertOrUpdateUserInfoSelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("setting:view")
    @RequestMapping("/setting")
    public String setting(ModelMap modelMap) {

        return "sys/profile/setting";
    }

    @RequiresPermissions("profile:view")
    @RequestMapping("/profile")
    public String profile(@CurrentUser SysUserView loginUser,
                          @RequestParam(required = false, defaultValue = "1") byte type,
                          ModelMap modelMap) {

        modelMap.put("type", type);

        if (type == 3) {

            modelMap.put("sysUser", loginUser);
            return "sys/profile/password";

        } else if (type == 4) {

            return "sys/profile/profile_sign";
        } else {
            Integer userId = loginUser.getId();
            modelMap.put("adminPartyIdList", partyAdminService.adminPartyIdList(userId));
            modelMap.put("adminBranchIdList", branchAdminService.adminBranchIdList(userId));

            return "sys/profile/profile";
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public Map do_profile(MultipartFile _avatar, String realname, String _birth, Byte gender, String email, String mobile) throws IOException {

        int userId = ShiroHelper.getCurrentUserId();
        //SysUserView uv = sysUserService.findById(userId);

        String avatar = avatarService.uploadAvatar(_avatar);

        SysUserInfo record = new SysUserInfo();
        record.setUserId(userId);
        record.setAvatar(avatar);
        record.setAvatarUploadTime(new Date());
        record.setRealname(realname);
        record.setBirth(DateUtils.parseDate(_birth, DateUtils.YYYY_MM_DD));
        record.setGender(gender);
        record.setEmail(email);
        record.setMobile(mobile);

        sysUserService.insertOrUpdateUserInfoSelective(record);

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("profile:updateAvatar")
    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    @ResponseBody
    public Map do_updateAvatar(MultipartFile _avatar, int userId) throws IOException {

        //SysUserView uv = sysUserService.findById(userId);
        String avatar = avatarService.uploadAvatar(_avatar);

        SysUserInfo record = new SysUserInfo();
        record.setUserId(userId);
        record.setAvatar(avatar);
        sysUserService.insertOrUpdateUserInfoSelective(record);
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("password:modify")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public Map password(@CurrentUser SysUserView sysUser, String oldPassword, String password, HttpServletRequest request) {

        if (sysUser.isCasUser()) {
            return failed("当前账号不允许修改密码");
        }
      /*  oldPassword = new String(Base64.decodeBase64(oldPassword.getBytes()));
        password = new String(Base64.decodeBase64(password.getBytes()));*/

        String passwordHash = passwordHelper.encryptBySalt(oldPassword, sysUser.getSalt());
        if (!StringUtils.equalsIgnoreCase(sysUser.getPasswd(), passwordHash)) {
            return failed("原密码错误");
        }

        if (!ShiroHelper.hasRole(RoleConstants.ROLE_SUPER)) {
            if (password != null) {
                if (!CmTag.validPasswd(password)) {
                    return failed(CmTag.getStringProperty("passwdMsg"));
                }
            }
        }

        SysUser _sysUser = new SysUser();
        _sysUser.setId(sysUser.getId());
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(password);
        _sysUser.setSalt(encrypt.getSalt());
        _sysUser.setPasswd(encrypt.getPassword());
        sysUserService.updateByPrimaryKeySelective(_sysUser);

        return success(FormUtils.SUCCESS);
    }
}
