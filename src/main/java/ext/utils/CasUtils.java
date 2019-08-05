package ext.utils;

import shiro.ShiroHelper;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by fafa on 2016/6/11.
 */
public class CasUtils {

    // 获取CAS用户名
    public static String getName(HttpServletRequest request){

        Principal userPrincipal = request.getUserPrincipal();
        /*if(userPrincipal instanceof AttributePrincipal) {
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
            if (principal != null) {
                return principal.getName();
            }
        }*/

        // 已登录的情况下，不再读取CAS登录账号
        if(ShiroHelper.getCurrentUsername()!=null) return null;

        return userPrincipal==null?null:userPrincipal.getName();
    }
}
