package service;

import domain.sys.SysResource;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.party.BranchAdminService;
import service.party.PartyAdminService;
import service.sys.SysLoginLogService;
import service.sys.SysResourceService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.utils.HttpRequestDeviceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fafa on 2016/1/20.
 */
@Service
public class LoginUserService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    protected PartyAdminService partyAdminService;
    @Autowired
    protected BranchAdminService branchAdminService;

    public List<Integer> adminPartyIdList(){
        return  new ArrayList<>(partyAdminService.adminPartyIdList(ShiroHelper.getCurrentUserId()));
    }
    public List<Integer> adminBranchIdList(){
        return  new ArrayList<>(branchAdminService.adminBranchIdList(ShiroHelper.getCurrentUserId()));
    }

    /**
     * 使用账号直接登录
     *
     * @param username
     * @param loginType   登录方式
     * @param request
     * @param response
     * @param _switchUser 切换登录的账号
     * @return
     */
    public String directLogin(String username, byte loginType,
                               HttpServletRequest request, HttpServletResponse response,
                               String _switchUser) {

        Session session = ShiroHelper.getSession();
        session.setAttribute("_loginType", loginType); // 此次登录类型
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

                sysLoginLogService.setTimeout(ShiroHelper.getSubject());

                if (loginType != SystemConstants.LOGIN_TYPE_SWITCH) {
                    logger.info(sysLoginLogService.log(shiroUser.getId(), shiroUser.getUsername(),
                            loginType, true, "登录成功"));
                }
                // 读取最新的session
                session = ShiroHelper.getSession();
                session.setAttribute("_loginType", loginType);
                if (StringUtils.isNotBlank(_switchUser)) {
                    session.setAttribute("_switchUser", _switchUser);
                } else {
                    session.removeAttribute("_switchUser");
                }

                return loginRedirect(request, response);
            } else {
                logger.info(sysLoginLogService.log(null, username,
                        loginType,
                        false, "登录失败"));
            }

            return redirect("/page/unauthorized.jsp?username=" + StringUtils.trimToEmpty(username), response);
        }

        //return "redirect:/page/timeout.jsp";
        return redirect("/", response);
    }

    public String loginRedirect(HttpServletRequest request, HttpServletResponse response) {

        String url = StringUtils.trimToEmpty(request.getParameter("url"));
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        // 是否校验权限
        if(StringUtils.equals(request.getParameter("checkAuth"), "1")){
            SysResource sysResource = sysResourceService.getByUrl(url);
            if(sysResource==null || !ShiroHelper.isPermitted(sysResource.getPermission())){
                url = ""; // 资源不存在或没有权限，则跳转至首页
            }
        }

        return redirect((HttpRequestDeviceUtils.isMobileDevice(request) ? "" : "/#") + url, response);
    }

    public String redirect(String url, HttpServletResponse response) {

        if (response.getStatus() != HttpStatus.SC_OK) {
            logger.warn("response.getStatus()=" + response.getStatus());
            return null;
        }

        return "redirect:" + url;
    }
}
