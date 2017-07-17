package controller.global;

import controller.BaseController;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.constants.SystemConstants;
import sys.shiro.SaltPassword;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by fafa on 2016/9/21.
 */
@Controller
public class FindPassController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CacheManager cacheManager;

    @RequestMapping("/find_pass")
    public String find_pass() {

        return "find_pass";
    }

    @RequestMapping(value="/find_pass", method = RequestMethod.POST)
    @ResponseBody
    public Map checkUsername(String username) {

        Map resultMap = success();
        SysUserView uv = sysUserService.findByUsername(username);
        if(uv==null || uv.getLocked()){
            resultMap.put("type", 0); // 账号不存在或账号被锁定
            return resultMap;
        }

        switch (uv.getSource()){
            case SystemConstants.USER_SOURCE_BKS:
            case SystemConstants.USER_SOURCE_YJS:
            case SystemConstants.USER_SOURCE_JZG:
                resultMap.put("type", 1); // 人事账号
                break;
            default:
                resultMap.put("type", 2);  // 注册账号
                String mobile = uv.getMobile();
                if(StringUtils.isBlank(mobile)){
                    resultMap.put("send", 0); // 手机号码为空
                }else{
                    int seq = shortMsgService.changePassword(username);
                    if(seq>0){

                        resultMap.put("send", 1); // 短信发送成功
                        resultMap.put("seq", seq); // 短信序号
                        resultMap.put("mobile", mobile.substring(0, 3) + "****" + mobile.substring(7,11));
                    }else{
                        resultMap.put("send", 2); // 短信发送失败
                    }
                }
                break;
        }

        return resultMap;
    }

    @RequestMapping(value="/find_pass/changepw", method = RequestMethod.POST)
    @ResponseBody
    public Map changepw(String username, String password, String code){


        if(!FormUtils.match(PropertiesUtils.getString("passwd.regex"), password)){
            return failed("密码由6-16位的字母、下划线和数字组成");
        }

        SysUserView uv = sysUserService.findByUsername(username);
        if(uv==null){
            return failed("该账号不存在，请重新输入。");
        }

        Cache<String, String> findPassCache = cacheManager.getCache("FindPassDayCount");
        String cacheKey = DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + "_" + uv.getMobile();
        String cacheVal = findPassCache.get(cacheKey);
        String _code = null;
        int seq = 0;
        if(cacheVal!=null){
            seq = Integer.parseInt(cacheVal.split("_")[0]);
            _code = cacheVal.split("_")[1];
        }

        if(_code==null){
            return failed("短信验证码错误，请点击【发送短信】按钮。");
        }

        if(code!=null && StringUtils.equalsIgnoreCase(code.trim(), _code)){

            SysUser _sysUser = new SysUser();
            _sysUser.setId(uv.getId());
            SaltPassword encrypt = passwordHelper.encryptByRandomSalt(password);
            _sysUser.setSalt(encrypt.getSalt());
            _sysUser.setPasswd(encrypt.getPassword());
            sysUserService.updateByPrimaryKeySelective(_sysUser, uv.getUsername(), uv.getCode());

            // 覆盖原验证码，使其失效
            findPassCache.put(cacheKey, seq + "_" + RandomStringUtils.randomNumeric(4));

            logger.info(addLog(uv.getId(), uv.getUsername(), SystemConstants.LOG_USER, "账号%s通过短信验证修改密码成功", username));
            return success();
        }else{
            return failed("短信验证码错误，请输入正确的短信验证码");
        }
    }

}
