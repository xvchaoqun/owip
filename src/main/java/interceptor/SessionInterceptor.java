package interceptor;

import controller.BaseController;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionInterceptor extends BaseController implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

		/*if(SecurityUtils.getSubject().hasRole(SystemConstants.ROLE_UNIT_ADMIN)){

			User user = (User) request.getAttribute(Constants.CURRENT_USER);

			// 单位管理员必须填好信息
			if(StringUtils.isBlank(user.getRealname())
					||StringUtils.isBlank(user.getPhone())
					||StringUtils.isBlank(user.getMobile())
					||StringUtils.isBlank(user.getCode())
					||StringUtils.isBlank(user.getTitle())){

				String path = (String) request.getAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH");
				//System.out.println(request.getAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH"));
				if(path.indexOf("/update_info")==-1 && path.indexOf("/unit_note")==-1){

					response.sendRedirect(request.getContextPath() + "/update_info");
					return false;
				}
			}
		}*/

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (null != modelAndView) {

            ModelMap modelMap = modelAndView.getModelMap();
            //modelMap.put("roleMap", sysRoleService.findAll());
            //modelMap.put("metaClassMap", metaClassService.findAll());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request,
                                               HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub

    }
}
