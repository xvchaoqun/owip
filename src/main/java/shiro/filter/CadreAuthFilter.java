package shiro.filter;

import domain.cadre.Cadre;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.cadre.CadreService;
import shiro.ShiroUser;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by fafa on 2016/12/16.
 *
 * 过滤所有url为 "/cadre*"的请求 ，这些请求都带参数cadreId
 */
public class CadreAuthFilter extends AuthorizationFilter{

    @Autowired
    private CadreService cadreService;
    private final static String PARAM_CADERID = "cadreId";
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        Subject subject = getSubject(request, response);
        boolean isCadre = subject.hasRole(SystemConstants.ROLE_CADRE);
        boolean isCadreAdmin = subject.hasRole(SystemConstants.ROLE_CADREADMIN);

        // 不是干部也不是干部管理员，拒绝访问
        if(!isCadre && !isCadreAdmin){
            return false;
        }

        // 只是干部，不是干部管理员，只能访问本人的数据
        if(isCadre && !isCadreAdmin){
            ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
            Integer userId = shiroUser.getId();
            Cadre cadre = cadreService.findByUserId(userId);
            Integer cadreId = Integer.valueOf( WebUtils.getCleanParam(request, PARAM_CADERID));
            if(cadre.getId().intValue()!=cadreId){
                return false;
            }

            boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadre.getId());
            request.setAttribute("hasDirectModifyCadreAuth", hasDirectModifyCadreAuth);
            if(!hasDirectModifyCadreAuth){ // 如果没有直接修改权限，则POST修改数据都是不合法的
                HttpServletRequest req = (HttpServletRequest) request;
                if(req.getMethod().equalsIgnoreCase("POST")){
                    return false;
                }
            }
        }

        return true;
    }
}
