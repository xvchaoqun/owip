package interceptor;

import controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sys.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;


public class SessionInterceptor extends BaseController implements AsyncHandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String _commonUrls = PropertiesUtils.getString("sys.commonUrls");

        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/WEB-INF")){
            servletPath = (String)request.getAttribute("javax.servlet.forward.request_uri");
        }
        if(!PatternUtils.match(_commonUrls, servletPath)) {
            if (HttpUtils.isMoblie(request)) {
                if (!servletPath.startsWith("/m/")) { // 移动端
                    WebUtils.issueRedirect(request, response, "/m/index");
                    return false;
                }
            } else {
                if (servletPath.startsWith("/m/")) { // 非移动端
                    WebUtils.issueRedirect(request, response, "/index");
                    return false;
                }
            }
        }

        if(servletPath.length()>1 && servletPath.endsWith("/")){
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
            modelMap.putAll(getMetaMap());
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
