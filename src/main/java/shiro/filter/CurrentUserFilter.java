package shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.SysUserService;
import shiro.ShiroUser;
import sys.shiro.Constants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CurrentUserFilter extends PathMatchingFilter {

    @Autowired
    private SysUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        String username = shiroUser.getUsername();

        request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
        return true;
    }
}
