package controller.global;

import controller.BaseController;
import ext.service.ShortMsgService;
import ext.utils.CasUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiro.ShiroHelper;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fafa on 2016/6/9.
 */
@Controller
public class CasController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected CacheManager cacheManager;
    @Autowired
    private ShortMsgService shortMsgService;

    //@RequiresPermissions("sysLogin:switch")
    @RequestMapping("/login_switch")
    public String login_switch(String username, HttpServletRequest request, HttpServletResponse response) {

        if (!ShiroHelper.isPermitted("sysLogin:switch")) {

            if (ShiroHelper.isPermitted("sysLogin:switchParty")) {

                // 限定了只允许切换分党委管理员的账号
                if (!CmTag.hasRole(username, RoleConstants.ROLE_PARTYADMIN)) {
                    throw new UnauthorizedException();
                }
            } else {
                throw new UnauthorizedException();
            }
        }

        logger.info(addLog(LogConstants.LOG_ADMIN, "切换账号登录%s", username));

        String _switchUser = ShiroHelper.getCurrentUsername();

        ShiroHelper.logoutAndRemoveSessionCache(request);

        return loginUserService.directLogin(username, SystemConstants.LOGIN_TYPE_SWITCH, request, response, _switchUser);
    }

    // 切换回主账号
    @RequestMapping("/sysLogin_switch_back")
    public String sysLogin_switch_back(HttpServletRequest request, HttpServletResponse response) {

        String switchUser = (String) request.getSession().getAttribute("_switchUser");
        if (StringUtils.isBlank(switchUser)){

            return loginUserService.redirect("/", response);
        }

        // 防止被切换的账号登录时，踢出主账号 (但不能避免切换回来之前，被踢出)
        Cache<Object, Object> cache = cacheManager.getCache("shiro-kickout-session");
        cache.remove(ShiroHelper.getCurrentUsername());

        ShiroHelper.logoutAndRemoveSessionCache(request);

        return loginUserService.directLogin(switchUser, SystemConstants.LOGIN_TYPE_SWITCH, request, response, null);
    }

    @RequestMapping("/cas")
    public String cas(HttpServletRequest request, HttpServletResponse response) {

        if (ShiroHelper.getCurrentUser() != null) {
            // 已登录的情况下，跳转到原账号
            return loginUserService.loginRedirect(request, response);
        }

        return loginUserService.directLogin(CasUtils.getName(request), SystemConstants.LOGIN_TYPE_CAS,
                request, response, null);
    }

    @RequestMapping("/wxLogin")
    public String wxLogin(HttpServletRequest request, HttpServletResponse response) {

        if (ShiroHelper.getCurrentUser() != null) {
            // 已登录的情况下，跳转到原账号
            return loginUserService.loginRedirect(request, response);
        }

        String wxCode = request.getParameter("code");
        String username = shortMsgService.wxUserToCode(wxCode);
        logger.info("wxCode="+wxCode + " username="+ username);

        return loginUserService.directLogin(username, SystemConstants.LOGIN_TYPE_WX,
                request, response, null);
    }
}