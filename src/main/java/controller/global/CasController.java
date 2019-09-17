package controller.global;

import controller.BaseController;
import domain.party.OrgAdmin;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import ext.utils.CasUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.sys.SysLoginLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.LogConstants;
import sys.constants.SystemConstants;
import sys.utils.HttpRequestDeviceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by fafa on 2016/6/9.
 */
@Controller
public class CasController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private EnterpriseCacheSessionDAO sessionDAO;
    @Autowired
    private ShortMsgService shortMsgService;

    //@RequiresPermissions("sysLogin:switch")
    @RequestMapping("/login_switch")
    public String login_switch(String username, HttpServletRequest request, HttpServletResponse response) {

        if (!ShiroHelper.isPermitted("sysLogin:switch")) {

            if (ShiroHelper.isPermitted("sysLogin:switchParty")) {
                // 限定了只允许切换党组织管理员的账号
                SysUserView uv = sysUserService.findByUsername(username);
                OrgAdmin orgAdmin = orgAdminService.get(uv.getId(), null, null);
                if (orgAdmin == null) {
                    throw new UnauthorizedException();
                }
            } else {
                throw new UnauthorizedException();
            }
        }

        logger.info(addLog(LogConstants.LOG_ADMIN, "切换账号登录%s", username));

        String _switchUser = ShiroHelper.getCurrentUsername();

        logoutAndRemoveSessionCache(request);

        return directLogin(username, SystemConstants.LOGIN_TYPE_SWITCH, request, response, _switchUser);
    }

    // 登出当前账号并清除session cache
    private void logoutAndRemoveSessionCache(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        //System.out.println(sessionDAO.getActiveSessionsCache().keys());
        SecurityUtils.getSubject().logout();
        sessionDAO.getActiveSessionsCache().remove(sessionId);
        // System.out.println(sessionDAO.getActiveSessionsCache().keys());
    }

    // 切换回主账号
    @RequestMapping("/sysLogin_switch_back")
    public String sysLogin_switch_back(HttpServletRequest request, HttpServletResponse response) {

        String switchUser = (String) request.getSession().getAttribute("_switchUser");
        if (StringUtils.isBlank(switchUser))
            throw new UnauthorizedException();

        // 防止被切换的账号登录时，踢出主账号 (但不能避免切换回来之前，被踢出)
        Cache<Object, Object> cache = cacheManager.getCache("shiro-kickout-session");
        cache.remove(ShiroHelper.getCurrentUsername());

        logoutAndRemoveSessionCache(request);

        return directLogin(switchUser, SystemConstants.LOGIN_TYPE_SWITCH, request, response, null);
    }

    @RequestMapping("/cas")
    public String cas(HttpServletRequest request, HttpServletResponse response) {

        if (ShiroHelper.getCurrentUser() != null) {
            // 已登录的情况下，跳转到原账号
            return loginRedirect(request, response);
        }

        return directLogin(CasUtils.getName(request), SystemConstants.LOGIN_TYPE_CAS,
                request, response, null);
    }

    @RequestMapping("/wxLogin")
    public String wxLogin(HttpServletRequest request, HttpServletResponse response) {

        if (ShiroHelper.getCurrentUser() != null) {
            // 已登录的情况下，跳转到原账号
            return loginRedirect(request, response);
        }

        String wxCode = request.getParameter("code");
        String username = shortMsgService.wxUserToCode(wxCode);
        logger.info("wxCode="+wxCode + " username="+ username);

        return directLogin(username, SystemConstants.LOGIN_TYPE_WX,
                request, response, null);
    }

    /**
     * 使用账号直接登录
     *
     * @param username
     * @param loginType   登录方式
     * @param request
     * @param response
     * @param _switchUser 切换登录的账号
     * @return
     */
    private String directLogin(String username, byte loginType,
                               HttpServletRequest request, HttpServletResponse response,
                               String _switchUser) {

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("_loginType", loginType); // 此次登录类型
        if (StringUtils.isNotBlank(username)) {

            SysUserView uv = sysUserService.findByUsername(username);
            if (uv != null && BooleanUtils.isFalse(uv.getLocked())) {  // 系统中存在这个用户（且状态正常）才处理

                ShiroUser shiroUser = new ShiroUser(uv.getId(), uv.getUsername(), uv.getCode(),
                        uv.getRealname(), uv.getType(), uv.getTimeout());
                PrincipalCollection principals = new SimplePrincipalCollection(
                        shiroUser, "casRealm");
                WebSubject.Builder builder = new WebSubject.Builder(request, response);
                builder.principals(principals);
                builder.authenticated(true);
                WebSubject subject = builder.buildWebSubject();
                ThreadContext.bind(subject);

                sysLoginLogService.setTimeout(SecurityUtils.getSubject());

                if (loginType != SystemConstants.LOGIN_TYPE_SWITCH) {
                    logger.info(sysLoginLogService.log(shiroUser.getId(), shiroUser.getUsername(),
                            loginType, true, "登录成功"));
                }
                // 读取最新的session
                session = SecurityUtils.getSubject().getSession();
                session.setAttribute("_loginType", loginType);
                if (StringUtils.isNotBlank(_switchUser)) {
                    session.setAttribute("_switchUser", _switchUser);
                } else {
                    session.removeAttribute("_switchUser");
                }

                return loginRedirect(request, response);
            } else {
                logger.info(sysLoginLogService.log(null, username,
                        loginType,
                        false, "登录失败"));
            }

            return redirect("/page/unauthorized.jsp?username=" + StringUtils.trimToEmpty(username), response);
        }

        //return "redirect:/page/timeout.jsp";
        return redirect("/", response);
    }

    private String loginRedirect(HttpServletRequest request, HttpServletResponse response) {

        String url = StringUtils.trimToEmpty(request.getParameter("url"));
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return redirect((HttpRequestDeviceUtils.isMobileDevice(request) ? "" : "/#") + url, response);
    }

    private String redirect(String url, HttpServletResponse response) {

        if (response.getStatus() != HttpStatus.SC_OK) {
            logger.warn("response.getStatus()=" + response.getStatus());
            return null;
        }

        return "redirect:" + url;
    }
}