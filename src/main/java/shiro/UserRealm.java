package shiro;

import domain.sys.SysUserView;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginService;
import service.SpringProps;
import service.sys.SysUserService;
import sys.shiro.AuthToken;
import sys.shiro.NeedCASLoginException;
import sys.shiro.SSOException;
import sys.tags.CmTag;

import java.util.Set;


public class UserRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SpringProps springProps;
    @Autowired
    private SysUserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        /*HttpServletRequest request = ContextHelper.getRequest();
        System.out.println(request.getRequestURI()+" +++++++++++++++++++++++++++++++++++++++++++++");*/

        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(shiroUser.getRoles());

        Set<String> permissions = shiroUser.getPermissions();
        permissions.addAll(shiroUser.getmPermissions());
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    @Autowired
    protected PasswordHelper passwordHelper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        AuthToken authToken = (AuthToken) token;
        String username = authToken.getUsername();
        String password = null;
        String salt = "salt";

        SysUserView uv = userService.findByUsername(username);
        if (uv == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if (uv.getLocked()) {
            throw new LockedAccountException(); //帐号锁定
        }
        if (authToken.getPassword() == null) {
            throw new IncorrectCredentialsException();
        }
        String inputPasswd = String.valueOf(authToken.getPassword());

        if (uv.isCasUser()) { // 如果是校园账号，且提供了代理接口的情况下，检测是否可以通过代理接口认证

            byte casType = CmTag.getByteProperty("cas_type", (byte)1);

            if(!springProps.devMode){

                if (casType==2 || casType==3) { // 如果提供了代理接口

                    boolean tryLogin;
                    try {
                        tryLogin = loginService.tryLogin(uv.getCode(), inputPasswd);
                    } catch (Exception ex) {
                        logger.error("异常", ex);
                        throw new SSOException();
                    }
                    if (!tryLogin) {
                        throw new IncorrectCredentialsException();
                    }
                } else if (casType==1) { // 仅提供了CAS接口

                    throw new NeedCASLoginException();
                }else if(casType !=0 ){  // 除了0，其他情况不允许登录

                    throw new IncorrectCredentialsException();
                }
            }

            // 通过代理接口认证后，允许登录系统（校验密码等同输入的密码）
            password = new SimpleHash(
                    credentialsMatcher.getHashAlgorithmName(),
                    inputPasswd,
                    ByteSource.Util.bytes(salt),
                    credentialsMatcher.getHashIterations()).toHex();
        } else {

            password = uv.getPasswd();
            salt = uv.getSalt();
        }

        Integer userId = uv.getId();
        ShiroUser shiroUser = new ShiroUser(userId, username, uv.getCode(),
                uv.getRealname(), uv.getType(), uv.getTimeout());

        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                shiroUser,
                password, //密码
                ByteSource.Util.bytes(salt),
                getName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
