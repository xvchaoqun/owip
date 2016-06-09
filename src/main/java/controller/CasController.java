package controller;

import domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.sys.LogService;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fafa on 2016/6/9.
 */
@Controller
public class CasController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LogService logService;

    @RequestMapping("/cas")
    public String cas(HttpServletRequest request, HttpServletResponse response) {

        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        if(principal!=null) {
            String userID = principal.getName();
            if (StringUtils.isNotBlank(userID)) {
                SysUser sysUser = sysUserService.findByUsername(userID);
                if(sysUser != null) {  // 系统中存在这个用户才处理

                    ShiroUser shiroUser = new ShiroUser(sysUser.getId(), sysUser.getUsername(), sysUser.getCode(),
                            sysUser.getRealname(), sysUser.getType());
                    PrincipalCollection principals = new SimplePrincipalCollection(
                            shiroUser, "casRealm");
                    WebSubject.Builder builder = new WebSubject.Builder(request, response);
                    builder.principals(principals);
                    builder.authenticated(true);
                    WebSubject subject = builder.buildWebSubject();
                    ThreadContext.bind(subject);
                    String userAgent = RequestUtils.getUserAgent(request);
                    logger.info("login {}, {}", new Object[]{logService.log(SystemConstants.LOG_LOGIN,
                            "登录成功 by CAS"), userAgent});
                    return "redirect:/login";
                }
            }
        }

        return "redirect:/login";
    }
}
