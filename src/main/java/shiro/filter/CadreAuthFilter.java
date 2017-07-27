package shiro.filter;

import domain.cadre.CadreView;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.cadre.CadreService;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.tags.CmTag;
import sys.utils.PatternUtils;

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
    // 拥有[干部本人修改权限]的角色的请求（/cadre*），必须带此参数；且值为当前用户的干部ID
    private final static String PARAM_CADERID = "cadreId";
    // 拥有[干部本人修改权限],但是没有直接修改的权限的角色的POST请求，必须携带此参数；且值为1
    private final static String PARAM_TOAPPLY = "toApply";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        // 干部库特殊访问权限
        if(PatternUtils.match("/cadre|/cadre_data", WebUtils.getRequestUri((HttpServletRequest) request))
                && ShiroHelper.isPermitted("cadre:list2")){
            return true;
        }

        // 拥有管理干部信息或管理干部本人信息的权限，才允许访问
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN)
                && !ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMINSELF)){
            return false;
        }

        // 只有修改干部本人信息的权限，需要判断一下是否是本人和读取更新的权限
        if(!ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMIN) &&
                ShiroHelper.isPermitted(SystemConstants.PERMISSION_CADREADMINSELF)){

            Integer userId = ShiroHelper.getCurrentUserId();
            CadreView cadre = cadreService.dbFindByUserId(userId);
            String _cadreId = WebUtils.getCleanParam(request, PARAM_CADERID);
            if(!NumberUtils.isDigits(_cadreId)) return false;
            Integer cadreId = Integer.valueOf(_cadreId);
            if(cadre==null || cadre.getId().intValue()!=cadreId){
                return false;
            }

            boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadre.getId());
            request.setAttribute("hasDirectModifyCadreAuth", hasDirectModifyCadreAuth);
            if(!hasDirectModifyCadreAuth){
                HttpServletRequest req = (HttpServletRequest) request;
                // 如果没有直接修改权限，则POST直接修改数据是不合法的，必须携带修改申请参数toApply=1
                if(req.getMethod().equalsIgnoreCase("POST")){

                    String _toApply = WebUtils.getCleanParam(request, PARAM_TOAPPLY);
                    return StringUtils.equals(_toApply, "1");
                }
            }
        }

        return true;
    }
}
