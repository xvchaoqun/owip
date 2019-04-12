package shiro.filter;

import domain.sys.SysUserView;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomLogoutFilter extends LogoutFilter {

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {

        SysUserView uv = ShiroHelper.getCurrentUser();

        if (uv.getSource() == SystemConstants.USER_SOURCE_ADMIN
                || uv.getSource() == SystemConstants.USER_SOURCE_REG) {
            return DEFAULT_REDIRECT_URL;
        }

        return getRedirectUrl();
    }
}
