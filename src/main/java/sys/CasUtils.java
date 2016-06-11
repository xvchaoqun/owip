package sys;

import org.jasig.cas.client.authentication.AttributePrincipal;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by fafa on 2016/6/11.
 */
public class CasUtils {

    // 获取CAS用户名
    public static String getUsername(HttpServletRequest request){

        Principal userPrincipal = request.getUserPrincipal();
        if(userPrincipal instanceof AttributePrincipal) {
            AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
            if (principal != null) {
                return principal.getName();
            }
        }
        return null;
    }
}
