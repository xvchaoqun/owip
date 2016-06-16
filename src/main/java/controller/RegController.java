package controller;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.sys.RegException;
import shiro.AuthToken;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.IdcardValidator;
import sys.utils.IpUtils;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RegController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	@ResponseBody
	public Map do_reg(String username, String passwd, Byte type,
					  String realname, String idcard, String phone,
					  Integer party, String captcha, HttpServletRequest request) {

		username = StringUtils.trimToNull(username);
		realname = StringUtils.trimToNull(realname);
		idcard = StringUtils.trimToNull(idcard);
		phone = StringUtils.trimToNull(phone);
		captcha = StringUtils.trimToNull(captcha);

		String _captcha = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isBlank(_captcha) || !_captcha.equalsIgnoreCase(captcha)) {
			return failed("验证码错误。");
		}
		if(!FormUtils.usernameFormatRight(username)){
			return failed("用户名由3-10位的字母、下划线和数字组成，且不能以数字或下划线开头。");
		}
		if(!FormUtils.match(PropertiesUtils.getString("passwd.regex"), passwd)){
			return failed("密码由6-16位的字母、下划线和数字组成");
		}
		if(type==null || SystemConstants.USER_TYPE_MAP.get(type)==null){
			return failed("类型有误");
		}
		if(StringUtils.isBlank(realname) || StringUtils.length(realname)<2){
			return failed("请填写真实姓名");
		}
		IdcardValidator idcardValidator = new IdcardValidator();
		if(!idcardValidator.isValidatedAllIdcard(idcard)){
			return failed("身份证号码有误。");
		}
		if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), phone)){
			return failed("手机号码有误");
		}
		if(party==null || partyService.findAll().get(party)==null){
			return failed("请选择分党委。");
		}
		try {
			sysUserRegService.reg(username, passwd, type, realname,
					idcard, phone, party, IpUtils.getRealIp(request));
		}catch (RegException re){
			return failed(re.getMessage());
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error("注册失败：" + ex.getMessage());
			return failed("系统错误：" + ex.getMessage());
		}

		logger.info(String.format("%s 注册成功", username));

		AuthToken token = new AuthToken(username,
				passwd.toCharArray(), false, request.getRemoteHost(), null, null);

		SecurityUtils.getSubject().login(token);

		logger.info(addLog(SystemConstants.LOG_USER, "注册后登录成功"));

		return success();
	}
}
