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
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

		if(sysUserService.findByUsername(username)==null){
			logger.info(sysLoginLogService.log(null, username,
					SystemConstants.LOGIN_TYPE_MOBILE, false, "登录失败，用户不存在"));
			return failed("账号或密码错误");
		}

		Set<String> roles = sysUserService.findRoles(username);
		if(!roles.contains("cadre") && !roles.contains("cadreAdmin")){

			logger.info(sysLoginLogService.log(null, username,
					SystemConstants.LOGIN_TYPE_MOBILE, false, "登录失败，权限错误"));

			return failed("权限错误");
		}

		AuthToken token = new AuthToken(username,
				password.toCharArray(), false, request.getRemoteHost(), null, null);
		try {
			SecurityUtils.getSubject().login(token);
		}catch (Exception e){
			String message = e.getClass().getSimpleName();
			/*String userAgent = RequestUtils.getUserAgent(request);
			logger.info("login  failed. {}, {}, {}, {}", new Object[]{token.getPrincipal(), message, userAgent});

			String msg;
			SysUser sysUser = sysUserService.findByUsername(username);
			if(sysUser==null){
				msg = "登录失败，用户名不存在";
			}else{
				msg = "登录失败，密码错误";
			}
			logger.info(sysLoginLogService.log(null, username,
					SystemConstants.LOGIN_TYPE_MOBILE, false, msg));*/

			return SystemConstants.loginFailedResultMap(message);
		}

		String successUrl=request.getContextPath() + "/m/index";
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
			successUrl = savedRequest.getRequestUrl();
		}

		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		logger.info(sysLoginLogService.log(shiroUser.getId(), username,
				SystemConstants.LOGIN_TYPE_MOBILE, true, "登录成功"));

		Map<String, Object> resultMap = success("登入成功");
		resultMap.put("url", successUrl);
		return resultMap;
	}

	@RequestMapping("/logout")
	@ResponseBody
	public Map logout(@CurrentUser SysUser loginUser,  HttpSession session) {

		SecurityUtils.getSubject().logout();

		logger.debug("logout success. {}", (loginUser != null) ? loginUser.getUsername() : "");
		return success();
	}

}
