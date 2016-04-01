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
import sys.utils.FormUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

		AuthToken token = new AuthToken(username,
				password.toCharArray(), false, request.getRemoteHost(), null, null);

		try {
			SecurityUtils.getSubject().login(token);
		}catch (Exception e){
			String message = e.getClass().getSimpleName();
			String userAgent = RequestUtils.getUserAgent(request);
			logger.info("login  failed. {}, {}, {}, {}", new Object[]{token.getPrincipal(), message, userAgent});

			Map<String, Object> resultMap = new HashMap();
			resultMap.put("success", false);
			if ("SystemClosedException".equals(message)) {

				resultMap.put("msg", "参评人员测评未开启");
			} else if ("IncorrectCredentialsException".equals(message)) {
				resultMap.put("msg", "账号或密码错误");
			} else if ("UnknownAccountException".equals(message)) {
				resultMap.put("msg", "账号或密码错误");
			} else if ("IncorrectCaptchaException".equals(message)) {
				resultMap.put("msg", "验证码错误");
			} else if ("LockedAccountException".equals(message)) {
				resultMap.put("msg", "账号被锁定");
			}else if ("InspectorFinishException".equals(message)) {
				resultMap.put("msg", "该账号已经测评完成");
			}else if("SSOException".equals(message)){
				resultMap.put("msg", "单点登录服务器错误，请稍后重试");
			}else {
				resultMap.put("msg", "系统错误");
			}
			return resultMap;
		}

		String successUrl=request.getContextPath() + "/m/index";
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		if(savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
			successUrl = savedRequest.getRequestUrl();
		}

		Map<String, Object> resultMap = new HashMap();
		resultMap.put("success", true);
		resultMap.put("msg", "登入成功");
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
