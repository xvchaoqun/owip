package controller.mobile;

import controller.BaseController;
import domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.AuthToken;
import shiro.CurrentUser;
import sys.constants.SystemConstants;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/m")
public class MobileLoginController extends BaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/login")
	public String login() {

		return "m/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> do_login(String username, String password,
										HttpServletRequest request,
										HttpServletResponse response) throws IOException {

		Set<String> roles = sysUserService.findRoles(username);
		if(!roles.contains("cadre") && !roles.contains("cadreAdmin")){

			return failed("权限错误");
		}

		AuthToken token = new AuthToken(username,
				password.toCharArray(), false, request.getRemoteHost(), null, null);
		try {
			SecurityUtils.getSubject().login(token);
		}catch (Exception e){
			String message = e.getClass().getSimpleName();
			String userAgent = RequestUtils.getUserAgent(request);
			logger.info("login  failed. {}, {}, {}, {}", new Object[]{token.getPrincipal(), message, userAgent});

			return SystemConstants.loginFailedResultMap(message);
		}

		String successUrl=request.getContextPath() + "/m/index";
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
			successUrl = savedRequest.getRequestUrl();
		}

		logger.info(addLog(SystemConstants.LOG_LOGIN, "移动端登录成功"));

		Map<String, Object> resultMap = success("登入成功");
		resultMap.put("url", successUrl);
		return resultMap;
	}

	@RequestMapping("/logout")
	public String logout(@CurrentUser SysUser loginUser,  HttpSession session) {

		SecurityUtils.getSubject().logout();

		logger.debug("logout success. {}", (loginUser != null) ? loginUser.getUsername() : "");

		return "redirect:"+ PropertiesUtils.getString("site.logout.redirectTo");
	}

}
