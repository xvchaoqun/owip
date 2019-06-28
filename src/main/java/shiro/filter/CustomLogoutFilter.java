package shiro.filter;

import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.utils.PropertiesUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomLogoutFilter extends LogoutFilter {

    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {

        boolean isCasUser = false;
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        SysUserView uv = CmTag.getUserById(currentUserId);
        if(uv!=null){
            isCasUser = BooleanUtils.isTrue(uv.isCasUser());
        }
        // 门户账号才需要单点登出
        return isCasUser?getRedirectUrl():
                StringUtils.defaultIfBlank(
                        PropertiesUtils.getString("logout.redirectUrl.default"), "/");
    }
}
