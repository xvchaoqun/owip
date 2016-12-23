package shiro.filter;

import domain.cadre.Cadre;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.cadre.CadreService;
import service.helper.ShiroHelper;
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

        // 不是干部、后备干部、干部管理员，拒绝访问
        /*if(!ShiroHelper.hasAnyRoles(SystemConstants.ROLE_CADRE,
                SystemConstants.ROLE_CADRERESERVE, SystemConstants.ROLE_CADREADMIN)){
            return false;
        }*/
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isPermitted("cadre:admin")
                && !subject.isPermitted("cadre:adminSelf")){ // 拥有管理干部信息或管理干部本人信息的权限，才允许访问
            return false;
        }

        // 只是干部或预备干部，不是干部管理员，只能访问本人的数据
        /*if(ShiroHelper.hasAnyRoles(SystemConstants.ROLE_CADRE, SystemConstants.ROLE_CADRERESERVE) &&
                ShiroHelper.lackRole(SystemConstants.ROLE_CADREADMIN)){*/
        if(subject.isPermitted("cadre:adminSelf")){ // 只有修改干部本人信息的权限

            Integer userId = ShiroHelper.getCurrentUserId();
            Cadre cadre = cadreService.findByUserId(userId);
            String cleanParam = WebUtils.getCleanParam(request, PARAM_CADERID);
            if(!NumberUtils.isDigits(cleanParam)) return false;
            Integer cadreId = Integer.valueOf(cleanParam);
            if(cadre==null || cadre.getId().intValue()!=cadreId){
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
