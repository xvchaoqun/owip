package controller.global;

import controller.BaseController;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
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
import sys.CasUtils;
import sys.constants.LogConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.PropertiesUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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

    @RequestMapping("/cas_test")
    public String cas_test(String username, HttpServletRequest request, HttpServletResponse response) {

        boolean lackRoleAdmin = !ShiroHelper.hasAnyRoles(RoleConstants.ROLE_ADMIN,
                RoleConstants.ROLE_ADMIN1);
        if(!PropertiesUtils.getBoolean("devMode")){
            if(lackRoleAdmin) { // 允许系统管理员、管理员在登录状态下登录别的账号，且不产生登录记录
                throw new UnauthorizedException();
            }else{

                boolean superAccount = CmTag.isSuperAccount(ShiroHelper.getCurrentUsername());
                if(!superAccount) {
                    Set<String> roles = sysUserService.findRoles(username);
                    if (roles.contains(RoleConstants.ROLE_ADMIN) || roles.contains(RoleConstants.ROLE_ADMIN1)) {
                        throw new UnauthorizedException();
                    }
                }
            }
        }

        logger.info(addLog(LogConstants.LOG_ADMIN, "切换账号登录%s", username));

        String _switchUser = ShiroHelper.getCurrentUsername();
        return casLogin(username, lackRoleAdmin, request, response, _switchUser);
    }

    // 切换回主账号
    @RequestMapping("/sysLogin_switch_back")
    public String sysLogin_switch_back(HttpServletRequest request, HttpServletResponse response) {

        String switchUser = (String) request.getSession().getAttribute("_switchUser");
        if(StringUtils.isBlank(switchUser))
            throw  new UnauthorizedException();

        // 防止被切换的账号登录时，踢出主账号 (但不能避免切换回来之前，被踢出)
        Cache<Object, Object> cache = cacheManager.getCache("shiro-kickout-session");
        cache.remove(ShiroHelper.getCurrentUsername());

        return casLogin(switchUser, false, request, response, null);
    }

    @RequestMapping("/cas")
    public String cas(HttpServletRequest request, HttpServletResponse response) {

        String username = CasUtils.getUsername(request);

        if(StringUtils.isBlank(username) && ShiroHelper.getCurrentUser()!=null){
            // 已登录的情况下，跳转到原账号
            return "redirect:/";
        }

        return casLogin(username, true, request, response, null);
    }

    private String casLogin(String username, boolean hasLoginLog,
                            HttpServletRequest request, HttpServletResponse response,
                            String _switchUser){

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

                if(hasLoginLog) {
                    logger.info(sysLoginLogService.log(shiroUser.getId(), shiroUser.getUsername(),
                            SystemConstants.LOGIN_TYPE_CAS, true, "登录成功"));
                }
                if(StringUtils.isNotBlank(_switchUser)){
                    SecurityUtils.getSubject().getSession().setAttribute("_switchUser", _switchUser);
                }else{
                    SecurityUtils.getSubject().getSession().removeAttribute("_switchUser");
                }

                return "redirect:/";
            } else {
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_CAS, false, "登录失败"));
            }
        }

        return "redirect:/jsp/unauthorized.jsp?username="+ StringUtils.trimToEmpty(username);
    }
}
