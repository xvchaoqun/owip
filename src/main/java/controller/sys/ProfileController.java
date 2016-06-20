package controller.sys;

import controller.BaseController;
import domain.SysUser;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import shiro.CurrentUser;
import shiro.SaltPassword;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by fafa on 2015/11/18.
 */
@Controller
public class ProfileController extends BaseController {

    @RequiresPermissions("setting:view")
    @RequestMapping("/profile_sign")
    public String sign() {

        return "index";
    }

    @RequiresPermissions("setting:view")
    @RequestMapping("/profile_sign_page")
    public String sign_page(ModelMap modelMap) {

        return "sys/profile/profile_sign";
    }

    @RequiresPermissions("passportApply:*")
    @RequestMapping(value = "/profile_sign", method = RequestMethod.POST)
    @ResponseBody
    public Map do_passportApply_sign(@CurrentUser SysUser loginUser,String mobile, String phone,
                                     MultipartFile sign) throws IOException {

        String savePath =null;
        if(sign!=null && !sign.isEmpty()) {
            savePath = File.separator + "sign" + File.separator;
            File path = new File(springProps.uploadPath + savePath);
            if (!path.exists()) path.mkdirs();
            savePath += loginUser.getId() + ".jpg";

            Thumbnails.of(sign.getInputStream())
                    .size(750, 500)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.uploadPath + savePath);
        }

        if(StringUtils.isBlank(phone)){
            return failed("请填写办公电话");
        }

        if(StringUtils.isBlank(mobile) ||
                !FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            return failed("手机号码有误");
        }

        SysUser record = new SysUser();
        record.setId(loginUser.getId());
        record.setSign(savePath);
        record.setMobile(mobile);
        record.setPhone(phone);

        sysUserService.updateByPrimaryKeySelective(record, loginUser.getUsername(), loginUser.getCode());

        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("setting:view")
    @RequestMapping("/setting")
    public String setting() {

        return "index";
    }

    @RequiresPermissions("setting:view")
    @RequestMapping("/setting_page")
    public String setting_page(ModelMap modelMap) {

        return "sys/profile/setting";
    }

    @RequiresPermissions("profile:view")
    @RequestMapping("/profile")
    public String profile() {
        return "index";
    }

    @RequiresPermissions("profile:view")
    @RequestMapping("/profile_page")
    public String profile_page(@CurrentUser SysUser loginUser, ModelMap modelMap) {

        Integer userId = loginUser.getId();
        modelMap.put("adminPartyIdList", partyMemberAdminService.adminPartyIdList(userId));
        modelMap.put("adminBranchIdList", branchMemberAdminService.adminBranchIdList(userId));

        return "sys/profile/profile";
    }

    @RequestMapping(value="/profile", method=RequestMethod.POST)
    @ResponseBody
    public Map do_profile(MultipartFile _avatar, String realname, String _birth, Byte gender, String email, String mobile) throws IOException {

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        int userId = shiroUser.getId();
        SysUser sysUser = sysUserService.findById(userId);
        String avatar = null;
        if(_avatar!=null && !_avatar.isEmpty()){
            //String originalFilename = _avatar.getOriginalFilename();

            avatar =  File.separator + userId%100 + File.separator;
            File path = new File(springProps.avatarFolder + avatar);
            if(!path.exists()) path.mkdirs();
            avatar += sysUser.getCode() +".jpg";

            Thumbnails.of(_avatar.getInputStream())
                    .size(143, 198)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.avatarFolder + avatar);
            //FileUtils.copyFile(_avatar, new File(springProps.uploadPath + avatar));
        }

        SysUser record = new SysUser();
        record.setId(userId);
        record.setAvatar(avatar);
        record.setRealname(realname);
        record.setBirth(DateUtils.parseDate(_birth, DateUtils.YYYY_MM_DD));
        record.setGender(gender);
        record.setEmail(email);
        record.setMobile(mobile);

        sysUserService.updateByPrimaryKeySelective(record, shiroUser.getUsername(),shiroUser.getCode());

        return success(FormUtils.SUCCESS);
    }

    @RequestMapping(value="/updateAvatar", method=RequestMethod.POST)
    @ResponseBody
    public Map do_updateAvatar(MultipartFile _avatar, int userId) throws IOException {

        SysUser sysUser = sysUserService.findById(userId);
        String avatar = null;
        if(_avatar!=null && !_avatar.isEmpty()){

            avatar =  File.separator + userId%100 + File.separator;
            File path = new File(springProps.avatarFolder + avatar);
            if(!path.exists()) path.mkdirs();
            avatar += sysUser.getCode() +".jpg";

            Thumbnails.of(_avatar.getInputStream())
                    .size(143, 198)
                    .outputFormat("jpg")
                    .outputQuality(1.0f)
                    .toFile(springProps.avatarFolder + avatar);
        }

        SysUser record = new SysUser();
        record.setId(userId);
        record.setAvatar(avatar);
        sysUserService.updateByPrimaryKeySelective(record, sysUser.getUsername(), sysUser.getCode());
        return success(FormUtils.SUCCESS);
    }

    @RequiresPermissions("password:modify")
    @RequestMapping("/password")
    public String password() {
        return "index";
    }

    @RequiresPermissions("password:modify")
    @RequestMapping("/password_page")
    public String password_page(@CurrentUser SysUser sysUser, ModelMap modelMap) {

        modelMap.put("sysUser", sysUser);
        return "sys/profile/password";
    }

    @RequiresPermissions("password:modify")
    @RequestMapping(value="/password", method= RequestMethod.POST)
    @ResponseBody
    public Map password(@CurrentUser SysUser sysUser, String oldPassword, String password, HttpServletRequest request) {

        if(sysUser.getSource()!= SystemConstants.USER_SOURCE_ADMIN){
            return failed("当前账号不允许修改密码");
        }
        oldPassword = new String(Base64.decodeBase64(oldPassword.getBytes()));
        password = new String(Base64.decodeBase64(password.getBytes()));

        String passwordHash = passwordHelper.encryptBySalt(oldPassword, sysUser.getSalt());
        if(!StringUtils.equalsIgnoreCase(sysUser.getPasswd(), passwordHash)){
            return failed("原密码错误");
        }

        SysUser _sysUser = new SysUser();
        _sysUser.setId(sysUser.getId());
        SaltPassword encrypt = passwordHelper.encryptByRandomSalt(password);
        _sysUser.setSalt(encrypt.getSalt());
        _sysUser.setPasswd(encrypt.getPassword());
        sysUserService.updateByPrimaryKeySelective(_sysUser, sysUser.getUsername() , sysUser.getCode());

        return success(FormUtils.SUCCESS);
    }
}
