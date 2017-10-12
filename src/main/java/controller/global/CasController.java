package controller.global;

import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import shiro.ShiroUser;
import sys.CasUtils;
import sys.constants.SystemConstants;

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
    private SysLoginLogService sysLoginLogService;

    @RequestMapping("/cas")
    public String cas(HttpServletRequest request, HttpServletResponse response) {

        String username = CasUtils.getUsername(request);
        if (StringUtils.isNotBlank(username)) {
            SysUserView uv = sysUserService.findByUsername(username);
            if (uv != null && BooleanUtils.isFalse(uv.getLocked())) {  // 系统中存在这个用户（且状态正常）才处理

                ShiroUser shiroUser = new ShiroUser(uv.getId(), uv.getUsername(), uv.getCode(),
                        uv.getRealname(), uv.getType());
                PrincipalCollection principals = new SimplePrincipalCollection(
                        shiroUser, "casRealm");
                WebSubject.Builder builder = new WebSubject.Builder(request, response);
                builder.principals(principals);
                builder.authenticated(true);
                WebSubject subject = builder.buildWebSubject();
                ThreadContext.bind(subject);

                logger.info(sysLoginLogService.log(shiroUser.getId(), shiroUser.getUsername(),
                        SystemConstants.LOGIN_TYPE_CAS, true, "登录成功"));
                return "redirect:/";
            } else {
                logger.info(sysLoginLogService.log(null, username,
                        SystemConstants.LOGIN_TYPE_CAS, false, "登录失败"));
            }
        }


        return "redirect:/login";
    }
}
