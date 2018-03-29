package shiro.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.shiro.Constants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CurrentUserFilter extends PathMatchingFilter {

    @Autowired
    private SysUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = ShiroHelper.getCurrentUsername();
        request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
        return true;
    }
}
