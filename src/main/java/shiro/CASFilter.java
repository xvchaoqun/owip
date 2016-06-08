package shiro;

import domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.subject.WebSubject;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.LogService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.RequestUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by fafa on 2016/6/8.
 */
public class CASFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LogService logService;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Subject _subject = getSubject(request, response);
        if (!_subject.isAuthenticated() && !_subject.isRemembered()) {
            AttributePrincipal principal = (AttributePrincipal)httpServletRequest.getUserPrincipal();
            if(principal!=null) {
                String userID = principal.getName();
                if (StringUtils.isNotBlank(userID)) {
                    SysUser sysUser = sysUserService.findByUsername(userID);
                    ShiroUser shiroUser = new ShiroUser(sysUser.getId(), sysUser.getUsername(), sysUser.getCode(),
                            sysUser.getRealname(), sysUser.getType());
                    PrincipalCollection principals = new SimplePrincipalCollection(
                            shiroUser, "casRealm");
                    WebSubject.Builder builder = new WebSubject.Builder(request, response);
                    builder.principals(principals);
                    builder.authenticated(true);
                    WebSubject subject = builder.buildWebSubject();
                    ThreadContext.bind(subject);

                    String userAgent = RequestUtils.getUserAgent((HttpServletRequest) request);
                    logger.info("login {}, {}", new Object[]{logService.log(SystemConstants.LOG_LOGIN,
                            "登录成功 by CAS"), userAgent});
                }
            }
        }

        return true;
    }
}
