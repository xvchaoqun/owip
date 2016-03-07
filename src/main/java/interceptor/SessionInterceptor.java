package interceptor;

import controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sys.utils.IpUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;


public class SessionInterceptor extends BaseController implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        if(requestURI.length()>1 && requestURI.endsWith("/")){
            response.setStatus(404);
            return false;
        }
        String userAgent = RequestUtils.getUserAgent(request);
        logger.debug("request {}, {}, {}, {}, {},request.getContentType()={}, request.getHeader(\"Cookie\")={}", new Object[]{
                IpUtils.getRealIp(request), userAgent,
                request.getMethod(),
                request.getRequestURL(), RequestUtils.getQueryString(request),
                request.getContentType(),
                request.getHeader("Cookie")
        });

        /*if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SortTable sortTable = method.getAnnotation(SortTable.class);
            if(sortTable!=null) {
                String tableName = sortTable.value();
                String sort = request.getParameter("sort");
                String order = request.getParameter("order");
                Set<String> tableColumns = dbServcie.getTableColumns(tableName);

                if (sort != null && !tableColumns.contains(sort)) {
                    request.getParameterMap().remove("sort");
                    //throw new RuntimeException("sort参数有误");
                }
                if (order != null && !order.equals("desc") && !order.equals("asc")) {
                    request.getParameterMap().remove("order");
                    //throw new RuntimeException("order参数有误");
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
            modelMap.put("useCaptcha",springProps.useCaptcha);

            modelMap.put("cadreMap", cadreService.findAll());
            modelMap.put("passportTypeMap", metaTypeService.metaTypes("mc_passport_type"));

            modelMap.put("adminLevelMap", metaTypeService.metaTypes("mc_admin_level"));
            modelMap.put("postMap", metaTypeService.metaTypes("mc_post"));

           // modelMap.put("countryMap", countryService.findAll());
            modelMap.put("unitMap", unitService.findAll());
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
