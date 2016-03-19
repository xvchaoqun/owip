package shiro;

import domain.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import service.LoginService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.utils.MD5Util;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        authorizationInfo.setRoles(userService.findRoles(shiroUser.getUsername()));
        authorizationInfo.setStringPermissions(userService.findPermissions(shiroUser.getUsername()));

        return authorizationInfo;
    }

    @Autowired
    protected PasswordHelper passwordHelper;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        AuthToken authToken = (AuthToken)token;
        String username = authToken.getUsername();
        String password = null;
        String salt = "salt";

        SysUser user = userService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if (user.getLocked()) {
            throw new LockedAccountException(); //帐号锁定
        }

        String inputPasswd = String.valueOf(authToken.getPassword());

        if(user.getSource()!=SystemConstants.USER_SOURCE_ADMIN ){
            // 如果是第三方账号登陆，则登陆密码换成第三方登陆的
            boolean tryLogin;
            try{
                tryLogin = loginService.tryLogin(username, inputPasswd);
            }catch (Exception ex){
                ex.printStackTrace();
                throw new RuntimeException("单点登录服务器错误，请稍后重试");
            }
            if(!tryLogin){
                throw new IncorrectCredentialsException();
            }
            password = new SimpleHash(
                    credentialsMatcher.getHashAlgorithmName(),
                    inputPasswd,
                    ByteSource.Util.bytes(salt),
                    credentialsMatcher.getHashIterations()).toHex();
        }else {
            password = user.getPasswd();
            salt = user.getSalt();
        }

        ShiroUser shiroUser = new ShiroUser(user.getId(), username, user.getRealname(), user.getType());

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
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
