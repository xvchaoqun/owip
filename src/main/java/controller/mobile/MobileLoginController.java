package controller.mobile;

import bean.ApproverTypeBean;
import controller.BaseController;
import domain.Cadre;
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
import sys.tags.CmTag;
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

		SysUser sysUser = sysUserService.findByUsername(username);
		if(sysUser==null){
			logger.info(sysLoginLogService.log(null, username,
					SystemConstants.LOGIN_TYPE_MOBILE, false, "登录失败，用户不存在"));
			return failed("账号或密码错误");
		}

		Set<String> roles = sysUserService.findRoles(username);
		// 不是干部，也不是干部管理员
		if(!roles.contains("cadre") && !roles.contains("cadreAdmin")){

			logger.info(sysLoginLogService.log(null, username,
					SystemConstants.LOGIN_TYPE_MOBILE, false, "登录失败，请使用PC端登录网站办理相关业务，谢谢!"));

			return failed("登录失败，请使用PC端登录网站办理相关业务，谢谢!");
		}
		// 是干部，但不是干部管理员
		if(roles.contains("cadre") && !roles.contains("cadreAdmin")){
			ApproverTypeBean approverTypeBean = CmTag.getApproverTypeBean(sysUser.getId());
			if(approverTypeBean==null)
				return failed(sysUser.getRealname()+"老师，您好！您没有因私出国（境）审批权限，无法登陆。请在电脑的浏览器中登录系统办理相关业务。谢谢！");
			Cadre cadre = approverTypeBean.getCadre();
			// 没有审批权限的干部 不能登录
			if (cadre.getStatus() != SystemConstants.CADRE_STATUS_NOW ||
					!(approverTypeBean.isMainPost() || approverTypeBean.isManagerLeader() || approverTypeBean.isApprover())) {
				return failed(sysUser.getRealname()+"老师，您好！您没有因私出国（境）审批权限，无法登陆。请在电脑的浏览器中登录系统办理相关业务。谢谢！");
			}
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
