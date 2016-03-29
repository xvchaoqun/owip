package shiro;

import bean.ApproverTypeBean;
import domain.Cadre;
import domain.MetaType;
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
import service.SpringProps;
import service.abroad.ApplySelfService;
import service.sys.SysResourceService;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.HashSet;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SpringProps springProps;
    @Autowired
    private SysUserService userService;
    @Autowired
    private ApplySelfService applySelfService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roles = userService.findRoles(shiroUser.getUsername());
        Set<String> permissions = userService.findPermissions(shiroUser.getUsername());

        Set<String> _permissions = new HashSet<>(); /// 拷贝， 防止缓存被篡改
        _permissions.addAll(permissions);

        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(filterMenus(shiroUser, roles, _permissions));

        return authorizationInfo;
    }

    /**
     * 特殊的用户权限过滤
     */
    public Set<String> filterMenus(ShiroUser shiroUser, Set<String> userRoles,  Set<String> userPermissions){

        if(userRoles.contains("cadre") && shiroUser.getApproverTypeBean()!=null) {

            ApproverTypeBean approverTypeBean = shiroUser.getApproverTypeBean();
            Cadre cadre = approverTypeBean.getCadre();
            if (cadre != null) {
                MetaType leaderPostType = CmTag.getMetaTypeByCode("mt_leader");
                if (cadre.getPostId().intValue() == leaderPostType.getId()) {
                    // 干部的职务属性为校领导的，没有(userApplySelf:*， userPassportDraw:*)
                    userPermissions.remove("userApplySelf:*");
                    userPermissions.remove("userPassportDraw:*");
                }

                // 没有审批权限的干部，没有（abroad:admin（目录）, applySelf:approvalList)
                if (!(approverTypeBean.isMainPost() || approverTypeBean.isManagerLeader() || approverTypeBean.isApprover())) {

                    userPermissions.remove("applySelf:approvalList");
                    if (!userRoles.contains("cadreAdmin")) {
                        // 干部管理员 需要目录，普通干部不需要
                        userPermissions.remove("abroad:admin");
                    }
                }
            }
        }
        return userPermissions;
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

        if(springProps.useSSOLogin && user.getSource()!=SystemConstants.USER_SOURCE_ADMIN ){
            // 如果是第三方账号登陆，则登陆密码换成第三方登陆的
            boolean tryLogin;
            try{
                tryLogin = loginService.tryLogin(username, inputPasswd);
            }catch (Exception ex){
                ex.printStackTrace();
                throw new SSOException();
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
        Integer userId = user.getId();
        ShiroUser shiroUser = new ShiroUser(userId, username, user.getRealname(), user.getType(),
                applySelfService.getApproverTypeBean(userId));

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
