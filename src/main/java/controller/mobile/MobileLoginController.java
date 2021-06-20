package controller.mobile;

import controller.BaseController;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.shiro.AuthToken;
import sys.shiro.CurrentUser;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/m")
public class MobileLoginController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/login")
	public String login() {

		return "mobile/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> do_login(String username, String password,
										Boolean rememberMe,
										HttpServletRequest request,
										HttpServletResponse response) throws IOException {

		SysUserView sysUser = sysUserService.findByUsername(username);
		if(sysUser==null){
			logger.info(sysLoginLogService.log(null, username,
					SystemConstants.LOGIN_TYPE_MOBILE, false, "登录失败，账号不存在"));
			return failed("账号或密码错误");
		}

		AuthToken token = new AuthToken(username,
				password.toCharArray(), BooleanUtils.isTrue(rememberMe), request.getRemoteHost(), null, null);
		try {
			ShiroHelper.login(token);
		}catch (Exception e){
			String message = e.getClass().getSimpleName();

			return SystemConstants.loginFailedResultMap(message);
		}

		String successUrl=request.getContextPath() + "/m/index";
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
			successUrl = savedRequest.getRequestUrl();
		}

		sysLoginLogService.setTimeout(ShiroHelper.getSubject());

		if(ShiroHelper.ipAccessLimited(request)){
            return failed("禁止访问，请联系管理员");
		}

		logger.info(sysLoginLogService.log(ShiroHelper.getCurrentUserId(), username,
				SystemConstants.LOGIN_TYPE_MOBILE, true, "登录成功"));

		Map<String, Object> resultMap = success("登入成功");
		resultMap.put("url", successUrl);
		return resultMap;
	}

	@RequestMapping("/logout")
	public String logout(@CurrentUser SysUserView loginUser,  HttpSession session) {

		ShiroHelper.logout();

		logger.debug("logout success. {}", (loginUser != null) ? loginUser.getUsername() : "");

		return "redirect:"+ PropertiesUtils.getString("logout.mobile.redirectUrl");
	}

}
