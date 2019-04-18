package shiro.filter;

import domain.sys.SysUserView;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import shiro.ShiroHelper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomLogoutFilter extends LogoutFilter {

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {

        SysUserView uv = ShiroHelper.getCurrentUser();
        // 门户账号才需要单点登出
        return uv.isCasUser()?getRedirectUrl():DEFAULT_REDIRECT_URL;
    }
}
